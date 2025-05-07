package com.gestion.filmotheque.service;

import com.gestion.filmotheque.entities.Acteur;
import java.util.List;
import java.util.Optional;

public interface IServiceActeur {



    public Acteur createActeur(Acteur acteur);
    public  List<Acteur> findAllActeurs();
     Acteur findActeurById(int id);
    Acteur updateActeur(Acteur acteur);
    void deleteActeur(int id);
}