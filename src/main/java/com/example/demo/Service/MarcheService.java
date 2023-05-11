package com.example.demo.Service;


import com.example.demo.Dao.*;
import com.example.demo.Dtos.RadioDTO;
import com.example.demo.Dtos.SituationsSummaryDTO;
import com.example.demo.Model.Marche;
import com.example.demo.Model.Situation;
import com.example.demo.Model.SituationDetail;
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


    @Autowired
    public MarcheService(Marche_Repo marcheRepo) {
        marche_repo = marcheRepo;
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
