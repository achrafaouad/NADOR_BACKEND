package com.example.demo.Service;

import com.example.demo.Dao.Lot_Repo;
import com.example.demo.Dao.Projet_Repo;
import com.example.demo.Model.Lot;
import com.example.demo.Model.Projet;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@org.springframework.stereotype.Service

public class ProjectService {



    @Autowired
    private Projet_Repo projetRepo;


    public Optional<Projet> findprojetById(Long id){
        return this.projetRepo.findById(id);
    }

}
