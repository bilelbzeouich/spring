package com.gestion.filmotheque.service;

import com.gestion.filmotheque.entities.Film;
import com.gestion.filmotheque.repository.FilmRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class ServiceFilm implements IServiceFilm {

    FilmRepository filmRepository;
    FileStorageService fileStorageService;

    @Override
    public Film createFilm(Film film) {
        return filmRepository.save(film);
    }

    @Override
    public Film createFilmWithImage(Film film, MultipartFile image) throws IOException {
        if (image != null && !image.isEmpty()) {
            String fileName = fileStorageService.saveFile(image);
            film.setImagePath(fileName);
        }
        return filmRepository.save(film);
    }

    @Override
    public Film findFilmById(int id) {
        return filmRepository.findById(id).get();
    }

    @Override
    public List<Film> findAllFilms() {
        return filmRepository.findAllByOrderByTitreAsc();
    }

    @Override
    public Film updateFilm(Film film) {
        return filmRepository.save(film);
    }

    @Override
    public Film updateFilmWithImage(Film film, MultipartFile image) throws IOException {
        if (image != null && !image.isEmpty()) {
            // Delete old image if exists
            Film existingFilm = filmRepository.findById(film.getId()).orElse(null);
            if (existingFilm != null && existingFilm.getImagePath() != null) {
                fileStorageService.deleteFile(existingFilm.getImagePath());
            }

            // Save new image
            String fileName = fileStorageService.saveFile(image);
            film.setImagePath(fileName);
        } else {
            // Keep existing image path if no new image is uploaded
            Film existingFilm = filmRepository.findById(film.getId()).orElse(null);
            if (existingFilm != null) {
                film.setImagePath(existingFilm.getImagePath());
            }
        }
        return filmRepository.save(film);
    }

    @Override
    public void deleteFilm(int id) {
        // Delete image file if exists
        Film film = filmRepository.findById(id).orElse(null);
        if (film != null && film.getImagePath() != null) {
            fileStorageService.deleteFile(film.getImagePath());
        }

        filmRepository.deleteById(id);
    }

    @Override
    public List<Film> searchByTitre(String keyword) {
        return filmRepository.findByTitreContainingIgnoreCase(keyword);
    }

    @Override
    public List<Film> findFilmsByCategorie(int idCategorie) {
        return filmRepository.findByCategorieId(idCategorie);
    }

    @Override
    public Boolean filmExist(int id) {
        return filmRepository.existsById(id);
    }
}
