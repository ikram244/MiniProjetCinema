package com.cinema.cinemaapp.repositories;

import com.cinema.cinemaapp.entities.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {

    /** Filtrage par genre (insensible à la casse) */
    List<Film> findByGenreIgnoreCase(String genre);

    /** Recherche par titre partiel */
    List<Film> findByTitreContainingIgnoreCase(String titre);

    /** Liste distincte des genres disponibles */
    @Query("SELECT DISTINCT f.genre FROM Film f ORDER BY f.genre")
    List<String> findDistinctGenres();

    /** Recettes par film : somme des prix des billets VENDUS */
    @Query("""
        SELECT f.id, f.titre, COALESCE(SUM(b.prix), 0)
        FROM Film f
        LEFT JOIN f.seances s
        LEFT JOIN s.billets b ON b.statut = 'VENDU'
        GROUP BY f.id, f.titre
        ORDER BY f.titre
    """)
    List<Object[]> findRecettesParFilm();
}