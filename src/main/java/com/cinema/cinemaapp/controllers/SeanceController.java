package com.cinema.cinemaapp.controllers;

import com.cinema.cinemaapp.entities.Seance;
import com.cinema.cinemaapp.services.FilmService;
import com.cinema.cinemaapp.services.SeanceService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
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

    // ===== LISTE + FILTRAGE =====

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
        // Ajouter les stats de remplissage pour la section statistiques
        model.addAttribute("tauxRemplissage",   seanceService.getTauxRemplissage());
        return "seances/list";
    }

    // ===== STATISTIQUES =====

    @GetMapping("/statistiques")
    public String statistiques(Model model) {
        List<Map<String, Object>> stats = seanceService.getTauxRemplissage();
        model.addAttribute("stats", stats);
        return "seances/statistiques";
    }

    // ===== FORMULAIRE CRÉATION =====

    @GetMapping("/nouvelle")
    public String showCreateForm(Model model) {
        model.addAttribute("seance",     new Seance());
        model.addAttribute("films",      filmService.findAll());
        model.addAttribute("versions",   List.of("VF", "VOST", "VFQ", "VO"));
        model.addAttribute("titre_page", "Nouvelle séance");
        return "seances/form";
    }

    // ===== ENREGISTREMENT =====

    @PostMapping
    public String save(@Valid @ModelAttribute("seance") Seance seance,
                       BindingResult result,
                       @RequestParam(required = false) Long filmId,
                       Model model,
                       RedirectAttributes redirectAttributes) {

        if (filmId != null) {
            filmService.findById(filmId).ifPresent(seance::setFilm);
        }

        if (result.hasErrors() || seance.getFilm() == null) {
            if (seance.getFilm() == null) {
                result.rejectValue("film", "film.requis", "Veuillez sélectionner un film");
            }
            model.addAttribute("films",      filmService.findAll());
            model.addAttribute("versions",   List.of("VF", "VOST", "VFQ", "VO"));
            model.addAttribute("titre_page", seance.getId() == null ? "Nouvelle séance" : "Modifier la séance");
            return "seances/form";
        }

        seanceService.save(seance);
        redirectAttributes.addFlashAttribute("success", "Séance enregistrée avec succès.");
        return "redirect:/seances";
    }

    // ===== FORMULAIRE MODIFICATION =====

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

    // ===== SUPPRESSION =====

    @PostMapping("/{id}/supprimer")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        seanceService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Séance supprimée.");
        return "redirect:/seances";
    }
}