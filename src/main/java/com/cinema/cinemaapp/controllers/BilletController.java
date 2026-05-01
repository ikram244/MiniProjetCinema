package com.cinema.cinemaapp.controllers;

import com.cinema.cinemaapp.entities.Billet;
import com.cinema.cinemaapp.services.BilletService;
import com.cinema.cinemaapp.services.SeanceService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/billets")
public class BilletController {

    private final BilletService billetService;
    private final SeanceService seanceService;

    public BilletController(BilletService billetService, SeanceService seanceService) {
        this.billetService = billetService;
        this.seanceService = seanceService;
    }

    // ===== LISTE + FILTRAGE =====

    @GetMapping
    public String list(@RequestParam(required = false) String statut,
                       @RequestParam(required = false) Long seanceId,
                       Model model) {
        List<Billet> billets;

        if (seanceId != null) {
            billets = billetService.findBySeanceId(seanceId);
        } else if (statut != null && !statut.isBlank()) {
            billets = billetService.findByStatut(statut);
        } else {
            billets = billetService.findAll();
        }

        model.addAttribute("billets",           billets);
        model.addAttribute("statuts",           billetService.getAllStatuts());
        model.addAttribute("statutSelectionne", statut);
        model.addAttribute("seanceId",          seanceId);
        model.addAttribute("totalRecettes",     billetService.totalRecettes());
        model.addAttribute("repartition",       billetService.getStatutDistribution());
        return "billets/list";
    }

    // ===== FORMULAIRE VENTE (création) =====

    @GetMapping("/nouveau")
    public String showVenteForm(@RequestParam(required = false) Long seanceId,
                                Model model) {
        Billet billet = new Billet();
        billet.setStatut("VENDU");
        billet.setDateAchat(LocalDate.now());

        model.addAttribute("billet",           billet);
        model.addAttribute("seances",          seanceService.findAll());
        model.addAttribute("statuts",          billetService.getAllStatuts());
        model.addAttribute("titre_page",       "Vendre un billet");
        model.addAttribute("seanceIdPreselect", seanceId);
        return "billets/form";
    }

    // ===== ENREGISTREMENT =====

    @PostMapping
    public String save(@Valid @ModelAttribute("billet") Billet billet,
                       BindingResult result,
                       @RequestParam(required = false) Long seanceId,
                       Model model,
                       RedirectAttributes redirectAttributes) {

        if (seanceId != null) {
            seanceService.findById(seanceId).ifPresent(billet::setSeance);
        }

        if (result.hasErrors() || billet.getSeance() == null) {
            if (billet.getSeance() == null) {
                result.rejectValue("seance", "seance.requise", "Veuillez sélectionner une séance");
            }
            model.addAttribute("seances",    seanceService.findAll());
            model.addAttribute("statuts",    billetService.getAllStatuts());
            model.addAttribute("titre_page", billet.getId() == null ? "Vendre un billet" : "Modifier le billet");
            return "billets/form";
        }

        billetService.save(billet);
        redirectAttributes.addFlashAttribute("success",
                "Billet enregistré avec succès (statut : " + billet.getStatut() + ").");
        return "redirect:/billets";
    }

    // ===== FORMULAIRE MODIFICATION =====

    @GetMapping("/{id}/modifier")
    public String showEditForm(@PathVariable Long id, Model model) {
        Billet billet = billetService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Billet introuvable : " + id));
        model.addAttribute("billet",     billet);
        model.addAttribute("seances",    seanceService.findAll());
        model.addAttribute("statuts",    billetService.getAllStatuts());
        model.addAttribute("titre_page", "Modifier le billet");
        return "billets/form";
    }

    // ===== ANNULATION RAPIDE =====

    @PostMapping("/{id}/annuler")
    public String annuler(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        billetService.annulerBillet(id);
        redirectAttributes.addFlashAttribute("success", "Billet annulé.");
        return "redirect:/billets";
    }

    // ===== CONFIRMATION RAPIDE =====

    @PostMapping("/{id}/confirmer")
    public String confirmer(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        billetService.confirmerBillet(id);
        redirectAttributes.addFlashAttribute("success", "Billet confirmé (VENDU).");
        return "redirect:/billets";
    }

    // ===== SUPPRESSION =====

    @PostMapping("/{id}/supprimer")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        billetService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Billet supprimé.");
        return "redirect:/billets";
    }
}