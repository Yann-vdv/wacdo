package com.gdu.wacdo.services;

import com.gdu.wacdo.DTO.ColabExample;
import com.gdu.wacdo.entities.Collaborateur;
import com.gdu.wacdo.repositories.CollaborateurRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CollaborateurService {

    private final CollaborateurRepository collaborateurRepository;
    private final ModelMapper modelMapper;

    public CollaborateurService(CollaborateurRepository collaborateurRepository, ModelMapper modelMapper) {
        this.collaborateurRepository = collaborateurRepository;
        this.modelMapper = modelMapper;
    }

    public Collaborateur create(Collaborateur collaborateur) {
        try {
            Collaborateur newColab = collaborateurRepository.save(collaborateur);
            if(newColab.getId() != null) {
                return newColab;
            } else {
                return null;
            }
        } catch(Exception err) {
            log.error("Create Collaborateur error : {}", err.getMessage());
            return null;
        }
    }

    public List<Collaborateur> findAll() {
        return collaborateurRepository.findAll();
    }

    public List<ColabExample> findAllForView() {
        List<Collaborateur> colabs = collaborateurRepository.findAll();
        List<ColabExample> colabsDTO = new ArrayList<>();
        for (Collaborateur collaborateur : colabs) {
            colabsDTO.add(modelMapper.map(collaborateur,ColabExample.class));
        }
        log.info("list colabsExemple : {}", colabsDTO);
        return colabsDTO;
    }
}
