package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Projet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String objet;
    private String mot_cle;
    private String observation;
    private Integer anneeProjet;



    @JsonIgnoreProperties({"projets"})
    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name = "niveau")
    private Niveau niveau;

    @JsonIgnoreProperties({"projets"})
    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name = "foncier")
    private Foncier foncier;


    @JsonIgnoreProperties({"projet"})
    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Lot> lots;


    @JsonIgnoreProperties({"projet"})
    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Section> sections;



}
