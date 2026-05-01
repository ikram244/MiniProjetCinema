package com.cinema.cinemaapp.entities;
//import com.cinema.cinemaapp.entities.Seance ;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "film")
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private String genre;
    private int dureeMin;

    @OneToMany(mappedBy = "film")
    private List<Seance> seances;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public int getDureeMin() { return dureeMin; }
    public void setDureeMin(int dureeMin) { this.dureeMin = dureeMin; }
    public List<Seance> getSeances() { return seances; }
    public void setSeances(List<Seance> seances) { this.seances = seances; }
}