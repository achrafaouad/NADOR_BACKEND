package com.example.demo.Dtos;

import com.example.demo.Model.Prix;
import com.example.demo.Model.Situation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MarcheDto {
    private Long id_marche;

    private String numero_marche;
    private Integer delai;
    private String objet;
    private String titulaire;
    private Double montant;
    private Date os_commencement;
    private Date dateOverturePlit;
    private String n_appel_offre;
    private Double estimationao;
    private Double montantengage;
    private String etape_etude;
    private String status_etude;
    private String delegation;
    private String status_marche;
    private Situation situation;

    private  Double avReel;
    private Double avPgt;

}
