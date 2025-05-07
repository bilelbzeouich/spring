// ActeurController.java
package com.gestion.filmotheque.controller;

import com.gestion.filmotheque.entities.Acteur;
import com.gestion.filmotheque.service.IServiceActeur;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/acteur/")
@AllArgsConstructor
public class ActeurController {
    private final IServiceActeur iServiceActeur;

    @GetMapping("all")
    public String allActeurs(Model model) {
        model.addAttribute("acteurs", iServiceActeur.findAllActeurs());
        return "acteurs";
    }

    @GetMapping("new")
    public String newActeurForm(Model model) {
        model.addAttribute("acteur", new Acteur());
        return "add-acteur";
    }

    @PostMapping("add")
    public String addActeur(@ModelAttribute Acteur acteur) {
        iServiceActeur.createActeur(acteur);
        return "redirect:/acteur/all";
    }

    @GetMapping("edit/{id}")
    public String editActeurForm(@PathVariable int id, Model model) {
        Acteur acteur = iServiceActeur.findActeurById(id);
        model.addAttribute("acteur", acteur);
        return "edit-acteur";
    }

    @PostMapping("update")
    public String updateActeur(@ModelAttribute Acteur acteur) {
        iServiceActeur.updateActeur(acteur);
        return "redirect:/acteur/all";
    }

    @GetMapping("delete/{id}")
    public String deleteActeur(@PathVariable int id) {
        iServiceActeur.deleteActeur(id);
        return "redirect:/acteur/all";
    }
}