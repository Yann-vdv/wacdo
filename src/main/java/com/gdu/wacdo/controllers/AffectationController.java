package com.gdu.wacdo.controllers;

import com.gdu.wacdo.DTO.*;
import com.gdu.wacdo.entities.ApiResponse;
import com.gdu.wacdo.entities.Affectation;
import com.gdu.wacdo.entities.Status;
import com.gdu.wacdo.repositories.AffectationRepository;
import com.gdu.wacdo.services.AffectationService;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequestMapping("/affectations")
public class AffectationController {

    private final AffectationRepository affectationRepository;
    private final AffectationService affectationService;

    public AffectationController(AffectationRepository affectationRepository, AffectationService affectationService) {
        this.affectationRepository = affectationRepository;
        this.affectationService = affectationService;
    }

    @GetMapping
    public String affectations(Model model, @RequestParam(required = false) String error){
        List<AffectationDTO> affectationsDTO = affectationService.findAllForView();
        if (affectationsDTO != null) {
            DataDTO processData = affectationService.getProcessData();
            if (!processData.getFonctions().isEmpty() && !processData.getRestaurants().isEmpty() && !processData.getCollabs().isEmpty()) {
                model.addAttribute("processData", processData);
                model.addAttribute("affectation", new Affectation());
                model.addAttribute("filterAffectation", new AffectationFilterDTO());
            }
            ApiResponse<List<AffectationDTO>> response = new ApiResponse<>(error != null ? Status.ERROR : Status.SUCCESS, affectationsDTO,true, error != null ? error : "Affectations récupérés avec succès");
            model.addAttribute("response", response);
        } else {
            ApiResponse<List<AffectationDTO>> response = new ApiResponse<>(Status.ERROR,null,true,"La récupération des affectations a échouée");
            model.addAttribute("response", response);
        }
        return "affectations";
    }

    @PostMapping("/filtered")
    public String affectationsFiltered(AffectationFilterDTO filteredAffectation,Model model){
        List<AffectationDTO> affectationsDTO = affectationService.findAllForViewFiltered(filteredAffectation);
        if (affectationsDTO != null) {
            DataDTO processData = affectationService.getProcessData();
            if (!processData.getFonctions().isEmpty() && !processData.getRestaurants().isEmpty() && !processData.getCollabs().isEmpty()) {
                model.addAttribute("processData", processData);
                model.addAttribute("affectation", new Affectation());
                model.addAttribute("filterAffectation", new NewAffectationDTO());
            }
            ApiResponse<List<AffectationDTO>> response = new ApiResponse<>(Status.SUCCESS,affectationsDTO,true,"Affectations(filtrés) récupérés avec succès");
            model.addAttribute("response", response);
        } else {
            ApiResponse<List<AffectationDTO>> response = new ApiResponse<>(Status.ERROR,null,true,"La récupération des affectations(filtrés) a échouée");
            model.addAttribute("response", response);
        }
        return "affectations";
    }

    @GetMapping("/restaurant/{id}")
    public String newAffectationByRestaurant(Model model, @PathVariable Long id){
        DataDTO processData = affectationService.getProcessData();
        if (!processData.getFonctions().isEmpty() && !processData.getRestaurants().isEmpty() && !processData.getCollabs().isEmpty()) {
            model.addAttribute("processData", processData);
            model.addAttribute("affectation", new NewAffectationDTO(LocalDate.now(),null,id,null,null));
            ApiResponse<List<AffectationDTO>> response = new ApiResponse<>(Status.SUCCESS,null,false,"Données récupérés avec succès");
            model.addAttribute("response", response);
        } else {
            ApiResponse<List<AffectationDTO>> response = new ApiResponse<>(Status.ERROR,null,true,"La récupération des données a échouée");
            model.addAttribute("response", response);
        }
        return "affectations";
    }

    @GetMapping("/collaborateur/{id}")
    public String newAffectationByCollaborateur(Model model, @PathVariable Long id){
        DataDTO processData = affectationService.getProcessData();
        if (!processData.getFonctions().isEmpty() && !processData.getRestaurants().isEmpty() && !processData.getCollabs().isEmpty()) {
            model.addAttribute("processData", processData);
            model.addAttribute("affectation", new NewAffectationDTO(LocalDate.now(),null,null,id,null));
            ApiResponse<List<AffectationDTO>> response = new ApiResponse<>(Status.SUCCESS,null,false,"Données récupérés avec succès");
            model.addAttribute("response", response);
        } else {
            ApiResponse<List<AffectationDTO>> response = new ApiResponse<>(Status.ERROR,null,true,"La récupération des données a échouée");
            model.addAttribute("response", response);
        }
        return "affectations";
    }

    @GetMapping("/{id}")
    public String affectationById(Model model, @PathVariable Long id, @RequestParam(required = false) String error) {
        AffectationDTO affectation = affectationService.findById(id);

        if (affectation != null) {
            ApiResponse<AffectationDTO> response = new ApiResponse<>(error != null ? Status.ERROR : Status.SUCCESS, affectation,true, error != null ? error : "Affectation récupéré avec succès");
            model.addAttribute("response", response);
            DataDTO processData = affectationService.getProcessData();
            if (!processData.getFonctions().isEmpty() && !processData.getRestaurants().isEmpty() && !processData.getCollabs().isEmpty()) {
                model.addAttribute("processData", processData);
                EditAffectationDTO editAffectationDTO = new EditAffectationDTO(affectation);
                model.addAttribute("affectation", editAffectationDTO);
            }
            return "affectation";
        } else {
            ApiResponse<AffectationDTO> response = new ApiResponse<>(Status.ERROR,null,true,"La récupération du affectation a échouée");
            model.addAttribute("response", response);
            return "redirect:/affectations?error=affectation introuvable";
        }
    }

    @PostMapping({"/new"})
    public String newAffectation(NewAffectationDTO newAffectation, Model model) {
        AffectationDTO affectation = affectationService.create(newAffectation);
        if (affectation != null) {
            return "redirect:/affectations/"+affectation.getId();
        } else {
            return "redirect:/affectations?error=La création du affectation a échouée";
        }
    }

    @PostMapping({"/edit/{id}"})
    public String editAffectation(@PathVariable Long id, EditAffectationDTO editedAffectation, Model model) {
        AffectationDTO affectation = affectationService.edit(id, editedAffectation);
        if (affectation != null) {
            ApiResponse<AffectationDTO> response = new ApiResponse<>(Status.SUCCESS,affectation,true,"Affectation modifié avec succès");
            model.addAttribute("response", response);
            model.addAttribute("affectation", response.getData());
            return "affectation";
        } else {
            return "redirect:/affectations/"+id+"?error=La modification de l'affectation a échouée";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteAffectation(@PathVariable Long id, Model model) {
        boolean res = affectationService.delete(id);
        if (res) {
            return "redirect:/affectations";
        } else {
            return "redirect:/affectations/"+id+"?error=La suppression de l'affectation a échouée";
        }
    }
}
