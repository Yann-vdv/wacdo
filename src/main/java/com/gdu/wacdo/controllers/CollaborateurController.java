package com.gdu.wacdo.controllers;

import com.gdu.wacdo.DTO.CollabDTO;
import com.gdu.wacdo.DTO.NewCollabDTO;
import com.gdu.wacdo.entities.ApiResponse;
import com.gdu.wacdo.services.CollaborateurService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        List<CollabDTO> collabsDTO = collaborateurService.findAllForView();
        if (collabsDTO != null) {
            //model.addAttribute("collaborateurs", collabsDTO);
            ApiResponse<List<CollabDTO>> response = ApiResponse.success(collabsDTO,true,"Collaborateurs récupérés avec succès");
            log.info("Response {}", response);
            model.addAttribute("response", response);
        } else {
            ApiResponse<List<CollabDTO>> response = ApiResponse.error(true,"La récupération des collaborateurs a échouée");
            model.addAttribute("response", response);
        }
        model.addAttribute("collaborateur", new Collaborateur());
        return "collaborateurs";
    }

    @GetMapping("/{id}")
    public String collaborateurById(Model model, @PathVariable Long id) {
        CollabDTO collab = collaborateurService.findById(id);

        if (collab != null) {
            ApiResponse<CollabDTO> response = ApiResponse.success(collab,true,"Collaborateur récupéré avec succès");
            model.addAttribute("response", response);
            return "collaborateur";
        } else {
            ApiResponse<CollabDTO> response = ApiResponse.error(true,"La récupération du collaborateur a échouée");
            model.addAttribute("response", response);
            return "collaborateurs";
        }
    }

    @PostMapping({"/newCollab"})
    public String newCollab(NewCollabDTO collaborateur, Model model) {
        CollabDTO collab = collaborateurService.create(collaborateur);
        if (collab != null) {
            //model.addAttribute("collaborateur", collab);
            ApiResponse<CollabDTO> response = ApiResponse.success(collab,true,"Collaborateur créé avec succès");
            model.addAttribute("response", response);
            return "collaborateur";
        } else {
            ApiResponse<CollabDTO> response = ApiResponse.error(true,"La création du collaborateur a échouée");
            model.addAttribute("response", response);
            return "collaborateurs";
        }
    }

    /*@GetMapping({"/api"})
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
    }*/

}
