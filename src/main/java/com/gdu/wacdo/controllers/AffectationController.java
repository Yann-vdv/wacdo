package com.gdu.wacdo.controllers;

import com.gdu.wacdo.DTO.AffectationDTO;
import com.gdu.wacdo.DTO.DataDTO;
import com.gdu.wacdo.DTO.EditAffectationDTO;
import com.gdu.wacdo.DTO.NewAffectationDTO;
import com.gdu.wacdo.entities.ApiResponse;
import com.gdu.wacdo.entities.Affectation;
import com.gdu.wacdo.entities.Status;
import com.gdu.wacdo.repositories.AffectationRepository;
import com.gdu.wacdo.services.AffectationService;
import com.gdu.wacdo.services.DataService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/affectations")
public class AffectationController {

    private final AffectationRepository affectationRepository;
    private final AffectationService affectationService;
    private final DataService dataService;

    public AffectationController(AffectationRepository affectationRepository, AffectationService affectationService, DataService dataService) {
        this.affectationRepository = affectationRepository;
        this.affectationService = affectationService;
        this.dataService = dataService;
    }

    @GetMapping
    public String affectations(Model model){
        List<AffectationDTO> affectationsDTO = affectationService.findAllForView();
        if (affectationsDTO != null) {
            DataDTO processData = dataService.getProcessData();
            if (!processData.getFonctions().isEmpty() && !processData.getRestaurants().isEmpty() && !processData.getCollabs().isEmpty()) {
                model.addAttribute("processData", processData);
                model.addAttribute("affectation", new Affectation());
            }
            ApiResponse<List<AffectationDTO>> response = new ApiResponse<>(Status.SUCCESS,affectationsDTO,true,"Affectations récupérés avec succès");
            model.addAttribute("response", response);
        } else {
            ApiResponse<List<AffectationDTO>> response = new ApiResponse<>(Status.ERROR,null,true,"La récupération des affectations a échouée");
            model.addAttribute("response", response);
        }
        return "affectations";
    }

    @GetMapping("/{id}")
    public String affectationById(Model model, @PathVariable Long id) {
        AffectationDTO affectation = affectationService.findById(id);

        if (affectation != null) {
            ApiResponse<AffectationDTO> response = new ApiResponse<>(Status.SUCCESS,affectation,true,"Affectation récupéré avec succès");
            model.addAttribute("response", response);

            DataDTO processData = dataService.getProcessData();
            if (!processData.getFonctions().isEmpty() && !processData.getRestaurants().isEmpty() && !processData.getCollabs().isEmpty()) {
                model.addAttribute("processData", processData);
                EditAffectationDTO editAffectationDTO = new EditAffectationDTO(affectation);
                log.info("initial edit data : {}", editAffectationDTO);
                model.addAttribute("affectation", editAffectationDTO);
            }
            return "affectation";
        } else {
            ApiResponse<AffectationDTO> response = new ApiResponse<>(Status.ERROR,null,true,"La récupération du affectation a échouée");
            model.addAttribute("response", response);
            return "affectations";
        }
    }

    @PostMapping({"/new"})
    public String newAffectation(NewAffectationDTO newAffectation, Model model) {
        AffectationDTO affectation = affectationService.create(newAffectation);
        if (affectation != null) {
            ApiResponse<AffectationDTO> response = new ApiResponse<>(Status.SUCCESS,affectation,true,"Affectation créé avec succès");
            model.addAttribute("response", response);
            return "affectation";
        } else {
            ApiResponse<AffectationDTO> response = new ApiResponse<>(Status.ERROR,null,true,"La création du affectation a échouée");
            model.addAttribute("response", response);
            return "affectations";
        }
    }

    @PostMapping({"/edit/{id}"})
    public String editAffectation(@PathVariable Long id, EditAffectationDTO editedAffectation, Model model) {
        log.info("edited edit data : {}", editedAffectation);
        AffectationDTO affectation = affectationService.edit(id, editedAffectation);
        if (affectation != null) {
            ApiResponse<AffectationDTO> response = new ApiResponse<>(Status.SUCCESS,affectation,true,"Affectation modifié avec succès");
            model.addAttribute("response", response);
            EditAffectationDTO editAffectationDTO = new EditAffectationDTO(response.getData());
            model.addAttribute("affectation", editAffectationDTO);
        } else {
            ApiResponse<AffectationDTO> response = new ApiResponse<>(Status.ERROR,null,true,"La modification du affectation a échouée");
            model.addAttribute("response", response);
        }
        return "affectation";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteAffectation(@PathVariable Long id, Model model) {
        boolean res = affectationService.delete(id);
        if (res) {
            ApiResponse<AffectationDTO> response = new ApiResponse<>(Status.SUCCESS,null,true,"Affectation supprimé avec succès");
            model.addAttribute("response", response);
            return "affectations";
        } else {
            ApiResponse<AffectationDTO> response = new ApiResponse<>(Status.ERROR,null,true,"La suppression du affectation a échouée");
            model.addAttribute("response", response);
            return "affectation";
        }
    }
}
