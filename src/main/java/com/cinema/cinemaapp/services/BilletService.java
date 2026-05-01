package com.cinema.cinemaapp.services;

import com.cinema.cinemaapp.entities.Billet;
import com.cinema.cinemaapp.repositories.BilletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class BilletService {

    private final BilletRepository billetRepository;

    public BilletService(BilletRepository billetRepository) {
        this.billetRepository = billetRepository;
    }

    // ===== CRUD =====

    public List<Billet> findAll() {
        return billetRepository.findAll();
    }

    public Optional<Billet> findById(Long id) {
        return billetRepository.findById(id);
    }

    public Billet save(Billet billet) {
        return billetRepository.save(billet);
    }

    public void deleteById(Long id) {
        billetRepository.deleteById(id);
    }

    // ===== Vente / Annulation =====

    public Billet annulerBillet(Long id) {
        Billet billet = billetRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Billet introuvable : " + id));
        billet.setStatut("ANNULE");
        return billetRepository.save(billet);
    }

    public Billet confirmerBillet(Long id) {
        Billet billet = billetRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Billet introuvable : " + id));
        billet.setStatut("VENDU");
        return billetRepository.save(billet);
    }

    // ===== Filtrage =====

    public List<Billet> findByStatut(String statut) {
        if (statut == null || statut.isBlank()) return findAll();
        return billetRepository.findByStatutIgnoreCase(statut);
    }

    public List<Billet> findBySeanceId(Long seanceId) {
        return billetRepository.findBySeanceId(seanceId);
    }

    public List<Billet> findByFilmId(Long filmId) {
        return billetRepository.findByFilmId(filmId);
    }

    // ===== Statistiques =====

    public Double totalRecettes() {
        Double total = billetRepository.totalRecettes();
        return total != null ? total : 0.0;
    }

    /**
     * Répartition par statut : Map<statut, count>
     */
    public Map<String, Long> getStatutDistribution() {
        List<Object[]> rows = billetRepository.countParStatut();
        Map<String, Long> map = new LinkedHashMap<>();
        for (Object[] row : rows) {
            map.put((String) row[0], (Long) row[1]);
        }
        return map;
    }

    public long countAll() {
        return billetRepository.count();
    }

    public List<String> getAllStatuts() {
        return List.of("VENDU", "RESERVE", "ANNULE");
    }
}