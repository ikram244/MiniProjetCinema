package com.cinema.cinemaapp.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "billets")
public class Billet {

    public enum Statut { VENDU, RESERVE, ANNULE }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DecimalMin(value = "0.0", inclusive = false, message = "Le prix doit être positif")
    @Column(nullable = false)
    private double prix;

    @NotBlank(message = "Le statut est obligatoire")
    @Column(nullable = false)
    private String statut; // VENDU, RESERVE, ANNULE

    @Column(nullable = false)
    private LocalDate dateAchat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seance_id", nullable = false)
    @NotNull(message = "La séance est obligatoire")
    private Seance seance;

    // ===== Constructeurs =====

    public Billet() {
        this.dateAchat = LocalDate.now();
    }

    public Billet(double prix, String statut, Seance seance) {
        this.prix      = prix;
        this.statut    = statut;
        this.dateAchat = LocalDate.now();
        this.seance    = seance;
    }

    // ===== Getters / Setters =====

    public Long getId()                         { return id; }
    public void setId(Long id)                  { this.id = id; }

    public double getPrix()                     { return prix; }
    public void setPrix(double prix)            { this.prix = prix; }

    public String getStatut()                   { return statut; }
    public void setStatut(String statut)        { this.statut = statut; }

    public LocalDate getDateAchat()             { return dateAchat; }
    public void setDateAchat(LocalDate da)      { this.dateAchat = da; }

    public Seance getSeance()                   { return seance; }
    public void setSeance(Seance seance)        { this.seance = seance; }

    @Override
    public String toString() {
        return "Billet{id=" + id + ", prix=" + prix + ", statut='" + statut +
                "', dateAchat=" + dateAchat + "}";
    }
}