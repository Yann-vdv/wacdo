package com.gdu.wacdo.controllers;

import com.gdu.wacdo.DTO.CollabDTO;
import com.gdu.wacdo.DTO.RestaurantDTO;
import com.gdu.wacdo.DTO.RestaurantDTO;
import com.gdu.wacdo.DTO.NewRestaurantDTO;
import com.gdu.wacdo.entities.ApiResponse;
import com.gdu.wacdo.entities.Restaurant;
import com.gdu.wacdo.entities.Status;
import com.gdu.wacdo.repositories.RestaurantRepository;

import com.gdu.wacdo.services.RestaurantService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
@Slf4j
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantRepository restaurantRepository, RestaurantService restaurantService) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public String restaurants(Model model){
        List<RestaurantDTO> restaurantsDTO = restaurantService.findAllForView();
        if (restaurantsDTO != null) {
            ApiResponse<List<RestaurantDTO>> response = new ApiResponse<>(Status.SUCCESS,restaurantsDTO,true,"Restaurants récupérés avec succès");
            log.info("Response {}", response);
            model.addAttribute("response", response);
        } else {
            ApiResponse<List<RestaurantDTO>> response = new ApiResponse<>(Status.ERROR,null,true,"La récupération des restaurants a échouée");
            model.addAttribute("response", response);
        }
        model.addAttribute("restaurant", new Restaurant());
        return "restaurants";
    }

    @GetMapping("/{id}")
    public String restaurantById(Model model, @PathVariable Long id) {
        RestaurantDTO restaurant = restaurantService.findById(id);
        List<CollabDTO> collabs = restaurantService.findCurrentCollabs(id);

        if (restaurant != null) {
            ApiResponse<RestaurantDTO> response = new ApiResponse<>(Status.SUCCESS,restaurant,true,"Restaurant récupéré avec succès");
            model.addAttribute("response", response);
            return "restaurant";
        } else {
            ApiResponse<RestaurantDTO> response = new ApiResponse<>(Status.ERROR,null,true,"La récupération du restaurant a échouée");
            model.addAttribute("response", response);
            return "restaurants";
        }
    }

    @PostMapping({"/new"})
    public String newRestaurant(NewRestaurantDTO newRestaurantDTO, Model model) {
        RestaurantDTO restaurant = restaurantService.create(newRestaurantDTO);
        if (restaurant != null) {
            //model.addAttribute("restaurant", restaurant);
            ApiResponse<RestaurantDTO> response = new ApiResponse<>(Status.SUCCESS,restaurant,true,"Restaurant créé avec succès");
            model.addAttribute("response", response);
            return "restaurant";
        } else {
            ApiResponse<RestaurantDTO> response = new ApiResponse<>(Status.ERROR,null,true,"La création du restaurant a échouée");
            model.addAttribute("response", response);
            return "restaurants";
        }
    }

    @PostMapping({"/edit/{id}"})
    public String editRestaurant(@PathVariable Long id, RestaurantDTO editedRestaurant, Model model) {
        RestaurantDTO restaurant = restaurantService.edit(id, editedRestaurant);
        if (restaurant != null) {
            ApiResponse<RestaurantDTO> response = new ApiResponse<>(Status.SUCCESS,restaurant,true,"Restaurant modifié avec succès");
            model.addAttribute("response", response);
            model.addAttribute("restaurant", response.getData());
        } else {
            ApiResponse<RestaurantDTO> response = new ApiResponse<>(Status.ERROR,null,true,"La modification du restaurant a échouée");
            model.addAttribute("response", response);
        }
        return "restaurant";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteRestaurant(@PathVariable Long id, Model model) {
        boolean res = restaurantService.delete(id);
        if (res) {
            ApiResponse<RestaurantDTO> response = new ApiResponse<>(Status.SUCCESS,null,true,"Restaurant supprimé avec succès");
            model.addAttribute("response", response);
            return "restaurants";
        } else {
            ApiResponse<RestaurantDTO> response = new ApiResponse<>(Status.ERROR,null,true,"La suppression du restaurant a échouée");
            model.addAttribute("response", response);
            return "restaurant";
        }
    }
}
