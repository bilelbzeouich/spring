package com.gestion.filmotheque.service;

import com.gestion.filmotheque.entities.Film;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IServiceFilm {
    public Film createFilm(Film film);

    public Film createFilmWithImage(Film film, MultipartFile image) throws IOException;

    public Film findFilmById(int id);

    public List<Film> findAllFilms();

    public Film updateFilm(Film film);

    public Film updateFilmWithImage(Film film, MultipartFile image) throws IOException;

    public void deleteFilm(int id);

    public List<Film> searchByTitre(String keyword);

    List<Film> findFilmsByCategorie(int idCategorie);

    Boolean filmExist(int id);
}
