package com.gdu.wacdo.services;

import com.gdu.wacdo.DTO.NewRestaurantDTO;
import com.gdu.wacdo.DTO.RestaurantDTO;
import com.gdu.wacdo.repositories.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ActiveProfiles("test")
public class RestaurantServiceTest {

    @Autowired
    private RestaurantService restaurantService;

    private RestaurantRepository restaurantRepository;

    @BeforeAll
    void setup() {
        restaurantService.deleteAll();
    }

    @Test
    void createRestaurant() throws Exception {
        NewRestaurantDTO resto1 = new NewRestaurantDTO("Chez Toto","3 rue picard","64000","Paris");
        RestaurantDTO newResto = restaurantService.create(resto1);
        assertEquals(1, restaurantService.findAllForView().size());
    }
}
