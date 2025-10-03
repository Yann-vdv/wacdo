package com.gdu.wacdo.services;

import com.gdu.wacdo.DTO.CollabDTO;
import com.gdu.wacdo.DTO.NewRestaurantDTO;
import com.gdu.wacdo.DTO.RestaurantDTO;
import com.gdu.wacdo.controllers.RestaurantController;
import com.gdu.wacdo.entities.Collaborateur;
import com.gdu.wacdo.entities.Restaurant;
import com.gdu.wacdo.entities.Status;
import com.gdu.wacdo.repositories.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ActiveProfiles("test")
public class RestaurantServiceTest {

    @Autowired
    private RestaurantService restaurantService;

    @MockitoBean
    private RestaurantRepository restaurantRepository;

    @MockitoBean
    private ModelMapper modelMapper;

//    @BeforeAll
//    void setup() {
//        restaurantRepository.deleteAll();
//    }

    @Test
    void createRestaurant() throws Exception {
        NewRestaurantDTO resto1 = new NewRestaurantDTO("Chez Toto","3 rue picard","64000","Paris");
        RestaurantDTO newResto = restaurantService.create(resto1);
        log.info("DEBUG newResto : {}",newResto);
        assertEquals(1, restaurantService.findAllForView().size());
//        verify(restaurantRepository, times(1)).save(any(Restaurant.class));
    }


}
