package com.gestion.filmotheque.service;


import com.gestion.filmotheque.entities.Categorie;
import com.gestion.filmotheque.repository.CategorieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ServiceCategorie implements IServiceCategorie{

    CategorieRepository categorieRepository;
    @Override
    public Categorie createCategorie(Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    @Override
    public List<Categorie> findAllCategories() {
        return categorieRepository.findAll();
    }

    @Override
    public Categorie updateCategorie(Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    @Override
    public Categorie findCategorieById(int id) {
        return categorieRepository.findById(id).get();
    }
}

