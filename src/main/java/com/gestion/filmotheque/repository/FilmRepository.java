package com.gestion.filmotheque.repository;
import com.gestion.filmotheque.entities.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FilmRepository extends JpaRepository<Film,Integer> {
    List<Film> findByTitreContainingIgnoreCase(String titre);
    List<Film> findAllByOrderByTitreAsc();
    List<Film> findByCategorieId(int idCategorie);
}
