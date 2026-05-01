package com.cinema.cinemaapp.services;

import com.cinema.cinemaapp.entities.Film;
import com.cinema.cinemaapp.repositories.FilmRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class FilmService {

    private final FilmRepository filmRepository;

    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    // ===== CRUD =====

    public List<Film> findAll() {
        return filmRepository.findAll();
    }

    public Optional<Film> findById(Long id) {
        return filmRepository.findById(id);
    }

    public Film save(Film film) {
        return filmRepository.save(film);
    }

    public void deleteById(Long id) {
        filmRepository.deleteById(id);
    }

    // ===== Filtrage =====

    public List<Film> findByGenre(String genre) {
        if (genre == null || genre.isBlank()) return findAll();
        return filmRepository.findByGenreIgnoreCase(genre);
    }

    public List<Film> rechercherParTitre(String titre) {
        if (titre == null || titre.isBlank()) return findAll();
        return filmRepository.findByTitreContainingIgnoreCase(titre);
    }

    public List<String> findDistinctGenres() {
        return filmRepository.findDistinctGenres();
    }

    // ===== Statistiques =====

    /**
     * Recettes par film : Map<filmTitre, recette>
     */
    public List<Map<String, Object>> getRecettesParFilm() {
        List<Object[]> rows = filmRepository.findRecettesParFilm();
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] row : rows) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("filmId",   row[0]);
            map.put("titre",    row[1]);
            map.put("recettes", row[2]);
            result.add(map);
        }
        return result;
    }

    public boolean existsById(Long id) {
        return filmRepository.existsById(id);
    }
}