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
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_province", nullable = false)
    private Long id_province;
    private String name;

//    @ManyToMany(mappedBy = "provinces")
//    @JsonBackReference
//    Set<Projet> projet;



//    @JsonIgnoreProperties({"province"})
//    @OneToMany(cascade=CascadeType.ALL,mappedBy = "province", fetch = FetchType.LAZY)
//    private Set<Section> sections;

}
