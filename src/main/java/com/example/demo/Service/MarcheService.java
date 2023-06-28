package com.example.demo.Service;


import com.example.demo.Dao.*;
import com.example.demo.Dtos.*;
import com.example.demo.Model.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class MarcheService {


    private Marche_Repo marche_repo;
    private LotService lotService;

    @Autowired
    public MarcheService(Marche_Repo marcheRepo, LotService lotService) {
        marche_repo = marcheRepo;
        this.lotService = lotService;
    }





    public Situation getLastSituationForMarche(Marche marche) {
        List<Situation> situations = marche.getSituations();

        if (situations == null || situations.isEmpty()) {
            return new Situation(null, null, null, null, null, new ArrayList<>());
        }

        // Find the Situation with the maximum ID
        Situation lastSituation = situations.stream()
                .max(Comparator.comparing(Situation::getId_situation))
                .orElse(null);


        return lastSituation;
    }



    public MarcheDto getStuationMarche(Long marcheID){
        Marche marche = this.marche_repo.findById(marcheID).get();


       Situation st = this.getLastSituationForMarche(marche);


        Double avReel = 0D;
        Double avPgt = 0D;


            Double avReelval = 0D;
            Double avPgtval = 0D;
            Double montant = 0D;
            if(st.getDatesituation()!= null){
                for ( SituationDetail situationDetail: st.getSituationdetail()){
                    if(situationDetail.getPrix().getPu()!=null &&  situationDetail.getQteprecu() !=null && situationDetail.getQtereacu()!= null){
                        avReelval = avReelval+ situationDetail.getPrix().getPu() * situationDetail.getQteprecu();
                        avPgtval = avPgtval+ situationDetail.getPrix().getPu() * situationDetail.getQtereacu();
                        montant = montant+ situationDetail.getPrix().getMontant();
                    }
                }
                avReel =  (double) Math.round(100* avReelval/montant);
                avPgt = (double) Math.round(100*avReelval/montant);

            }
        MarcheDto marcheDto = MarcheDto.builder().delai(marche.getDelai()).id_marche(marche.getId_marche()).dateOverturePlit(marche.getDateOverturePlit()).numero_marche(marche.getNumero_marche()).delegation(marche.getDelegation()).estimationao(marche.getEstimationao()).avPgt(avPgt).avReel(avReel).situation(st).build();

        return marcheDto;

    }




    public NiveauDto calculateForNiveau(Niveau n) {


        Double avReel = 0D;
        Double avPgt = 0D;
        Double avReelval = 0D;
        Double avPgtval = 0D;
        Double montant = 0D;
        List<ProjectDTO> projectDTOS = new ArrayList<>();
        for(Projet p: n.getProjets()){

            ProjectDTO projectDTO = this.calculate(p);
            avReelval =avReelval + projectDTO.getReel();
            avPgtval = avPgtval + projectDTO.getPgt();
            montant =montant + projectDTO.getMontant();

            projectDTO.setMyProject(p);
            projectDTOS.add(projectDTO);
        }


        avReel = ( (double) Math.round(100* avReelval/montant));
        avPgt =  ((double) Math.round(100*avReelval/montant));

        return NiveauDto.builder().projectDTOS(projectDTOS).niveau(n).avReel(avReel).avPgt(avPgt).build();

    }

    public ProjectDTO calculate(Projet p) {




        Double avReel = 0D;
        Double avPgt = 0D;
        Double avReelval = 0D;
        Double avPgtval = 0D;
        Double montant = 0D;
        List<LotDto> lotDtos = new ArrayList<>();
        for(Lot l:p.getLots()){



            List<Long> marchesId = new ArrayList<>();

            Lot lot = this.lotService.findLotById(l.getId()).get();
            for(Marche m: lot.getMarches()){
                marchesId.add(m.getId_marche());

            }

            LotDto lotDto = this.getStuationLot(marchesId);
            avReelval =avReelval + lotDto.getReel();
            avPgtval = avPgtval + lotDto.getPgt();
            montant =montant + lotDto.getMontant();
            lotDto.setMyLot(lot);

            lotDtos.add(lotDto);
        }

        avReel = ( (double) Math.round(100* avReelval/montant));
        avPgt =  ((double) Math.round(100*avReelval/montant));

        return ProjectDTO.builder().myProject(p).avReel(avReel).Reel(avReelval).Pgt(avPgtval).montant(montant).avPgt(avPgt).lotDtos(lotDtos).build();



    }


    public LotDto getStuationLot(List<Long> marcheIDS){
        List <Marche> marches = this.marche_repo.findAllById(marcheIDS);


        List<Situation> situations = new ArrayList<>();
        for(Marche m: marches){
            situations.add(this.getLastSituationForMarche(m)) ;
        }


//        Double avReel = 0D;
//        Double avPgt = 0D;

        Double avReel = 0D;
        Double avPgt = 0D;

        Double avReelval = 0D;
        Double avPgtval = 0D;
        Double montant = 0D;

        for(Situation st : situations){
            if(st.getDatesituation()!= null){
                for ( SituationDetail situationDetail: st.getSituationdetail()){
                    if(situationDetail.getPrix().getPu()!=null &&  situationDetail.getQteprecu() !=null && situationDetail.getQtereacu()!= null){
                        avReelval = avReelval+ situationDetail.getPrix().getPu() * situationDetail.getQteprecu();
                        avPgtval = avPgtval+ situationDetail.getPrix().getPu() * situationDetail.getQtereacu();
                        montant = montant+ situationDetail.getPrix().getMontant();
                    }
                }
            }
        }
        avReel = ( (double) Math.round(100* avReelval/montant));
        avPgt =  ((double) Math.round(100*avReelval/montant));

        List<MarcheDto> marcheDto = new ArrayList<>();

        for(long id:marcheIDS ){
            marcheDto.add(this.getStuationMarche(id));

        }


        LotDto lotDto = LotDto.builder().avReel(avReel).avPgt(avPgt).Reel(avReelval).Pgt(avPgtval).montant(montant).marcheDtoList(marcheDto).build();

        return lotDto;

    }



    public Marche saveMarche(Marche marche) {
        return marche_repo.save(marche);
    }



    public String getformatedDate(Date d){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        if(d != null){
            return  formatter.format(d);

        }
        return "-----";
    }



    public SituationsSummaryDTO getEvolutionAvancement(Long marcheID) {
        Marche marche = this.marche_repo.findById(marcheID).get();


        List<Situation> sortedSituations = marche.getSituations().stream()
                .sorted(Comparator.comparing(Situation::getId_situation))
                .collect(Collectors.toList());

        List<Double> avReel = new ArrayList<>();
        List<Double> avPgt = new ArrayList<>();
        List<String> Dates = new ArrayList<>();

        for(Situation st : sortedSituations){
            Double avReelval = 0D;
            Double avPgtval = 0D;
            Double montant = 0D;
            if(st.getDatesituation()!= null){
                for ( SituationDetail situationDetail: st.getSituationdetail()){
                    if(situationDetail.getPrix().getPu()!=null &&  situationDetail.getQteprecu() !=null && situationDetail.getQtereacu()!= null){
                        avReelval = avReelval+ situationDetail.getPrix().getPu() * situationDetail.getQteprecu();
                        avPgtval = avPgtval+ situationDetail.getPrix().getPu() * situationDetail.getQtereacu();
                        montant = montant+ situationDetail.getPrix().getMontant();
                    }

                }

                avReel.add( (double) Math.round(100* avReelval/montant));
                avPgt.add((double) Math.round(100*avReelval/montant));
                Dates.add(getformatedDate(st.getDatesituation()));

            }
        }


        SituationsSummaryDTO summaryDTO = new SituationsSummaryDTO(avReel, avPgt, Dates);
        return summaryDTO;

    }


      public RadioDTO getRadar(Long marcheID){

          Marche marche = this.marche_repo.findById(marcheID).get();

          Situation lastSituation = this.getLastSituationForMarche(marche);

          List<Double> avReel = new ArrayList<>();
          List<Double> avPgt = new ArrayList<>();
          List<String> prix = new ArrayList<>();



          for(SituationDetail std : lastSituation.getSituationdetail()){
              if(std.getPrix().getQte() != null && std.getPrix().getQte() !=0 && std.getQteprecu() != null &&  std.getQtereacu()!= null){
                  avReel.add((double) Math.round(100*std.getQteprecu()/std.getPrix().getQte()));
                  avPgt.add((double) Math.round(100*std.getQtereacu()/std.getPrix().getQte()));
                  prix.add(std.getPrix().getPrix());
              }

          }


          RadioDTO radioDTO = new RadioDTO(avReel, avPgt, prix);
          return radioDTO;

    }



}
