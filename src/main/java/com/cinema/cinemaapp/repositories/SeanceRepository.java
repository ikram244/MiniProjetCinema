package com.cinema.cinemaapp.repositories;

import com.cinema.cinemaapp.entities.Seance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SeanceRepository extends JpaRepository<Seance, Long> {

    /** Filtrer par salle */
    List<Seance> findBySalleIgnoreCase(String salle);

    /** Filtrer par date (entre deux dates) */
    List<Seance> findByDateHeureBetween(LocalDateTime debut, LocalDateTime fin);

    /** Filtrer par salle et date */
    List<Seance> findBySalleIgnoreCaseAndDateHeureBetween(
            String salle, LocalDateTime debut, LocalDateTime fin);

    /** Séances d'un film donné */
    List<Seance> findByFilmId(Long filmId);

    /** Liste distincte des salles */
    @Query("SELECT DISTINCT s.salle FROM Seance s ORDER BY s.salle")
    List<String> findDistinctSalles();

    /** Taux de remplissage : nb billets VENDUS / capacité */
    @Query("""
        SELECT s.id, s.salle, s.dateHeure, f.titre,
               COUNT(b.id), s.capacite
        FROM Seance s
        JOIN s.film f
        LEFT JOIN s.billets b ON b.statut = 'VENDU'
        GROUP BY s.id, s.salle, s.dateHeure, f.titre, s.capacite
        ORDER BY s.dateHeure DESC
    """)
    List<Object[]> findTauxRemplissage();

    /** Recherche par version (VF/VOST) */
    List<Seance> findByVersionIgnoreCase(String version);

    /** Séances à venir */
    @Query("SELECT s FROM Seance s WHERE s.dateHeure > :now ORDER BY s.dateHeure ASC")
    List<Seance> findSeancesAVenir(@Param("now") LocalDateTime now);
}