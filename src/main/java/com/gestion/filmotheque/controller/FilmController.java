package com.gestion.filmotheque.controller;

import com.gestion.filmotheque.entities.Film;
import com.gestion.filmotheque.service.IServiceCategorie;
import com.gestion.filmotheque.service.IServiceFilm;
import com.gestion.filmotheque.service.ServiceActeur;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/film/")
@AllArgsConstructor
public class FilmController {

    private final ServiceActeur serviceActeur;
    IServiceFilm iServiceFilm;
    IServiceCategorie iServiceCategorie;

    @GetMapping("all")
    public String allFilms(Model model) {
        model.addAttribute("films", iServiceFilm.findAllFilms());
        model.addAttribute("categories", iServiceCategorie.findAllCategories());
        model.addAttribute("idcat", 0);
        return "affiche";
    }

    @GetMapping("search")
    public String searchFilms(@RequestParam("keyword") String keyword, Model model) {
        model.addAttribute("films", iServiceFilm.searchByTitre(keyword));
        return "affiche";
    }

    @GetMapping("details/{id}")
    public String filmDetails(@PathVariable int id, Model model) {
        Film film = iServiceFilm.findFilmById(id);
        model.addAttribute("film", film);
        return "film-details";
    }

    @GetMapping("new")
    public String afficheNewForm(Model model) {
        model.addAttribute("categories", iServiceCategorie.findAllCategories());
        model.addAttribute("acteur", serviceActeur.findAllActeurs());
        return "ajout";
    }

    @PostMapping("add")
    public String add(@ModelAttribute Film f, @RequestParam("filmImage") MultipartFile image) {
        try {
            iServiceFilm.createFilmWithImage(f, image);
        } catch (IOException e) {
            // Handle the exception (could add flash attributes for error message)
            e.printStackTrace();
        }
        return "redirect:/film/all";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable int id) {
        iServiceFilm.deleteFilm(id);
        return "redirect:/film/all";
    }

    @GetMapping("edit/{id}")
    public String editFilmForm(@PathVariable int id, Model model) {
        Film film = iServiceFilm.findFilmById(id);
        model.addAttribute("film", film);
        model.addAttribute("categories", iServiceCategorie.findAllCategories());
        model.addAttribute("acteur", serviceActeur.findAllActeurs());
        return "update";
    }

    @PostMapping("update")
    public String update(@ModelAttribute Film f,
            @RequestParam(value = "filmImage", required = false) MultipartFile image) {
        try {
            iServiceFilm.updateFilmWithImage(f, image);
        } catch (IOException e) {
            // Handle the exception
            e.printStackTrace();
        }
        return "redirect:/film/all";
    }

    @PostMapping("filter")
    public String filterByCategorie(@RequestParam("idcat") int idcat, Model model) {
        List<Film> films;

        if (idcat == 0) {
            films = iServiceFilm.findAllFilms();
        } else {
            films = iServiceFilm.findFilmsByCategorie(idcat);
        }

        model.addAttribute("films", films);
        model.addAttribute("categories", iServiceCategorie.findAllCategories());
        model.addAttribute("idcat", idcat);
        return "affiche";
    }
}
