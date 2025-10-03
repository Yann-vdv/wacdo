package com.gdu.wacdo.controllers;

import com.gdu.wacdo.DTO.NewRestaurantDTO;
import com.gdu.wacdo.DTO.RestaurantDTO;
import com.gdu.wacdo.entities.Restaurant;
import com.gdu.wacdo.entities.Status;
import com.gdu.wacdo.repositories.RestaurantRepository;
import com.gdu.wacdo.services.RestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RestaurantService restaurantService;

    @MockitoBean
    private RestaurantRepository restaurantRepository;

//    @BeforeAll
//    void setup() {
//        restaurantRepository.deleteAll();
//        NewRestaurantDTO resto1 = new NewRestaurantDTO("Chez Toto","3 rue picard","64000","Paris");
//        NewRestaurantDTO resto2 = new NewRestaurantDTO("La Bonne Table","26 rue lafontaine","78000","Marseille");
//        restaurantService.create(resto1);
//        restaurantService.create(resto2);
//        log.info("j'ai créé des restos");
//    }

//    @Test
//    void postRestaurant_shouldReturnRestaurantView() throws Exception {
//
//
//    }
    @Disabled
    @Test
    void getRestaurantsPage_shouldReturn2RestaurantsView() throws Exception {
        mockMvc.perform(get("/restaurants"))
                .andExpect(status().isOk())
                .andExpect(view().name("restaurants"))
                .andExpect(model().attributeExists("response"))
                .andExpect(model().attribute("response",hasProperty("status", equalTo(Status.SUCCESS))))
                .andExpect(model().attribute("response",hasProperty("data", hasSize(2))))
                .andExpect(model().attribute("response",hasProperty("data", everyItem(instanceOf(RestaurantDTO.class)))));
    }



//    @Test
//    void getRestaurantsPage_shouldReturnRestaurantsView() throws Exception {
//
//        mockMvc.perform(get("/restaurants"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("restaurants"))
//                .andExpect(model().attribute("response",
//                        Matchers.hasProperty("data", everyItem(instanceOf(RestaurantDTO.class)))))
//                .andExpect(model().attribute("response",
//                        Matchers.hasProperty("status", Matchers.equalTo(Status.SUCCESS))));
//    }
}
