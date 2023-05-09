package com.example.demo.Dao;

import com.example.demo.Model.Lot;
import com.example.demo.Model.Marche;
import com.example.demo.Model.Prix;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
class LotRepoTest {

    @Autowired
    private Lot_Repo lotRepo;



    @Test
    public  void set(){

        List<Prix> prixList=new ArrayList<>();
        prixList.add(Prix.builder().prix("pr").montant(1564D).build());
        prixList.add(Prix.builder().prix("pr2").montant(184D).build());
        Marche m=  Marche.builder().numero_marche("num").objet("sdfg").prixes(prixList).build();
        Lot lot = lotRepo.findAll().get(0);
        lot.setMarche(m);
        lotRepo.save(lot);

    }



}