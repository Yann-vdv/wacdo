package com.gdu.wacdo.controllers;

import com.gdu.wacdo.DTO.CollabDTO;
import com.gdu.wacdo.DTO.NewCollabDTO;
import com.gdu.wacdo.entities.ApiResponse;
import com.gdu.wacdo.entities.Status;
import com.gdu.wacdo.services.CollaborateurService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
            ApiResponse<List<CollabDTO>> response = new ApiResponse<>(Status.SUCCESS,collabsDTO,true,"Collaborateurs récupérés avec succès");
            log.info("Response {}", response);
            model.addAttribute("response", response);
        } else {
            ApiResponse<List<CollabDTO>> response = new ApiResponse<>(Status.ERROR,null,true,"La récupération des collaborateurs a échouée");
            model.addAttribute("response", response);
        }
        model.addAttribute("collaborateur", new Collaborateur());
        return "collaborateurs";
    }

    @GetMapping("/{id}")
    public String collaborateurById(Model model, @PathVariable Long id) {
        CollabDTO collab = collaborateurService.findById(id);

        if (collab != null) {
            ApiResponse<CollabDTO> response = new ApiResponse<>(Status.SUCCESS,collab,true,"Collaborateur récupéré avec succès");
            model.addAttribute("response", response);
            model.addAttribute("collaborateur", collab);
            return "collaborateur";
        } else {
            ApiResponse<CollabDTO> response = new ApiResponse<>(Status.ERROR,null,true,"La récupération du collaborateur a échouée");
            model.addAttribute("response", response);
            return "collaborateurs";
        }
    }

    @PostMapping({"/new"})
    public String newCollab(NewCollabDTO collaborateur, Model model) {
        CollabDTO collab = collaborateurService.create(collaborateur);
        if (collab != null) {
            //model.addAttribute("collaborateur", collab);
            ApiResponse<CollabDTO> response = new ApiResponse<>(Status.SUCCESS,collab,true,"Collaborateur créé avec succès");
            model.addAttribute("response", response);
            return "collaborateur";
        } else {
            ApiResponse<CollabDTO> response = new ApiResponse<>(Status.ERROR,null,true,"La création du collaborateur a échouée");
            model.addAttribute("response", response);
            return "collaborateurs";
        }
    }

    @PostMapping({"/edit/{id}"})
    public String editCollab(@PathVariable Long id, CollabDTO collaborateur, Model model) {
        CollabDTO collab = collaborateurService.edit(id, collaborateur);
        if (collab != null) {
            ApiResponse<CollabDTO> response = new ApiResponse<>(Status.SUCCESS,collab,true,"Collaborateur modifié avec succès");
            model.addAttribute("response", response);
            model.addAttribute("collaborateur", response.getData());
        } else {
            ApiResponse<CollabDTO> response = new ApiResponse<>(Status.ERROR,null,true,"La modification du collaborateur a échouée");
            model.addAttribute("response", response);
        }
        return "collaborateur";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCollab(@PathVariable Long id, Model model) {
        boolean res = collaborateurService.delete(id);
        if (res) {
            ApiResponse<CollabDTO> response = new ApiResponse<>(Status.SUCCESS,null,true,"Collaborateur supprimé avec succès");
            model.addAttribute("response", response);
            return "collaborateurs";
        } else {
            ApiResponse<CollabDTO> response = new ApiResponse<>(Status.ERROR,null,true,"La suppression du collaborateur a échouée");
            model.addAttribute("response", response);
            return "collaborateur";
        }
    }
}