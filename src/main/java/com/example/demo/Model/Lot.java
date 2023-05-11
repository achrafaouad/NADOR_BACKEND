package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class Lot {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private  String intitule;
    private  String observation ;
    private  Double  pkd ;
    private  Double  pkf ;
    private  Double  montant  ;


//    @JsonIgnoreProperties({"lots"})
//    @ManyToOne(cascade=CascadeType.ALL)
//    @JoinColumn(name = "marche")


    @JsonIgnoreProperties({"projet"})
    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Section> sections;

//    @JsonIgnoreProperties({"lot"})
//    @OneToOne(cascade=CascadeType.ALL)
//    @JoinColumn(name = "marche")
//    private Marche marche;

    @JsonIgnoreProperties({"projet"})
    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Marche> marches;



}
