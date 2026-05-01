package com.cinema.cinemaapp.entities;
import com.cinema.cinemaapp.entities.Seance ;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "billet")
public class Billet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double prix;
    private String statut;
    private LocalDate dateAchat;

    @ManyToOne
    @JoinColumn(name = "seance_id")
    private Seance seance;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
    public LocalDate getDateAchat() { return dateAchat; }
    public void setDateAchat(LocalDate dateAchat) { this.dateAchat = dateAchat; }
    public Seance getSeance() { return seance; }
    public void setSeance(Seance seance) { this.seance = seance; }
}