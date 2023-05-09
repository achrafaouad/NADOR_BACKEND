package com.example.demo.Dtos;

import com.example.demo.Model.Projet;
import com.example.demo.Model.Province;
import com.example.demo.Model.Lot;

import java.util.Set;
import java.util.stream.Collectors;

public class ProjectDTO {
    private Long id;
    private String objet;
    private String mot_cle;
    private String observation;
    private Integer anneeProjet;
    private String niveauDescription;
    private String foncierDescription;
    private Set<String> provinceNames;
    private Set<String> lotIntitules;

    public ProjectDTO(Long id, String objet, String mot_cle, String observation, Integer anneeProjet, String niveauDescription,
                      String foncierDescription, Set<String> provinceNames, Set<String> lotIntitules) {
        this.id = id;
        this.objet = objet;
        this.mot_cle = mot_cle;
        this.observation = observation;
        this.anneeProjet = anneeProjet;
        this.niveauDescription = niveauDescription;
        this.foncierDescription = foncierDescription;
        this.provinceNames = provinceNames;
        this.lotIntitules = lotIntitules;
    }

    // Getters and setters
}