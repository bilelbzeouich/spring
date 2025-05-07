package com.gestion.filmotheque.controller;

import com.gestion.filmotheque.entities.Film;
import com.gestion.filmotheque.service.IServiceFilm;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/films")
@AllArgsConstructor
public class RestFilmController {
    IServiceFilm iserviceFilm;

    @GetMapping("")
    public List<Film> getAllFilms() {
        return iserviceFilm.findAllFilms();
    }

    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Film Found"),
            @ApiResponse(responseCode = "404", description = "Film Not Found")
    })
    public ResponseEntity<?> find(@PathVariable int id) {
        if (!iserviceFilm.filmExist(id))
            return new ResponseEntity<>("Film not Found!", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(iserviceFilm.findFilmById(id), HttpStatus.FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFilm(@PathVariable int id) {
        try {
            iserviceFilm.deleteFilm(id);
            return ResponseEntity.ok("Film supprimé avec succès.");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Film non trouvé.");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Film> updateFilm(@RequestBody Film film) {
        Film updated = iserviceFilm.updateFilm(film);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @GetMapping("/find")
    public List<Film> findByTitre(@RequestParam String keyword) {
        return iserviceFilm.searchByTitre(keyword);
    }

    @GetMapping("/filter")
    public List<Film> filterByCategorie(@RequestParam int idCategorie) {
        if (idCategorie == 0) {
            return iserviceFilm.findAllFilms();
        } else {
            return iserviceFilm.findFilmsByCategorie(idCategorie);
        }
    }

    @PostMapping("/add")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Film added") })
    public ResponseEntity<Film> add(@RequestBody Film film) {
        return new ResponseEntity<>(iserviceFilm.createFilm(film), HttpStatus.CREATED);
    }

    @PostMapping(value = "/create-with-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Film> createFilmWithImage(
            @RequestPart("film") Film film,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            Film created = iserviceFilm.createFilmWithImage(film, image);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping(value = "/update-with-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Film> updateFilmWithImage(
            @RequestPart("film") Film film,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            if (!iserviceFilm.filmExist(film.getId())) {
                return ResponseEntity.notFound().build();
            }
            Film updated = iserviceFilm.updateFilmWithImage(film, image);
            return ResponseEntity.ok(updated);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
