package com.example.demo;

import com.example.demo.Dao.SituationDetail_Repo;
import com.example.demo.Model.*;
import com.example.demo.Service.MarcheService;
import com.example.demo.Service.PrixService;
import com.example.demo.Service.Service;
import com.example.demo.Service.SituationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
    @RequestMapping("rest")
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
    public SituationDetail_Repo situationDetail_repo;



    //endpoint get
    @GetMapping(value = "/")
    public ResponseEntity<?> index(){
        return new ResponseEntity<>(this.service.getAll(), HttpStatus.OK);
    }

    @PostMapping(value = "/")
    public ResponseEntity<?> index2(@RequestBody String test){
        return new ResponseEntity<>(test, HttpStatus.OK);
    }


    @PostMapping(value = "/saveProject")
    public ResponseEntity<?> saveProject(@RequestBody Projet projet){

        Projet p = this.service.saveProjet(projet);

        for(Section s: p.getSections()){
            this.service.saveGeom(PersistgeomSections.builder().section(s.getId_section()).geometry(s.getGeom()).build());

        }
        return new ResponseEntity<>(p,HttpStatus.OK);
    }



    @PostMapping(value = "/getGeomProjet" ,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getGeomProjet(@RequestBody Projet projet){

        return new ResponseEntity<>(this.service.getGeomProjet(projet.getId()),HttpStatus.OK);
    }

    @PostMapping(value = "/saveMarchetoLot")
    public ResponseEntity<?> saveMarchetoLot(@RequestBody Lot lot){
        return new ResponseEntity<>(this.service.saveLot(lot),HttpStatus.OK);
    }


    @GetMapping(value = "/addPersonnel")
    public ResponseEntity<?> addPersonnel(){
        return new ResponseEntity<>(this.service.ajoutPer(), HttpStatus.OK);
    }



    @GetMapping(value = "/getProvinecs")
    public ResponseEntity<?> getProvinecs(){
        return new ResponseEntity<>(this.service.getProvinecs(), HttpStatus.OK);
    }

    @GetMapping(value = "/getFonciers")
    public ResponseEntity<?> getFonciers(){
        return new ResponseEntity<>(this.service.getFonciers(), HttpStatus.OK);
    }


    @GetMapping(value = "/getNiveau")
    public ResponseEntity<?> getNiveau(){ return new ResponseEntity<>(this.service.getNiveau(), HttpStatus.OK);
    }

    @GetMapping(value = "/getProjets")
    public ResponseEntity<?> getProjets(){ return new ResponseEntity<>(this.service.getProjets(), HttpStatus.OK);
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

            System.out.println(marche);

            System.out.println(marche.getSituations());

            List<Situation> st = this.situationService.saveSituations(marche.getSituations());
            marche.setSituations(st);
            Marche savedMarche = marcheService.saveMarche(marche);


            return ResponseEntity.ok(savedMarche);
        }





}
