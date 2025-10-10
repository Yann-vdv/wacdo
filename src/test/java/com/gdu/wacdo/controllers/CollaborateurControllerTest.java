package com.gdu.wacdo.controllers;

import com.gdu.wacdo.DTO.AffectationDTO;
import com.gdu.wacdo.DTO.CollabDTO;
import com.gdu.wacdo.DTO.CollaborateurAffectationFilterDTO;
import com.gdu.wacdo.DTO.NewCollabDTO;
import com.gdu.wacdo.entities.Collaborateur;
import com.gdu.wacdo.entities.Fonction;
import com.gdu.wacdo.entities.Restaurant;
import com.gdu.wacdo.entities.Status;
import com.gdu.wacdo.repositories.CollaborateurRepository;
import com.gdu.wacdo.services.CollaborateurService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    List<CollabDTO> collabs;

    @BeforeEach
    void setup() {
        collabs = new ArrayList<>(List.of(
                new CollabDTO(1L,"Michel","Jean","JeanMichel@gmail.com", LocalDate.now()),
                new CollabDTO(2L,"Jacke","Jean","JeanJack@gmail.com", LocalDate.of(2015,6,19))
        ));
    }

    @Test
    void getCollaborateursPage_shouldReturnCollaborateursView() throws Exception {
        given(collaborateurService.findAllForView()).willReturn(collabs);

        mockMvc.perform(get("/collaborateurs"))
                .andExpect(status().isOk())
                .andExpect(view().name("collaborateurs"))
                .andExpect(model().attributeExists("response"))
                .andExpect(model().attribute("response",hasProperty("status", equalTo(Status.SUCCESS))))
                .andExpect(model().attribute("response",hasProperty("data", hasSize(2))))
                .andExpect(model().attribute("response",hasProperty("data", everyItem(instanceOf(CollabDTO.class)))));
    }

    @Test
    void getCollaborateursFilteredPage_shouldReturnCollaborateursFilteredView() throws Exception {
        CollabDTO filter = new CollabDTO(null,"el",null,null,null);
        given(collaborateurService.findAllForViewFiltered(filter)).willReturn(List.of(collabs.getFirst()));

        mockMvc.perform(post("/collaborateurs/filtered")
                        .param("nom", filter.getNom())
                        .param("prenom", filter.getPrenom())
                        .param("email", filter.getEmail()))
                .andExpect(status().isOk())
                .andExpect(view().name("collaborateurs"))
                .andExpect(model().attributeExists("response"))
                .andExpect(model().attribute("response",hasProperty("status", equalTo(Status.SUCCESS))))
                .andExpect(model().attribute("response", allOf(
                        hasProperty("data", hasSize(1)),
                        hasProperty("data", contains(hasProperty("id", equalTo(collabs.getFirst().getId()))))
                )));
    }

    @Test
    void getCollaborateurPage_shouldReturnCollaborateurView() throws Exception {
        given(collaborateurService.findById(collabs.getFirst().getId())).willReturn(collabs.getFirst());

        mockMvc.perform(get("/collaborateurs/"+collabs.getFirst().getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("collaborateur"))
                .andExpect(model().attributeExists("response"))
                .andExpect(model().attribute("response",hasProperty("status", equalTo(Status.SUCCESS))))
                .andExpect(model().attribute("response",hasProperty("data", instanceOf(CollabDTO.class))))
                .andExpect(model().attribute("response",hasProperty("data", hasProperty("id", equalTo(collabs.getFirst().getId())))));
    }

    @Test
    void getCollaborateurPageFilteredAffectation_shouldReturnCollaborateurViewAffectation() throws Exception {
        CollaborateurAffectationFilterDTO filter = new CollaborateurAffectationFilterDTO("ja",null,null,null);
        List<AffectationDTO> historyAffectaionList = List.of(
                new AffectationDTO(1L,LocalDate.of(2020,5,1),LocalDate.of(2022,9,30),new Restaurant(),new Collaborateur(),new Fonction()),
                new AffectationDTO(2L,LocalDate.of(2015,8,1),LocalDate.of(2018,4,30),new Restaurant(),new Collaborateur(),new Fonction())
        );
        given(collaborateurService.findById(collabs.getFirst().getId())).willReturn(collabs.getFirst());
        given(collaborateurService.findHistoryAffectationsForViewFiltred(collabs.getFirst().getId(),filter)).willReturn(historyAffectaionList);

        mockMvc.perform(post("/collaborateurs/"+collabs.getFirst().getId()+"/filter")
                        .param("collaborateurNom", filter.getCollaborateurNom())
                        .param("collaborateurPrenom", filter.getCollaborateurPrenom())
                        .param("affectationDateDebut", filter.getAffectationDateDebut() != null ? String.valueOf(filter.getAffectationDateDebut()) : "")
                        .param("fonction", filter.getFonction()))
                .andExpect(status().isOk())
                .andExpect(view().name("collaborateur"))
                .andExpect(model().attributeExists("collabHistoryAff"))
                .andExpect(model().attribute("collabHistoryAff",hasSize(2)))
                .andExpect(model().attribute("collabHistoryAff",everyItem(instanceOf(AffectationDTO.class))));
    }

    @Test
    void getCollaborateurPage_shouldNotFindAndRedirect() throws Exception {
        given(collaborateurService.findById(999L)).willReturn(null);

        mockMvc.perform(get("/collaborateurs/"+999L))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/collaborateurs?error=Collaborateur introuvable"));
    }

    @Test
    void postNewCollaborateur_shouldCreateCollaborateur() throws Exception {
        NewCollabDTO newCollab = new NewCollabDTO("newNom","newPrenom","new@gmail.com",LocalDate.now(),false,null);
        CollabDTO collab = new CollabDTO(3L,newCollab.getNom(),newCollab.getPrenom(),newCollab.getEmail(),newCollab.getDateEmbauche());
        given(collaborateurService.create(newCollab)).willReturn(collab);

        mockMvc.perform(post("/collaborateurs/new")
                    .param("nom", newCollab.getNom())
                    .param("prenom", newCollab.getPrenom())
                    .param("email", newCollab.getEmail())
                    .param("dateEmbauche", newCollab.getDateEmbauche().toString())
                    .param("administrateur", String.valueOf(newCollab.isAdministrateur())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/collaborateurs/"+collab.getId()));

        verify(collaborateurService).create(any(NewCollabDTO.class));
    }

    @Test
    void postEditCollaborateur_shouldEditCollaborateur() throws Exception {
        CollabDTO editedCollab = new CollabDTO(collabs.getFirst().getId(), collabs.getFirst().getNom(),"Maxime",collabs.getFirst().getEmail(),collabs.getFirst().getDateEmbauche());
        given(collaborateurService.edit(editedCollab.getId(),editedCollab)).willReturn(editedCollab);

        mockMvc.perform(post("/collaborateurs/edit/"+collabs.getFirst().getId())
                        .param("nom", editedCollab.getNom())
                        .param("prenom", editedCollab.getPrenom())
                        .param("email", editedCollab.getEmail())
                        .param("dateEmbauche", editedCollab.getDateEmbauche().toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("collaborateur"))
                .andExpect(model().attributeExists("response"))
                .andExpect(model().attribute("response",hasProperty("status", equalTo(Status.SUCCESS))))
                .andExpect(model().attribute("response",hasProperty("data", instanceOf(CollabDTO.class))))
                .andExpect(model().attribute("response",hasProperty("data",hasProperty("prenom", equalTo("Maxime")))));

        verify(collaborateurService).edit(eq(editedCollab.getId()),any(CollabDTO.class));
    }

    @Test
    void deleteCollaborateur_shouldDeleteCollaborateur() throws Exception {
        given(collaborateurService.delete(collabs.getFirst().getId())).willReturn(true);

        mockMvc.perform(get("/collaborateurs/delete/"+collabs.getFirst().getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/collaborateurs"));

        verify(collaborateurService).delete(eq(collabs.getFirst().getId()));
    }

    @Test
    void deleteCollaborateur_shouldNotFindAndRedirect() throws Exception {
        given(collaborateurService.delete(999L)).willReturn(false);

        mockMvc.perform(get("/collaborateurs/delete/"+collabs.getFirst().getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/collaborateurs/"+collabs.getFirst().getId()+"?error=La suppression du collaborateur a échouée"));

        verify(collaborateurService).delete(eq(collabs.getFirst().getId()));
    }
}
