package com.gdu.wacdo.services;

import com.gdu.wacdo.DTO.NewCollabDTO;
import com.gdu.wacdo.DTO.CollabDTO;
import com.gdu.wacdo.entities.ApiResponse;
import com.gdu.wacdo.entities.Collaborateur;
import com.gdu.wacdo.repositories.CollaborateurRepository;
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
public class CollaborateurService {

    private final CollaborateurRepository collaborateurRepository;
    private final ModelMapper modelMapper;

    public CollaborateurService(CollaborateurRepository collaborateurRepository, ModelMapper modelMapper) {
        this.collaborateurRepository = collaborateurRepository;
        this.modelMapper = modelMapper;
    }

    public CollabDTO create(NewCollabDTO newCollabDTO) {
        try {
            Collaborateur newCollab;
            newCollab = modelMapper.map(newCollabDTO, Collaborateur.class);
            Collaborateur createdCollab = collaborateurRepository.save(newCollab);
            if(createdCollab.getId() != null) {
                CollabDTO collabDTO;
                collabDTO = modelMapper.map(createdCollab, CollabDTO.class);
                return collabDTO;
            } else {
                return null;
            }
        } catch(Exception err) {
            log.error("Create Collaborateur error : {}", err.getMessage());
            return null;
        }
    }

    public CollabDTO edit(long id, CollabDTO collabDTO) {
        try {
            Optional<Collaborateur> updated = collaborateurRepository.findById(id)
                .map(existing -> {
                    if (!Objects.equals(collabDTO.getNom(), existing.getNom())) {
                        existing.setNom(collabDTO.getNom());
                    }
                    if (!Objects.equals(collabDTO.getPrenom(), existing.getPrenom())) {
                        existing.setPrenom(collabDTO.getPrenom());
                    }
                    if (!Objects.equals(collabDTO.getDateEmbauche(), existing.getDateEmbauche())) {
                        existing.setDateEmbauche(collabDTO.getDateEmbauche());
                    }
                    return collaborateurRepository.save(existing);
                });
            return updated.map(entity -> modelMapper.map(entity, CollabDTO.class))
                    .orElse(null);
        } catch(Exception err) {
            log.error("Edit Collaborateur error : {}", err.getMessage());
            return null;
        }
    }

    public boolean delete(long id) {
        if (!collaborateurRepository.existsById(id)) {
            return false;
        }
        try {
            collaborateurRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException err) {
            log.error("Delete Collaborateur error : {}", err.getMessage());
            return false;
        }
    }

    public CollabDTO findById(Long id) {
        try {
            Optional<Collaborateur> collabOpt = collaborateurRepository.findById(id);
            if (collabOpt.isPresent()) {
                Collaborateur collab = collabOpt.get();
                CollabDTO collabDTO;
                collabDTO = modelMapper.map(collab, CollabDTO.class);
                return collabDTO;
            } else {
                return null;
            }
        } catch (Exception err) {
            log.error("get Collaborateur by id error : {}", err.getMessage());
            return null;
        }
    }

    public List<CollabDTO> findAllForView() {
        List<Collaborateur> collabs = collaborateurRepository.findAll();
        List<CollabDTO> collabsDTO = new ArrayList<>();
        for (Collaborateur collaborateur : collabs) {
            collabsDTO.add(modelMapper.map(collaborateur, CollabDTO.class));
        }
        //log.info("list Collabs : {}", collabsDTO);
        return collabsDTO;
    }
}
