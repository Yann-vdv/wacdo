package com.gdu.wacdo.controllers;

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

import com.gdu.wacdo.entities.Affectation;
import com.gdu.wacdo.repositories.AffectationRepository;

@Controller
@Slf4j
@RequestMapping("/affectations")
public class AffectationController {

    private final AffectationRepository affectationRepository;

    public AffectationController(AffectationRepository affectationRepository) {
        this.affectationRepository = affectationRepository;
    }

    @GetMapping
    public String Affectations(Model model){
        model.addAttribute("affectations", affectationRepository.findAll());
        return "affectations";
    }

    @GetMapping({"/{id}"})
    public String AffectationsById(Model model, @PathVariable Long id){
         Optional<Affectation> affectationOpt = affectationRepository.findById(id);

        if (affectationOpt.isPresent()) {
            Affectation affectation = affectationOpt.get();
            model.addAttribute("affectation", affectation);
        } else {
            System.out.println("affectation not found with id: " + id);
            return this.Affectations(model);
        }

        return "affectation";
    }

    @GetMapping({"/api"})
    public ResponseEntity<List<Affectation>> getAll() {
        return ResponseEntity.ok(affectationRepository.findAll());
    }

    @GetMapping({"/api/{id}"})
    public ResponseEntity<Affectation> getById(@PathVariable Long id) {
        return affectationRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping({"/api"})
    public ResponseEntity<Affectation> create(@RequestBody Affectation affectation) {
        Affectation saved = affectationRepository.save(affectation);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(saved.getId())
            .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @PatchMapping({"/api/{id}"})
    public ResponseEntity<Affectation> update(@PathVariable Long id, @RequestBody Affectation updates) {
        return affectationRepository.findById(id)
            .map(existing -> {
                if (updates.getDateDebut() != null) {
                    existing.setDateDebut(updates.getDateDebut());
                }
                if (updates.getDateFin() != null) {
                    existing.setDateFin(updates.getDateFin());
                }
                if (updates.getRestaurant() != null) {
                    existing.setRestaurant(updates.getRestaurant());
                }
                if (updates.getCollaborateur() != null) {
                    existing.setCollaborateur(updates.getCollaborateur());
                }
                if (updates.getFonction() != null) {
                    existing.setFonction(updates.getFonction());
                }
                Affectation saved = affectationRepository.save(existing);
                return ResponseEntity.ok(saved);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/api/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!affectationRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        affectationRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
