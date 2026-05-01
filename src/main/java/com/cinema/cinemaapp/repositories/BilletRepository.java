package com.cinema.cinemaapp.repositories;

import com.cinema.cinemaapp.entities.Billet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BilletRepository extends JpaRepository<Billet, Long> {

    /** Filtrer par statut */
    List<Billet> findByStatutIgnoreCase(String statut);

    /** Billets d'une séance donnée */
    List<Billet> findBySeanceId(Long seanceId);

    /** Billets d'un film donné (via séance) */
    @Query("SELECT b FROM Billet b WHERE b.seance.film.id = :filmId")
    List<Billet> findByFilmId(@Param("filmId") Long filmId);

    /** Filtrer par séance et statut */
    List<Billet> findBySeanceIdAndStatutIgnoreCase(Long seanceId, String statut);

    /** Recette totale (billets VENDUS) */
    @Query("SELECT COALESCE(SUM(b.prix), 0) FROM Billet b WHERE b.statut = 'VENDU'")
    Double totalRecettes();

    /** Nombre de billets par statut */
    @Query("SELECT b.statut, COUNT(b) FROM Billet b GROUP BY b.statut")
    List<Object[]> countParStatut();
}