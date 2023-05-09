package com.example.demo.Service;

import com.example.demo.Dao.PrixRepository;
import com.example.demo.Model.Marche;
import com.example.demo.Model.Prix;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
@org.springframework.stereotype.Service
public class PrixService {

    @Autowired
    private PrixRepository prixRepository;



    public Prix findById(Long id){
        return this.prixRepository.findById(id).orElse(null);
    }




    public Prix mergePrix(Prix prix) {
        if (prix.getId_prix() != null) {
            Optional<Prix> existingPrix = prixRepository.findById(prix.getId_prix());
            if (existingPrix.isPresent()) {
                Prix managedPrix = existingPrix.get();

                managedPrix.setNumprix(prix.getNumprix());
                managedPrix.setPrix(prix.getPrix());
                managedPrix.setUnite(prix.getUnite());
                managedPrix.setQte(prix.getQte());
                managedPrix.setPu(prix.getPu());
                managedPrix.setMontant(prix.getMontant());
                return managedPrix;


            }
        }
        return prixRepository.save(prix);
    }



}
