package com.example.demo;

import com.example.demo.Dao.SituationDetail_Repo;
import com.example.demo.Dtos.*;
import com.example.demo.Model.*;
import com.example.demo.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "rest")

public class Controller {

    @Autowired
    public Service service;


    @Autowired
    public MarcheService marcheService;


    @Autowired
    public PrixService prixService;

    @Autowired
    public SituationService situationService;


    @Autowired
    public ProjectService projectService;


    @Autowired
    public NiveauService niveauService;


    @Autowired
    public LotService lotService;

    @Autowired
    public SituationDetail_Repo situationDetail_repo;


    //endpoint get
    @GetMapping(value = "/")

    public ResponseEntity<?> index() {
        return new ResponseEntity<>(this.service.getAll(), HttpStatus.OK);
    }

    @PostMapping(value = "/")

    public ResponseEntity<?> index2(@RequestBody String test) {
        return new ResponseEntity<>(test, HttpStatus.OK);
    }


    @PostMapping(value = "/saveProject")

    public ResponseEntity<?> saveProject(@RequestBody Projet projet) {

        Projet p = this.service.saveProjet(projet);
        //todo
        for (Lot l : p.getLots()) {
            for (Section s : l.getSections()) {
                this.service.saveGeom(PersistgeomSections.builder().section(s.getId_section()).geometry(s.getGeom()).build());
            }
        }

        return new ResponseEntity<>(p, HttpStatus.OK);
    }


    @PostMapping(value = "/getGeomProjet",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<?> getGeomProjet(@RequestBody Projet projet) {
        System.out.println(projet.getId());
        return new ResponseEntity<>(this.service.getGeomProjet(projet.getId()), HttpStatus.OK);
    }

    @PostMapping(value = "/saveMarchetoLot")

    public ResponseEntity<?> saveMarchetoLot(@RequestBody Lot lot) {

        return new ResponseEntity<>(this.service.saveLot(lot), HttpStatus.OK);
    }


    @GetMapping(value = "/addPersonnel")

    public ResponseEntity<?> addPersonnel() {
        return new ResponseEntity<>(this.service.ajoutPer(), HttpStatus.OK);
    }


    @GetMapping(value = "/getProvinecs")

    public ResponseEntity<?> getProvinecs() {
        return new ResponseEntity<>(this.service.getProvinecs(), HttpStatus.OK);
    }

    @GetMapping(value = "/getFonciers")

    public ResponseEntity<?> getFonciers() {
        return new ResponseEntity<>(this.service.getFonciers(), HttpStatus.OK);
    }


    @GetMapping(value = "/getNiveau")

    public ResponseEntity<?> getNiveau() {
        return new ResponseEntity<>(this.service.getNiveau(), HttpStatus.OK);
    }

    @GetMapping(value = "/getProjets")

    public ResponseEntity<?> getProjets() {
        return new ResponseEntity<>(this.service.getProjets(), HttpStatus.OK);
    }

    @PostMapping(value = "/getLastSituationForMarche")

    public ResponseEntity<Situation> getLastSituationForMarche(@RequestBody Marche marche) {
        Situation lastSituation = marcheService.getLastSituationForMarche(marche);
        if (lastSituation == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lastSituation);
    }


    @PostMapping("/saveSituation")

    public ResponseEntity<Situation> saveSituation(@RequestBody Situation situation) {
        Situation savedSituation = situationService.saveSituation(situation);
        return ResponseEntity.ok(savedSituation);
    }


    @PostMapping("/saveMarche")

    public ResponseEntity<Marche> saveMarche(@RequestBody Marche marche) {

        List<Situation> st = this.situationService.saveSituations(marche.getSituations());
        marche.setSituations(st);
        Marche savedMarche = marcheService.saveMarche(marche);


        return ResponseEntity.ok(savedMarche);
    }

    @PostMapping("/saveMarcheAfterInit")

    public ResponseEntity<Marche> saveMarcheAfterInit(@RequestBody Marche marche) {


        List<Prix> prx = this.prixService.saveAll(marche.getPrixes());
        marche.setPrixes(prx);

        Marche savedMarche = marcheService.saveMarche(marche);

        for (Prix p : savedMarche.getPrixes()) {
            p.setMarche(savedMarche);
        }

        this.prixService.saveAll(savedMarche.getPrixes());


        return ResponseEntity.ok(savedMarche);
    }


    @GetMapping("/getEvolutionAvancement/{marcheId}")

    public SituationsSummaryDTO getEvolutionAvancement(@PathVariable Long marcheId) {
        System.out.println(marcheId);
        if (marcheId != null) {
            return this.marcheService.getEvolutionAvancement(marcheId);
        }
        return null;


    }

    @GetMapping("/getRadar/{marcheId}")

    public RadioDTO getRadar(@PathVariable Long marcheId) {
        System.out.println(marcheId);
        if (marcheId != null) {
            return this.marcheService.getRadar(marcheId);
        }
        return null;
    }

    @GetMapping("/getSituationGlobal")
    public ResponseEntity<?> getSituationGlobal(@RequestParam(required = false) Long niveauId, @RequestParam(required = false) Long projetId, @RequestParam(required = false) Long lotId, @RequestParam(required = false) Long marcheId) {
        System.out.println(niveauId);
        System.out.println(projetId);
        System.out.println(lotId);
        System.out.println(marcheId);

        if (marcheId != null ) {
            return new ResponseEntity<>(this.marcheService.getStuationMarche(marcheId),HttpStatus.OK);
        } else if (lotId != null) {


            List<Long> marchesId = new ArrayList<>();

            Lot lot = this.lotService.findLotById(lotId).get();
            for (Marche m : lot.getMarches()) {
                marchesId.add(m.getId_marche());

            }

            LotDto lotDto = this.marcheService.getStuationLot(marchesId);
            lotDto.setMyLot(lot);

            return new ResponseEntity<>(lotDto,HttpStatus.OK);

        } else if (projetId != null) {
            Projet p = this.projectService.findprojetById(projetId).get();


            return new ResponseEntity<>( this.marcheService.calculate(p),HttpStatus.OK);

        }else if (niveauId != null) {
            Niveau n = this.niveauService.findNiveauById(niveauId).get();
            return new ResponseEntity<>(this.marcheService.calculateForNiveau(n),HttpStatus.OK);

        }else{
            List<Niveau> niveaus = this.niveauService.findAll();
            List<NiveauDto> niveauDtos = new ArrayList<>();
            for(Niveau n:niveaus){
                niveauDtos.add(this.marcheService.calculateForNiveau(n));
            }

            return new ResponseEntity<>(niveauDtos,HttpStatus.OK);

        }
    }




    @GetMapping("/test")
    public NiveauDto test() {
        Niveau n = this.niveauService.findNiveauById(1L).get();
        return this.marcheService.calculateForNiveau(n);

    }


}
