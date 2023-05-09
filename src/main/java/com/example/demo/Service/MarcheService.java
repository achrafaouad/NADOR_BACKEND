package com.example.demo.Service;


import com.example.demo.Dao.*;
import com.example.demo.Model.Marche;
import com.example.demo.Model.Situation;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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


        System.out.println(lastSituation);
        return lastSituation;
    }



    public Marche saveMarche(Marche marche) {
        return marche_repo.save(marche);
    }




}
