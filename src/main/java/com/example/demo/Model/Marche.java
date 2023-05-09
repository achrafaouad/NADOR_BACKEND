package com.example.demo.Model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Marche {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_marche", nullable = false)
    private Long id_marche;

    private String numero_marche;
    private Integer delai;
    private String objet;
    private String titulaire;
    private Double montant;
    private Date os_commencement;
    private Date dateOverturePlit;
    private String n_appel_offre;



//    @OneToMany(mappedBy = "marche", cascade = CascadeType.ALL, fetch = FetchType.LAZY)


    @JsonIgnoreProperties({"marche"})
    @OneToMany( cascade = { CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private List<Prix> prixes = new ArrayList<>();

    @OneToMany( cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private List<Situation> situations = new ArrayList<>();

}

