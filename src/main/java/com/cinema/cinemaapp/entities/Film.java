package com.cinema.cinemaapp.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "films")
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le titre est obligatoire")
    @Size(max = 150, message = "Le titre ne doit pas dépasser 150 caractères")
    @Column(nullable = false)
    private String titre;

    @NotBlank(message = "Le genre est obligatoire")
    @Column(nullable = false)
    private String genre;

    @Min(value = 1, message = "La durée doit être positive")
    @Column(nullable = false)
    private int dureeMin;

    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL)
    private List<Seance> seances;

    // ===== Constructeurs =====

    public Film() {}

    public Film(String titre, String genre, int dureeMin) {
        this.titre   = titre;
        this.genre   = genre;
        this.dureeMin = dureeMin;
    }

    // ===== Getters / Setters =====

    public Long getId()                   { return id; }
    public void setId(Long id)            { this.id = id; }

    public String getTitre()              { return titre; }
    public void setTitre(String titre)    { this.titre = titre; }

    public String getGenre()              { return genre; }
    public void setGenre(String genre)    { this.genre = genre; }

    public int getDureeMin()              { return dureeMin; }
    public void setDureeMin(int dureeMin) { this.dureeMin = dureeMin; }

    public List<Seance> getSeances()      { return seances; }
    public void setSeances(List<Seance> seances) { this.seances = seances; }

    @Override
    public String toString() {
        return "Film{id=" + id + ", titre='" + titre + "', genre='" + genre +
                "', dureeMin=" + dureeMin + "}";
    }
}