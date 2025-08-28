package com.gdu.wacdo.services;

import com.gdu.wacdo.DTO.RestaurantDTO;
import com.gdu.wacdo.DTO.NewRestaurantDTO;
import com.gdu.wacdo.entities.Restaurant;
import com.gdu.wacdo.repositories.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final ModelMapper modelMapper;

    public RestaurantService(RestaurantRepository restaurantRepository, ModelMapper modelMapper) {
        this.restaurantRepository = restaurantRepository;
        this.modelMapper = modelMapper;
    }

    public RestaurantDTO create(NewRestaurantDTO newRestaurantDTO) {
        try {
            Restaurant newRestaurant;
            newRestaurant = modelMapper.map(newRestaurantDTO, Restaurant.class);
            Restaurant createdRestaurant = restaurantRepository.save(newRestaurant);
            if(createdRestaurant.getId() != null) {
                RestaurantDTO restaurantDTO;
                restaurantDTO = modelMapper.map(createdRestaurant, RestaurantDTO.class);
                return restaurantDTO;
            } else {
                return null;
            }
        } catch(Exception err) {
            log.error("Create Restaurant error : {}", err.getMessage());
            return null;
        }
    }

    public RestaurantDTO findById(Long id) {
        try {
            Optional<Restaurant> restaurantOpt = restaurantRepository.findById(id);
            if (restaurantOpt.isPresent()) {
                Restaurant restaurant = restaurantOpt.get();
                RestaurantDTO restaurantDTO;
                restaurantDTO = modelMapper.map(restaurant, RestaurantDTO.class);
                return restaurantDTO;
            } else {
                return null;
            }
        } catch (Exception err) {
            log.error("get Restaurant by id error : {}", err.getMessage());
            return null;
        }
    }

    public List<RestaurantDTO> findAllForView() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        List<RestaurantDTO> restaurantsDTO = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            restaurantsDTO.add(modelMapper.map(restaurant, RestaurantDTO.class));
        }
        //log.info("list Restaurants : {}", restaurantsDTO);
        return restaurantsDTO;
    }
}
