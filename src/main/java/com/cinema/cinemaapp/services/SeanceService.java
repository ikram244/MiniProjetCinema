package com.cinema.cinemaapp.services;

import com.cinema.cinemaapp.entities.Seance;
import com.cinema.cinemaapp.repositories.SeanceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class SeanceService {

    private final SeanceRepository seanceRepository;

    public SeanceService(SeanceRepository seanceRepository) {
        this.seanceRepository = seanceRepository;
    }

    // ===== CRUD =====

    public List<Seance> findAll() {
        return seanceRepository.findAll();
    }

    public Optional<Seance> findById(Long id) {
        return seanceRepository.findById(id);
    }

    public Seance save(Seance seance) {
        return seanceRepository.save(seance);
    }

    public void deleteById(Long id) {
        seanceRepository.deleteById(id);
    }

    // ===== Filtrage =====

    public List<Seance> findBySalle(String salle) {
        if (salle == null || salle.isBlank()) return findAll();
        return seanceRepository.findBySalleIgnoreCase(salle);
    }

    public List<Seance> findByDate(LocalDate date) {
        if (date == null) return findAll();
        LocalDateTime debut = date.atStartOfDay();
        LocalDateTime fin   = date.atTime(23, 59, 59);
        return seanceRepository.findByDateHeureBetween(debut, fin);
    }

    public List<Seance> findBySalleAndDate(String salle, LocalDate date) {
        boolean hasSalle = salle != null && !salle.isBlank();
        boolean hasDate  = date != null;

        if (hasSalle && hasDate) {
            return seanceRepository.findBySalleIgnoreCaseAndDateHeureBetween(
                    salle, date.atStartOfDay(), date.atTime(23, 59, 59));
        } else if (hasSalle) {
            return seanceRepository.findBySalleIgnoreCase(salle);
        } else if (hasDate) {
            return findByDate(date);
        }
        return findAll();
    }

    public List<Seance> findByFilmId(Long filmId) {
        return seanceRepository.findByFilmId(filmId);
    }

    public List<String> findDistinctSalles() {
        return seanceRepository.findDistinctSalles();
    }

    // ===== Statistiques =====

    /**
     * Taux de remplissage par séance
     * Retourne: seanceId, salle, dateHeure, filmTitre, nbVendus, capacite, taux%
     */
    public List<Map<String, Object>> getTauxRemplissage() {
        List<Object[]> rows = seanceRepository.findTauxRemplissage();
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] row : rows) {
            Map<String, Object> map = new LinkedHashMap<>();
            long capacite = ((Number) row[5]).longValue();
            long nbVendus = ((Number) row[4]).longValue();
            double taux   = capacite > 0 ? (nbVendus * 100.0 / capacite) : 0;

            map.put("seanceId",  row[0]);
            map.put("salle",     row[1]);
            map.put("dateHeure", row[2]);
            map.put("film",      row[3]);
            map.put("nbVendus",  nbVendus);
            map.put("capacite",  capacite);
            map.put("taux",      String.format("%.1f", taux));
            result.add(map);
        }
        return result;
    }

    public List<Seance> findSeancesAVenir() {
        return seanceRepository.findSeancesAVenir(LocalDateTime.now());
    }
}