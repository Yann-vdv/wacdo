package com.gdu.wacdo.controllers;

import com.gdu.wacdo.DTO.*;
import com.gdu.wacdo.entities.ApiResponse;
import com.gdu.wacdo.entities.Status;
import com.gdu.wacdo.services.CollaborateurService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;

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
            ApiResponse<List<CollabDTO>> response = new ApiResponse<>(Status.SUCCESS,collabsDTO,true,"Collaborateurs récupérés avec succès");
            model.addAttribute("response", response);
            model.addAttribute("filterCollaborateur",new CollabDTO());
        } else {
            ApiResponse<List<CollabDTO>> response = new ApiResponse<>(Status.ERROR,null,true,"La récupération des collaborateurs a échouée");
            model.addAttribute("response", response);
        }
        model.addAttribute("collaborateur", new Collaborateur());
        return "collaborateurs";
    }

    @PostMapping("/filtered")
    public String collaborateursFiltered(CollabDTO filteredCollab,Model model){
        List<CollabDTO> collabsDTO = collaborateurService.findAllForViewFiltered(filteredCollab);
        if (collabsDTO != null) {
            ApiResponse<List<CollabDTO>> response = new ApiResponse<>(Status.SUCCESS,collabsDTO,true,"Collaborateurs(filtrés) récupérés avec succès");
            model.addAttribute("response", response);
            model.addAttribute("filterCollaborateur",new CollabDTO());
        } else {
            ApiResponse<List<CollabDTO>> response = new ApiResponse<>(Status.ERROR,null,true,"La récupération des collaborateurs(filtrés) a échouée");
            model.addAttribute("response", response);
        }
        model.addAttribute("collaborateur", new Collaborateur());
        return "collaborateurs";
    }

    @GetMapping("/{id}")
    public String collaborateurById(Model model, @PathVariable Long id) {
        CollabDTO collab = collaborateurService.findById(id);
        CollaborateurAffectationFilterDTO emptyFilter = new CollaborateurAffectationFilterDTO();
        List<AffectationDTO> collabCurrentAff = collaborateurService.findCurrentAffectationsForView(id);
        List<AffectationDTO> collabHistoryAff = collaborateurService.findHistoryAffectationsForViewFiltred(id, emptyFilter);

        if (collab != null) {
            ApiResponse<CollabDTO> response = new ApiResponse<>(Status.SUCCESS,collab,true,"Collaborateur récupéré avec succès");
            model.addAttribute("response", response);
            model.addAttribute("collaborateur", collab);
            model.addAttribute("filter", emptyFilter);
            model.addAttribute("collabCurrentAff",collabCurrentAff);
            model.addAttribute("collabHistoryAff",collabHistoryAff);
            return "collaborateur";
        } else {
            ApiResponse<CollabDTO> response = new ApiResponse<>(Status.ERROR,null,true,"La récupération du collaborateur a échouée");
            model.addAttribute("response", response);
            return "collaborateurs";
        }
    }

    @PostMapping("/{id}/filter")
    public String collaborateurByIdFiltered(CollaborateurAffectationFilterDTO filter, Model model, @PathVariable Long id) {
        CollabDTO collab = collaborateurService.findById(id);
        CollaborateurAffectationFilterDTO emptyFilter = new CollaborateurAffectationFilterDTO();
        List<AffectationDTO> collabCurrentAff = collaborateurService.findCurrentAffectationsForView(id);
        List<AffectationDTO> collabHistoryAff = collaborateurService.findHistoryAffectationsForViewFiltred(id, filter != null ? filter : emptyFilter);

        if (collab != null) {
            ApiResponse<CollabDTO> response = new ApiResponse<>(Status.SUCCESS,collab,true,"Collaborateur récupéré avec succès");
            model.addAttribute("response", response);
            model.addAttribute("collaborateur", collab);
            model.addAttribute("filter", emptyFilter);
            model.addAttribute("collabCurrentAff",collabCurrentAff);
            model.addAttribute("collabHistoryAff",collabHistoryAff);
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