package com.gdu.wacdo.controllers;

import com.gdu.wacdo.services.CollaborateurService;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.gdu.wacdo.entities.Collaborateur;
import com.gdu.wacdo.repositories.CollaborateurRepository;

@Controller
@Slf4j
@RequestMapping("/collaborateurs")
public class CollaborateurController {

    private final CollaborateurRepository collaborateurRepository;
    private final CollaborateurService collaborateurService;

    public CollaborateurController(CollaborateurRepository collaborateurRepository, CollaborateurService collaborateurService) {
        this.collaborateurRepository = collaborateurRepository;
        this.collaborateurService = collaborateurService;
    }

    @GetMapping
    public String collaborateurs(Model model){
        model.addAttribute("collaborateurs", collaborateurService.findAllForView());
        model.addAttribute("collaborateur", new Collaborateur());
        return "collaborateurs";
    }

    @GetMapping("/{id}")
    public String collaborateurById(Model model, @PathVariable Long id) {
        Optional<Collaborateur> colabOpt = collaborateurRepository.findById(id);

        if (colabOpt.isPresent()) {
            Collaborateur colab = colabOpt.get();
            model.addAttribute("collaborateur", colab);
        } else {
            System.out.println("Collaborateur not found with id: " + id);
            return this.collaborateurs(model);
        }

        return "collaborateur";
    }

    @GetMapping({"/api"})
    public ResponseEntity<List<Collaborateur>> getAll() {
        return ResponseEntity.ok(collaborateurRepository.findAll());
    }

    @GetMapping({"/api/{id}"})
    public ResponseEntity<Collaborateur> getById(@PathVariable Long id) {
        return collaborateurRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping({"/api"})
    public ResponseEntity<Collaborateur> create(@RequestBody Collaborateur collaborateur) {
        Collaborateur saved = collaborateurRepository.save(collaborateur);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(saved.getId())
            .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @PostMapping({"/newColab"})
    public String newColab(Collaborateur collaborateur, Model model) {
        Collaborateur collab = collaborateurService.create(collaborateur);
        if (collab != null) {
            model.addAttribute("notification", "collaborateur créé");
            return "collaborateur";
        } else {
            model.addAttribute("notification", "erreur lors de la création du collaborateur");
            return "collaborateurs";
        }
    }

    @PatchMapping({"/api/{id}"})
    public ResponseEntity<Collaborateur> update(@PathVariable Long id, @RequestBody Collaborateur updates) {
        return collaborateurRepository.findById(id)
            .map(existing -> {
                if (updates.getNom() != null) {
                    existing.setNom(updates.getNom());
                }
                if (updates.getPrenom() != null) {
                    existing.setPrenom(updates.getPrenom());
                }
                if (updates.getDateEmbauche() != null) {
                    existing.setDateEmbauche(updates.getDateEmbauche());
                }
                Collaborateur saved = collaborateurRepository.save(existing);
                return ResponseEntity.ok(saved);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/api/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!collaborateurRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        collaborateurRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
