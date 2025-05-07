// RestActeurController.java
package com.gestion.filmotheque.controller;

import com.gestion.filmotheque.entities.Acteur;
import com.gestion.filmotheque.service.IServiceActeur;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/acteurs")
@AllArgsConstructor
public class RestActeurController {
    private final IServiceActeur iServiceActeur;

    @GetMapping("")
    public ResponseEntity<List<Acteur>> getAllActeurs() {
        return ResponseEntity.ok(iServiceActeur.findAllActeurs());
    }

    @PostMapping("")
    public ResponseEntity<Acteur> addActeur(@RequestBody Acteur acteur) {
        return new ResponseEntity<>(iServiceActeur.createActeur(acteur), HttpStatus.CREATED);
    }


}