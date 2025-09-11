package com.gdu.wacdo.controllers;

import com.gdu.wacdo.DTO.FonctionDTO;
import com.gdu.wacdo.DTO.NewFonctionDTO;
import com.gdu.wacdo.entities.ApiResponse;
import com.gdu.wacdo.entities.Fonction;
import com.gdu.wacdo.entities.Status;
import com.gdu.wacdo.services.FonctionService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
            ApiResponse<List<FonctionDTO>> response = new ApiResponse<>(Status.SUCCESS,fonctionsDTO,true,"les fonctions ont été récupérés avec succès");
            model.addAttribute("response", response);
            model.addAttribute("filterFonction",new FonctionDTO());
        } else {
            ApiResponse<List<FonctionDTO>> response = new ApiResponse<>(Status.ERROR,null,true,"La récupération des fonctions a échouée");
            model.addAttribute("response", response);
        }
        model.addAttribute("fonction", new Fonction());
        return "fonctions";
    }

    @PostMapping("/filtered")
    public String fonctionsFiltered(FonctionDTO filteredFonction,Model model){
        List<FonctionDTO> fonctionsDTO = fonctionService.findAllForViewFiltered(filteredFonction);
        if (fonctionsDTO != null) {
            ApiResponse<List<FonctionDTO>> response = new ApiResponse<>(Status.SUCCESS,fonctionsDTO,true,"les fonctions(filtrés) ont été récupérés avec succès");
            model.addAttribute("response", response);
            model.addAttribute("filterFonction",new FonctionDTO());
        } else {
            ApiResponse<List<FonctionDTO>> response = new ApiResponse<>(Status.ERROR,null,true,"La récupération des fonctions(filtrés) a échouée");
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
            model.addAttribute("fonction", fonction);
            return "fonction";
        } else {
            ApiResponse<FonctionDTO> response = new ApiResponse<>(Status.ERROR,null,true,"La récupération de la fonction a échouée");
            model.addAttribute("response", response);
            return "fonctions";
        }
    }

    @PostMapping({"/new"})
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
    
    @PostMapping({"/edit/{id}"})
    public String editFonction(@PathVariable Long id, FonctionDTO editedFonction, Model model) {
        FonctionDTO fonction = fonctionService.edit(id, editedFonction);
        if (fonction != null) {
            ApiResponse<FonctionDTO> response = new ApiResponse<>(Status.SUCCESS,fonction,true,"Fonction modifié avec succès");
            model.addAttribute("response", response);
            model.addAttribute("fonction", response.getData());
        } else {
            ApiResponse<FonctionDTO> response = new ApiResponse<>(Status.ERROR,null,true,"La modification du fonction a échouée");
            model.addAttribute("response", response);
        }
        return "fonction";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteFonction(@PathVariable Long id, Model model) {
        boolean res = fonctionService.delete(id);
        if (res) {
            ApiResponse<FonctionDTO> response = new ApiResponse<>(Status.SUCCESS,null,true,"Fonction supprimé avec succès");
            model.addAttribute("response", response);
            return "fonctions";
        } else {
            ApiResponse<FonctionDTO> response = new ApiResponse<>(Status.ERROR,null,true,"La suppression du fonction a échouée");
            model.addAttribute("response", response);
            return "fonction";
        }
    }
}
