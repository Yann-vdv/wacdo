package com.gdu.wacdo.services;

import com.gdu.wacdo.DTO.*;
import com.gdu.wacdo.entities.Affectation;
import com.gdu.wacdo.entities.Collaborateur;
import com.gdu.wacdo.entities.Fonction;
import com.gdu.wacdo.entities.Restaurant;
import com.gdu.wacdo.repositories.AffectationRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class AffectationService {

    private final AffectationRepository affectationRepository;
    private final ModelMapper modelMapper;
    private final RestaurantService restaurantService;
    private final FonctionService fonctionService;
    private final CollaborateurService collaborateurService;

    public AffectationService(AffectationRepository affectationRepository, ModelMapper modelMapper, RestaurantService restaurantService, FonctionService fonctionService, CollaborateurService collaborateurService) {
        this.affectationRepository = affectationRepository;
        this.modelMapper = modelMapper;
        this.restaurantService = restaurantService;
        this.fonctionService = fonctionService;
        this.collaborateurService = collaborateurService;
    }

    public AffectationDTO create(NewAffectationDTO newAffectationDTO) {
        try {
            Affectation newAffectation = new Affectation();
            newAffectation.setDateDebut(newAffectationDTO.getDateDebut());
            newAffectation.setDateFin(newAffectationDTO.getDateFin());
            newAffectation.setCollaborateur(collaborateurService.findByIdFull(newAffectationDTO.getCollaborateur()));
            newAffectation.setRestaurant(restaurantService.findByIdFull(newAffectationDTO.getRestaurant()));
            newAffectation.setFonction(fonctionService.findByIdFull(newAffectationDTO.getFonction()));
            Affectation createdAffectation = affectationRepository.save(newAffectation);
            if(createdAffectation.getId() != null) {
                AffectationDTO affectationDTO;
                affectationDTO = modelMapper.map(createdAffectation, AffectationDTO.class);
                return affectationDTO;
            } else {
                return null;
            }
        } catch(Exception err) {
            log.error("Create Affectation error : {}", err.getMessage());
            return null;
        }
    }

    public AffectationDTO edit(long id, EditAffectationDTO editAffectationDTO) {
        log.info("service, edit affectation : {}",editAffectationDTO);
        try {
            Optional<Affectation> updated = affectationRepository.findById(id)
                .map(existing -> {
                    if (!Objects.equals(editAffectationDTO.getDateDebut(), existing.getDateDebut())) {
                        existing.setDateDebut(editAffectationDTO.getDateDebut());
                    }
                    if (!Objects.equals(editAffectationDTO.getDateFin(), existing.getDateFin())) {
                        existing.setDateFin(editAffectationDTO.getDateFin());
                    }
                    Restaurant restaurant = restaurantService.findByIdFull(editAffectationDTO.getRestaurant());
                    if (!Objects.equals(restaurant, existing.getRestaurant())) {
                        existing.setRestaurant(restaurant);
                    }
                    Collaborateur collaborateur = collaborateurService.findByIdFull(editAffectationDTO.getCollaborateur());
                    if (!Objects.equals(collaborateur, existing.getCollaborateur())) {
                        existing.setCollaborateur(collaborateur);
                    }
                    Fonction fonction = fonctionService.findByIdFull(editAffectationDTO.getFonction());
                    if (!Objects.equals(fonction, existing.getFonction())) {
                        existing.setFonction(fonction);
                    }
                    return affectationRepository.save(existing);
                });
            return updated.map(entity -> modelMapper.map(entity, AffectationDTO.class))
                    .orElse(null);
        } catch(Exception err) {
            log.error("Edit Affectation error : {}", err.getMessage());
            return null;
        }
    }

    public boolean delete(long id) {
        if (!affectationRepository.existsById(id)) {
            return false;
        }
        try {
            affectationRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException err) {
            log.error("Delete Affectation error : {}", err.getMessage());
            return false;
        }
    }
    
    public AffectationDTO findById(Long id) {
        try {
            Optional<Affectation> affectationOpt = affectationRepository.findById(id);
            if (affectationOpt.isPresent()) {
                Affectation affectation = affectationOpt.get();
                AffectationDTO affectationDTO;
                affectationDTO = modelMapper.map(affectation, AffectationDTO.class);
                return affectationDTO;
            } else {
                return null;
            }
        } catch (Exception err) {
            log.error("get Affectation by id error : {}", err.getMessage());
            return null;
        }
    }

    public List<AffectationDTO> findAllForView() {
        List<Affectation> affectations = affectationRepository.findAll();
        List<AffectationDTO> affectationsDTO = new ArrayList<>();
        for (Affectation affectation : affectations) {
            affectationsDTO.add(modelMapper.map(affectation, AffectationDTO.class));
        }
        log.info("list Affectations : {}", affectationsDTO);
        return affectationsDTO;
    }

    public DataDTO getProcessData() {
        List<CollabDTO> collabs = collaborateurService.findAllForView();
        List<RestaurantDTO> restaurants = restaurantService.findAllForView();
        List<FonctionDTO> fonctions = fonctionService.findAllForView();

        return new DataDTO(collabs, restaurants, fonctions);
    }
}
