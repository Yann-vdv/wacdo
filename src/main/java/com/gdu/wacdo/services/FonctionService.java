package com.gdu.wacdo.services;

import com.gdu.wacdo.DTO.FonctionDTO;
import com.gdu.wacdo.DTO.NewFonctionDTO;
import com.gdu.wacdo.entities.Fonction;
import com.gdu.wacdo.entities.Restaurant;
import com.gdu.wacdo.repositories.FonctionRepository;
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
public class FonctionService {

    private final FonctionRepository fonctionRepository;
    private final ModelMapper modelMapper;

    public FonctionService(FonctionRepository fonctionRepository, ModelMapper modelMapper) {
        this.fonctionRepository = fonctionRepository;
        this.modelMapper = modelMapper;
    }

    public FonctionDTO create(NewFonctionDTO newFonctionDTO) {
        try {
            Fonction newFonction;
            newFonction = modelMapper.map(newFonctionDTO, Fonction.class);
            Fonction createdFonction = fonctionRepository.save(newFonction);
            if(createdFonction.getId() != null) {
                FonctionDTO fonctionDTO;
                fonctionDTO = modelMapper.map(createdFonction, FonctionDTO.class);
                return fonctionDTO;
            } else {
                return null;
            }
        } catch(Exception err) {
            log.error("Create Fonction error : {}", err.getMessage());
            return null;
        }
    }

    public FonctionDTO edit(long id, FonctionDTO fonctionDTO) {
        try {
            Optional<Fonction> updated = fonctionRepository.findById(id)
                .map(existing -> {
                    if (!Objects.equals(fonctionDTO.getNom(), existing.getNom())) {
                        existing.setNom(fonctionDTO.getNom());
                    }
                    return fonctionRepository.save(existing);
                });
            return updated.map(entity -> modelMapper.map(entity, FonctionDTO.class))
                    .orElse(null);
        } catch(Exception err) {
            log.error("Edit Fonction error : {}", err.getMessage());
            return null;
        }
    }

    public boolean delete(long id) {
        if (!fonctionRepository.existsById(id)) {
            return false;
        }
        try {
            fonctionRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException err) {
            log.error("Delete Fonction error : {}", err.getMessage());
            return false;
        }
    }
    
    public FonctionDTO findById(Long id) {
        try {
            Optional<Fonction> fonctionOpt = fonctionRepository.findById(id);
            if (fonctionOpt.isPresent()) {
                Fonction fonction = fonctionOpt.get();
                FonctionDTO fonctionDTO;
                fonctionDTO = modelMapper.map(fonction, FonctionDTO.class);
                return fonctionDTO;
            } else {
                return null;
            }
        } catch (Exception err) {
            log.error("get Fonction by id error : {}", err.getMessage());
            return null;
        }
    }

    public Fonction findByIdFull(long id) {
        try {
            Optional<Fonction> fonctionOpt = fonctionRepository.findById(id);
            return fonctionOpt.orElse(null);
        } catch (Exception err) {
            log.error("get full Fonction by id error : {}", err.getMessage());
            return null;
        }
    }

    public List<FonctionDTO> findAllForView() {
        List<Fonction> fonctions = fonctionRepository.findAll();
        List<FonctionDTO> fonctionsDTO = new ArrayList<>();
        for (Fonction fonction : fonctions) {
            fonctionsDTO.add(modelMapper.map(fonction, FonctionDTO.class));
        }
        //log.info("list Fonctions : {}", fonctionsDTO);
        return fonctionsDTO;
    }
}
