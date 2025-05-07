package com.gestion.filmotheque.controller;

import com.gestion.filmotheque.entities.Categorie;
import com.gestion.filmotheque.service.IServiceCategorie;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categorie/")
@AllArgsConstructor
public class CategorieController {
    private final IServiceCategorie iServiceCategorie;

    @GetMapping("all")
    public String allCategories(Model model) {
        model.addAttribute("categories", iServiceCategorie.findAllCategories());
        return "categories";
    }

    @GetMapping("new")
    public String newCategoryForm(Model model) {
        model.addAttribute("categorie", new Categorie());
        return "add-categorie";
    }

    @PostMapping("add")
    public String addCategory(@ModelAttribute Categorie categorie) {
        iServiceCategorie.createCategorie(categorie);
        return "redirect:/categorie/all";
    }

    @GetMapping("edit/{id}")
    public String editCategoryForm(@PathVariable int id, Model model) {
        Categorie categorie = iServiceCategorie.findCategorieById(id);
        model.addAttribute("categorie", categorie);
        return "edit-categorie";
    }

    @PostMapping("update")
    public String updateCategory(@ModelAttribute Categorie categorie) {
        iServiceCategorie.updateCategorie(categorie);
        return "redirect:/categorie/all";
    }
}