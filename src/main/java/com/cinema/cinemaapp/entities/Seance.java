package com.cinema.cinemaapp.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "seances")
public class Seance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La date et heure sont obligatoires")
    @Column(nullable = false)
    private LocalDateTime dateHeure;

    @NotBlank(message = "La salle est obligatoire")
    @Column(nullable = false)
    private String salle;

    @NotBlank(message = "La version est obligatoire")
    @Column(nullable = false)
    private String version; // VF ou VOST

    @Min(value = 1, message = "La capacité doit être au moins 1")
    @Column(nullable = false)
    private int capacite = 100;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id", nullable = false)
    @NotNull(message = "Le film est obligatoire")
    private Film film;

    @OneToMany(mappedBy = "seance", cascade = CascadeType.ALL)
    private List<Billet> billets;

    // ===== Constructeurs =====

    public Seance() {}

    public Seance(LocalDateTime dateHeure, String salle, String version, int capacite, Film film) {
        this.dateHeure = dateHeure;
        this.salle     = salle;
        this.version   = version;
        this.capacite  = capacite;
        this.film      = film;
    }

    // ===== Méthode utilitaire =====

    /**
     * Nombre de billets vendus (statut = VENDU)
     */
    public long getNombreBilletsVendus() {
        if (billets == null) return 0;
        return billets.stream()
                .filter(b -> "VENDU".equalsIgnoreCase(b.getStatut()))
                .count();
    }

    /**
     * Taux de remplissage en pourcentage
     */
    public double getTauxRemplissage() {
        if (capacite == 0) return 0;
        return (getNombreBilletsVendus() * 100.0) / capacite;
    }

    // ===== Getters / Setters =====

    public Long getId()                           { return id; }
    public void setId(Long id)                    { this.id = id; }

    public LocalDateTime getDateHeure()           { return dateHeure; }
    public void setDateHeure(LocalDateTime dh)    { this.dateHeure = dh; }

    public String getSalle()                      { return salle; }
    public void setSalle(String salle)            { this.salle = salle; }

    public String getVersion()                    { return version; }
    public void setVersion(String version)        { this.version = version; }

    public int getCapacite()                      { return capacite; }
    public void setCapacite(int capacite)         { this.capacite = capacite; }

    public Film getFilm()                         { return film; }
    public void setFilm(Film film)                { this.film = film; }

    public List<Billet> getBillets()              { return billets; }
    public void setBillets(List<Billet> billets)  { this.billets = billets; }

    @Override
    public String toString() {
        return "Seance{id=" + id + ", salle='" + salle + "', version='" + version +
                "', dateHeure=" + dateHeure + "}";
    }
}