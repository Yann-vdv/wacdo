package com.gdu.wacdo.services;

import com.gdu.wacdo.DTO.NewRestaurantDTO;
import com.gdu.wacdo.DTO.RestaurantDTO;
import com.gdu.wacdo.repositories.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ActiveProfiles("test")
public class RestaurantServiceTest {

    @Autowired
    private RestaurantService restaurantService;

    private RestaurantRepository restaurantRepository;

    @BeforeEach
    void setup() {
        restaurantService.deleteAll();
    }

    NewRestaurantDTO resto1 = new NewRestaurantDTO("Chez Toto","3 rue picard","64000","Paris");
    NewRestaurantDTO resto2 = new NewRestaurantDTO("La Bonne Table","26 rue lafontaine","78000","Marseille");

    @Test
    void noRestaurantsYet() throws Exception {
        assertEquals(0, restaurantService.findAllForView().size());
    }

    @Test
    void createRestaurant() throws Exception {
        restaurantService.create(resto1);
        restaurantService.create(resto2);

        assertEquals(2, restaurantService.findAllForView().size());
    }

    @Test
    void getRestaurant() throws Exception {
        RestaurantDTO created = restaurantService.create(resto1);

        RestaurantDTO resto = restaurantService.findById(created.getId());
        assertNotNull(resto);
        assertEquals(created, resto);
    }

    @Test
    void updateRestaurant() throws Exception {
        RestaurantDTO resto = restaurantService.create(resto1);

        RestaurantDTO updatedResto = new RestaurantDTO(
                resto.getId(),
                "Au Buffet",          // nouveau nom
                resto.getAdresse(),
                resto.getCodePostal(),
                resto.getVille()
        );
        restaurantService.edit(resto.getId(),updatedResto);

        RestaurantDTO result = restaurantService.findById(resto.getId());
        assertNotNull(result);
        assertEquals("Au Buffet", result.getNom());
        assertNotEquals(resto.getNom(), result.getNom());
    }

    @Test
    void getFilteredRestaurants() throws Exception {
        restaurantService.create(resto1);
        restaurantService.create(resto2);

        RestaurantDTO expected = new RestaurantDTO(null,"Chez Toto","3 rue picard","64000","Paris");
        RestaurantDTO filter = new RestaurantDTO(null,"To",null,"64",null);
        List<RestaurantDTO> filtered = restaurantService.findAllForViewFiltered(filter);
        assertEquals(1, filtered.size());
        RestaurantDTO result = filtered.getFirst();
        assertEquals(expected.getNom(),result.getNom());
        assertEquals(expected.getAdresse(),result.getAdresse());
        assertEquals(expected.getCodePostal(),result.getCodePostal());
        assertEquals(expected.getVille(),result.getVille());

        //aucun restaurant ne correspond à ce filtre
        RestaurantDTO filter2 = new RestaurantDTO(null,"ABCD",null,"1234",null);
        List<RestaurantDTO> filtered2 = restaurantService.findAllForViewFiltered(filter2);
        assertEquals(0, filtered2.size());
    }
}
