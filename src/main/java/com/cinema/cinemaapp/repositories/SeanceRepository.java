package com.cinema.cinemaapp.repositories;

import com.cinema.cinemaapp.entities.Seance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SeanceRepository extends JpaRepository<Seance, Long> {
    List<Seance> findBySalle(String salle);
    List<Seance> findByFilmId(Long filmId);
}