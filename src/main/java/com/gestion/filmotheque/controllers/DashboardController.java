package com.gestion.filmotheque.controllers;

import com.gestion.filmotheque.entities.Film;
import com.gestion.filmotheque.service.IServiceActeur;
import com.gestion.filmotheque.service.IServiceCategorie;
import com.gestion.filmotheque.service.IServiceFilm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final IServiceFilm filmService;
    private final IServiceActeur acteurService;
    private final IServiceCategorie categorieService;

    @Autowired
    public DashboardController(
            IServiceFilm filmService,
            IServiceActeur acteurService,
            IServiceCategorie categorieService) {
        this.filmService = filmService;
        this.acteurService = acteurService;
        this.categorieService = categorieService;
    }

    @GetMapping
    public String showDashboard(Model model) {
        // Get all films and take most recent (limited to 5)
        List<Film> allFilms = filmService.findAllFilms();
        List<Film> recentFilms = allFilms.size() > 5
                ? allFilms.subList(0, 5)
                : allFilms;
        model.addAttribute("recentFilms", recentFilms);

        // Add counts for statistics
        model.addAttribute("filmCount", allFilms.size());
        model.addAttribute("actorCount", acteurService.findAllActeurs().size());
        model.addAttribute("categoryCount", categorieService.findAllCategories().size());

        return "dashboard";
    }
}