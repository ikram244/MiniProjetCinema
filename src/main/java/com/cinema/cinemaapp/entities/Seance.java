package com.cinema.cinemaapp.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "seance")
public class Seance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateHeure;
    private String salle;
    private String version;

    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;

    @OneToMany(mappedBy = "seance")
    private List<Billet> billets;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getDateHeure() { return dateHeure; }
    public void setDateHeure(LocalDateTime dateHeure) { this.dateHeure = dateHeure; }
    public String getSalle() { return salle; }
    public void setSalle(String salle) { this.salle = salle; }
    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }
    public Film getFilm() { return film; }
    public void setFilm(Film film) { this.film = film; }
    public List<Billet> getBillets() { return billets; }
    public void setBillets(List<Billet> billets) { this.billets = billets; }
}