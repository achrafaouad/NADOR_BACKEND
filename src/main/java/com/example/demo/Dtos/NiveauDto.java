package com.example.demo.Dtos;

import com.example.demo.Model.Niveau;
import com.example.demo.Model.Projet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class NiveauDto {
    private Niveau niveau;

    private  Double avReel;
    private Double avPgt;

    List<ProjectDTO> projectDTOS;


}
