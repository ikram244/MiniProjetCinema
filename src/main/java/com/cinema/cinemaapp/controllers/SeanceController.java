package com.cinema.cinemaapp.controllers;

import com.cinema.cinemaapp.entities.Seance;
import com.cinema.cinemaapp.services.FilmService;
import com.cinema.cinemaapp.services.SeanceService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/seances")
public class SeanceController {

    private final SeanceService seanceService;
    private final FilmService   filmService;

    public SeanceController(SeanceService seanceService, FilmService filmService) {
        this.seanceService = seanceService;
        this.filmService   = filmService;
    }

    @GetMapping
    public String list(@RequestParam(required = false) String salle,
                       @RequestParam(required = false)
                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                       Model model) {
        List<Seance> seances = seanceService.findBySalleAndDate(salle, date);
        model.addAttribute("seances",           seances);
        model.addAttribute("salles",            seanceService.findDistinctSalles());
        model.addAttribute("salleSelectionnee", salle);
        model.addAttribute("dateSelectionnee",  date);
        model.addAttribute("tauxRemplissage",   seanceService.getTauxRemplissage());
        return "seances/list";
    }

    @GetMapping("/statistiques")
    public String statistiques(Model model) {
        List<Map<String, Object>> stats = seanceService.getTauxRemplissage();
        model.addAttribute("stats", stats);
        return "seances/statistiques";
    }

    @GetMapping("/nouvelle")
    public String showCreateForm(Model model) {
        model.addAttribute("seance",     new Seance());
        model.addAttribute("films",      filmService.findAll());
        model.addAttribute("versions",   List.of("VF", "VOST", "VFQ", "VO"));
        model.addAttribute("titre_page", "Nouvelle séance");
        return "seances/form";
    }

    // ===== FIX : suppression @Valid, assignation film AVANT validation =====
    @PostMapping
    public String save(@ModelAttribute("seance") Seance seance,
                       @RequestParam(required = false) Long filmId,
                       @RequestParam(required = false) String salle,
                       @RequestParam(required = false) String version,
                       @RequestParam(required = false) String dateHeure,
                       @RequestParam(required = false) Integer capacite,
                       Model model,
                       RedirectAttributes redirectAttributes) {

        // 1 — Assigner le film en premier
        if (filmId != null) {
            filmService.findById(filmId).ifPresent(seance::setFilm);
        }

        // 2 — Assigner les autres champs manuellement
        if (salle != null && !salle.isBlank())     seance.setSalle(salle);
        if (version != null && !version.isBlank()) seance.setVersion(version);
        if (capacite != null)                       seance.setCapacite(capacite);
        if (dateHeure != null && !dateHeure.isBlank()) {
            seance.setDateHeure(LocalDateTime.parse(dateHeure));
        }

        // 3 — Validation manuelle
        boolean hasError = false;

        if (seance.getFilm() == null) {
            model.addAttribute("erreurFilm", "Veuillez sélectionner un film");
            hasError = true;
        }
        if (seance.getSalle() == null || seance.getSalle().isBlank()) {
            model.addAttribute("erreurSalle", "La salle est obligatoire");
            hasError = true;
        }
        if (seance.getVersion() == null || seance.getVersion().isBlank()) {
            model.addAttribute("erreurVersion", "La version est obligatoire");
            hasError = true;
        }
        if (seance.getDateHeure() == null) {
            model.addAttribute("erreurDate", "La date et l'heure sont obligatoires");
            hasError = true;
        }

        if (hasError) {
            model.addAttribute("films",      filmService.findAll());
            model.addAttribute("versions",   List.of("VF", "VOST", "VFQ", "VO"));
            model.addAttribute("titre_page", seance.getId() == null ? "Nouvelle séance" : "Modifier la séance");
            return "seances/form";
        }

        // 4 — Sauvegarder
        seanceService.save(seance);
        redirectAttributes.addFlashAttribute("success", "Séance enregistrée avec succès.");
        return "redirect:/seances";
    }

    @GetMapping("/{id}/modifier")
    public String showEditForm(@PathVariable Long id, Model model) {
        Seance seance = seanceService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Séance introuvable : " + id));
        model.addAttribute("seance",     seance);
        model.addAttribute("films",      filmService.findAll());
        model.addAttribute("versions",   List.of("VF", "VOST", "VFQ", "VO"));
        model.addAttribute("titre_page", "Modifier la séance");
        return "seances/form";
    }

    @PostMapping("/{id}/supprimer")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        seanceService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Séance supprimée.");
        return "redirect:/seances";
    }
}