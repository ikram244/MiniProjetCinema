package com.cinema.cinemaapp.repositories;

import com.cinema.cinemaapp.entities.Billet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface BilletRepository extends JpaRepository<Billet, Long> {

    List<Billet> findByStatut(String statut);

    @Query("SELECT b.seance.film.titre, SUM(b.prix) FROM Billet b GROUP BY b.seance.film.titre")
    List<Object[]> recettesParFilm();

    @Query("SELECT b.seance.id, COUNT(b) FROM Billet b WHERE b.statut = 'valide' GROUP BY b.seance.id")
    List<Object[]> billetsVendusParSeance();
}