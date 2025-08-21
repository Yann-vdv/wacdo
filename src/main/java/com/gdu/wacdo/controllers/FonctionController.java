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

import com.gdu.wacdo.entities.Fonction;
import com.gdu.wacdo.repositories.FonctionRepository;

@Controller
@Slf4j
@RequestMapping("/fonctions")
public class FonctionController {

    private final FonctionRepository fonctionRepository;

    public FonctionController(FonctionRepository fonctionRepository) {
        this.fonctionRepository = fonctionRepository;
    }

    @GetMapping
    public String fonctions(Model model){
        model.addAttribute("fonctions", fonctionRepository.findAll());
        return "fonctions";
    }

    @GetMapping({"/{id}"})
    public String FonctionsById(Model model, @PathVariable Long id){
        Optional<Fonction> fctOpt = fonctionRepository.findById(id);

        if (fctOpt.isPresent()) {
            Fonction fct = fctOpt.get();
            model.addAttribute("Fonction", fct);
        } else {
            System.out.println("Fonction not found with id: " + id);
            return this.fonctions(model);
        }

        return "fonction";
    }

    @GetMapping({"/api"})
    public ResponseEntity<List<Fonction>> getAll() {
        return ResponseEntity.ok(fonctionRepository.findAll());
    }

    @GetMapping({"/api/{id}"})
    public ResponseEntity<Fonction> getById(@PathVariable Long id) {
        return fonctionRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping({"/api"})
    public ResponseEntity<Fonction> create(@RequestBody Fonction fonction) {
        Fonction saved = fonctionRepository.save(fonction);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(saved.getId())
            .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @PatchMapping({"/api/{id}"})
    public ResponseEntity<Fonction> update(@PathVariable Long id, @RequestBody Fonction updates) {
        return fonctionRepository.findById(id)
            .map(existing -> {
                if (updates.getNom() != null) {
                    existing.setNom(updates.getNom());
                }
                Fonction saved = fonctionRepository.save(existing);
                return ResponseEntity.ok(saved);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/api/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!fonctionRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        fonctionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
