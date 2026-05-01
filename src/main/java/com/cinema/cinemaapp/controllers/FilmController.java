package com.cinema.cinemaapp.controllers;

import com.cinema.cinemaapp.entities.Film;
import com.cinema.cinemaapp.services.FilmService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    // ===== LISTE + FILTRAGE =====

    @GetMapping
    public String list(@RequestParam(required = false) String genre,
                       @RequestParam(required = false) String titre,
                       Model model) {
        List<Film> films;

        if (genre != null && !genre.isBlank()) {
            films = filmService.findByGenre(genre);
        } else if (titre != null && !titre.isBlank()) {
            films = filmService.rechercherParTitre(titre);
        } else {
            films = filmService.findAll();
        }

        model.addAttribute("films",           films);
        model.addAttribute("genres",          filmService.findDistinctGenres());
        model.addAttribute("genreSelectionne", genre);
        model.addAttribute("titreRecherche",   titre);
        return "films/list";
    }

    // ===== STATISTIQUES =====

    @GetMapping("/statistiques")
    public String statistiques(Model model) {
        List<Map<String, Object>> recettes = filmService.getRecettesParFilm();
        model.addAttribute("recettes", recettes);
        return "films/statistiques";
    }

    // ===== FORMULAIRE CRÉATION =====

    @GetMapping("/nouveau")
    public String showCreateForm(Model model) {
        model.addAttribute("film",       new Film());
        model.addAttribute("genres",     List.of("Action", "Comédie", "Drame", "Horreur",
                "Science-Fiction", "Animation", "Documentaire", "Thriller"));
        model.addAttribute("titre_page", "Nouveau film");
        return "films/form";
    }

    // ===== ENREGISTREMENT =====

    @PostMapping
    public String save(@Valid @ModelAttribute("film") Film film,
                       BindingResult result,
                       Model model,
                       RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("genres", List.of("Action", "Comédie", "Drame", "Horreur",
                    "Science-Fiction", "Animation", "Documentaire", "Thriller"));
            model.addAttribute("titre_page", film.getId() == null ? "Nouveau film" : "Modifier le film");
            return "films/form";
        }
        filmService.save(film);
        redirectAttributes.addFlashAttribute("success",
                "Film « " + film.getTitre() + " » enregistré avec succès.");
        return "redirect:/films";
    }

    // ===== FORMULAIRE MODIFICATION =====

    @GetMapping("/{id}/modifier")
    public String showEditForm(@PathVariable Long id, Model model) {
        Film film = filmService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Film introuvable : " + id));
        model.addAttribute("film",       film);
        model.addAttribute("genres",     List.of("Action", "Comédie", "Drame", "Horreur",
                "Science-Fiction", "Animation", "Documentaire", "Thriller"));
        model.addAttribute("titre_page", "Modifier le film");
        return "films/form";
    }

    // ===== SUPPRESSION =====

    @PostMapping("/{id}/supprimer")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        filmService.findById(id).ifPresent(f -> {
            filmService.deleteById(id);
            redirectAttributes.addFlashAttribute("success",
                    "Film « " + f.getTitre() + " » supprimé.");
        });
        return "redirect:/films";
    }
}