package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_section", nullable = false)
    private Long id_section;

    private  Double  pkd ;
    private  Double  pkf ;
    private  String  type ;

    @Transient
    private String geom;


//    @ManyToOne(cascade=CascadeType.MERGE)
//    @JoinColumn(name = "province")
//    private Province province;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province")
    private Province province;


}
