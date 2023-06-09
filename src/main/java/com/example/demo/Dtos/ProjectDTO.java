package com.example.demo.Dtos;

import com.example.demo.Model.Projet;
import com.example.demo.Model.Province;
import com.example.demo.Model.Lot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;



@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProjectDTO {

    private Projet myProject;

    private  Double avReel;
    private Double avPgt;
    List<LotDto> lotDtos;

//calcul
    private  Double Reel;
    private Double Pgt;
    private Double montant;


    // Getters and setters
}