// ServiceActeur.java
package com.gestion.filmotheque.service;

import com.gestion.filmotheque.entities.Acteur;
import com.gestion.filmotheque.repository.ActeurRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class ServiceActeur implements IServiceActeur {
    private final ActeurRepository acteurRepository;

    @Override
    public Acteur createActeur(Acteur acteur) {
        return acteurRepository.save(acteur);
    }


    @Override
    public Acteur findActeurById(int id) {
        return acteurRepository.findById(id).orElse(null);
    }

    @Override
    public Acteur updateActeur(Acteur acteur) {
        return acteurRepository.save(acteur);
    }

    @Override
    public void deleteActeur(int id) {
        acteurRepository.deleteById(id);
    }
    @Override
    public List<Acteur> findAllActeurs() { // Implémentation
        return acteurRepository.findAll();
    }
}