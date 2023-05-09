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
public class    Situation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_situation", nullable = false)
    private Long id_situation;

    private Date datesituation;
    private String obervation;
    private Double avancementPGT;
    private Double avancementReel;


    @OneToMany(  cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SituationDetail> situationdetail;

}
