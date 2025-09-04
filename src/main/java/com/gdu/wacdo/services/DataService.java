package com.gdu.wacdo.services;

import com.gdu.wacdo.DTO.*;
import com.gdu.wacdo.entities.Restaurant;
import com.gdu.wacdo.repositories.RestaurantRepository;
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
public class DataService {

    private final RestaurantService restaurantService;
    private final FonctionService fonctionService;
    private final CollaborateurService collaborateurService;

    public DataService(RestaurantService restaurantService, FonctionService fonctionService, CollaborateurService collaborateurService) {
        this.restaurantService = restaurantService;
        this.fonctionService = fonctionService;
        this.collaborateurService = collaborateurService;
    }

    public DataDTO getProcessData() {
        List<CollabDTO> collabs = collaborateurService.findAllForView();
        List<RestaurantDTO> restaurants = restaurantService.findAllForView();
        List<FonctionDTO> fonctions = fonctionService.findAllForView();

        return new DataDTO(collabs, restaurants, fonctions);
    }
}
