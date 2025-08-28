package com.gdu.wacdo.controllers;

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
            //model.addAttribute("restaurants", restaurantsDTO);
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

    @PostMapping({"/newRestaurant"})
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

    /*@GetMapping({"/{id}"})
    public String restaurantsById(Model model, @PathVariable Long id){
        Optional<Restaurant> RestauOpt = restaurantRepository.findById(id);

        if (RestauOpt.isPresent()) {
            Restaurant Restau = RestauOpt.get();
            model.addAttribute("Restaurant", Restau);
        } else {
            System.out.println("Restaurant not found with id: " + id);
            return this.restaurants(model);
        }

        return "restaurant";
    }

    @GetMapping({"/api"})
    public ResponseEntity<List<Restaurant>> getAll() {
        return ResponseEntity.ok(restaurantRepository.findAll());
    }

    @GetMapping({"/api/{id}"})
    public ResponseEntity<Restaurant> getById(@PathVariable Long id) {
        return restaurantRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping({"/api"})
    public ResponseEntity<Restaurant> create(@RequestBody Restaurant restaurant) {
        Restaurant saved = restaurantRepository.save(restaurant);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(saved.getId())
            .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @PatchMapping({"/api/{id}"})
    public ResponseEntity<Restaurant> update(@PathVariable Long id, @RequestBody Restaurant updates) {
        return restaurantRepository.findById(id)
            .map(existing -> {
                if (updates.getNom() != null) {
                    existing.setNom(updates.getNom());
                }
                if (updates.getAdresse() != null) {
                    existing.setAdresse(updates.getAdresse());
                }
                if (updates.getCodePostal() != null) {
                    existing.setCodePostal(updates.getCodePostal());
                }
                if (updates.getVille() != null) {
                    existing.setVille(updates.getVille());
                }
                Restaurant saved = restaurantRepository.save(existing);
                return ResponseEntity.ok(saved);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/api/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!restaurantRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        restaurantRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }*/
}
