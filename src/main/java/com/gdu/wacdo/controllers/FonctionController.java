package com.gdu.wacdo.controllers;

import com.gdu.wacdo.DTO.FonctionDTO;
import com.gdu.wacdo.DTO.NewFonctionDTO;
import com.gdu.wacdo.entities.ApiResponse;
import com.gdu.wacdo.entities.Fonction;
import com.gdu.wacdo.entities.Status;
import com.gdu.wacdo.services.FonctionService;
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
    private final FonctionService fonctionService;

    public FonctionController(FonctionRepository fonctionRepository, FonctionService fonctionService) {
        this.fonctionRepository = fonctionRepository;
        this.fonctionService = fonctionService;
    }

    @GetMapping
    public String fonctions(Model model){
        List<FonctionDTO> fonctionsDTO = fonctionService.findAllForView();
        if (fonctionsDTO != null) {
            //model.addAttribute("fonctions", fonctionsDTO);
            ApiResponse<List<FonctionDTO>> response = new ApiResponse<>(Status.SUCCESS,fonctionsDTO,true,"les fonctions ont été récupérés avec succès");
            log.info("Response {}", response);
            model.addAttribute("response", response);
        } else {
            ApiResponse<List<FonctionDTO>> response = new ApiResponse<>(Status.ERROR,null,true,"La récupération des fonctions a échouée");
            model.addAttribute("response", response);
        }
        model.addAttribute("fonction", new Fonction());
        return "fonctions";
    }

    @GetMapping("/{id}")
    public String fonctionById(Model model, @PathVariable Long id) {
        FonctionDTO fonction = fonctionService.findById(id);

        if (fonction != null) {
            ApiResponse<FonctionDTO> response = new ApiResponse<>(Status.SUCCESS,fonction,true,"la fonction a été récupéré avec succès");
            model.addAttribute("response", response);
            return "fonction";
        } else {
            ApiResponse<FonctionDTO> response = new ApiResponse<>(Status.ERROR,null,true,"La récupération de la fonction a échouée");
            model.addAttribute("response", response);
            return "fonctions";
        }
    }

    @PostMapping({"/newFonction"})
    public String newFonction(NewFonctionDTO newFonctionDTO, Model model) {
        FonctionDTO fonction = fonctionService.create(newFonctionDTO);
        if (fonction != null) {
            //model.addAttribute("fonction", fonction);
            ApiResponse<FonctionDTO> response = new ApiResponse<>(Status.SUCCESS,fonction,true,"la fonction a été créé avec succès");
            model.addAttribute("response", response);
            return "fonction";
        } else {
            ApiResponse<FonctionDTO> response = new ApiResponse<>(Status.ERROR,null,true,"La création de la fonction a échouée");
            model.addAttribute("response", response);
            return "fonctions";
        }
    }

    /*@GetMapping({"/{id}"})
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
    }*/


}
