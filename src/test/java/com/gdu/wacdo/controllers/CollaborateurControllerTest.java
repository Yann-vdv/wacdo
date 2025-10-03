package com.gdu.wacdo.controllers;

import com.gdu.wacdo.DTO.CollabDTO;
import com.gdu.wacdo.entities.ApiResponse;
import com.gdu.wacdo.entities.Collaborateur;
import com.gdu.wacdo.entities.Status;
import com.gdu.wacdo.repositories.CollaborateurRepository;
import com.gdu.wacdo.services.CollaborateurService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@WebMvcTest(CollaborateurController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CollaborateurControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CollaborateurService collaborateurService;

    @MockitoBean
    private CollaborateurRepository collaborateurRepository;

//    @Test
//    void postCollaborateur_shouldReturnCollaborateurView() throws Exception {
//
//
//    }

    @Disabled
    @Test
    void logResponseData() throws Exception {

        MvcResult result = mockMvc.perform(get("/restaurants"))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView mav = result.getModelAndView();
        assert mav != null;

        Object response = mav.getModel().get("response");
        if (response instanceof ApiResponse<?> apiResponse) {
            log.info("DebugResponse status: {}", apiResponse.getStatus());
            log.info("DebugResponse data: {}", apiResponse.getData());
        } else {
            log.warn("DebugResponse is not an ApiResponse: {}", response);
        }
    }


    @Disabled
    @Test
    void getCollaborateursPage_shouldReturnCollaborateursView() throws Exception {

        Collaborateur collab1 = new Collaborateur(1L,"Michel","Jean","JeanMichel@gmail.com", LocalDate.now(),false,"",null);
        Collaborateur collab2 = new Collaborateur(2L,"Jacke","Jean","JeanJack@gmail.com", LocalDate.of(2015,6,19),true,"pass",null);
        given(collaborateurRepository.findAll()).willReturn(List.of(collab1, collab2));

        mockMvc.perform(get("/collaborateurs"))
                .andExpect(status().isOk())
                .andExpect(view().name("collaborateurs"))
                .andExpect(model().attributeExists("response"))
                .andExpect(model().attribute("response",hasProperty("status", equalTo(Status.SUCCESS))))
                .andExpect(model().attribute("response",hasProperty("data", hasSize(2))))
                .andExpect(model().attribute("response",hasProperty("data", everyItem(instanceOf(CollabDTO.class)))));
    }

    
}
