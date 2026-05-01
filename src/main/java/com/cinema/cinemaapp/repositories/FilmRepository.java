package com.cinema.cinemaapp.repositories;

import com.cinema.cinemaapp.entities.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FilmRepository extends JpaRepository<Film, Long> {
    List<Film> findByGenre(String genre);
}