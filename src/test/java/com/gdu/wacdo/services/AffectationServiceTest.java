package com.gdu.wacdo.services;

import com.gdu.wacdo.DTO.*;
import com.gdu.wacdo.repositories.AffectationRepository;
import com.gdu.wacdo.repositories.CollaborateurRepository;
import com.gdu.wacdo.repositories.FonctionRepository;
import com.gdu.wacdo.repositories.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ActiveProfiles("test")
public class AffectationServiceTest {

    @Autowired
    private AffectationService affectationService;
    @Autowired
    private CollaborateurService collaborateurService;
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private FonctionService fonctionService;

    private AffectationRepository affectationRepository;
    private CollaborateurRepository collaborateurRepository;
    private RestaurantRepository restaurantRepository;
    private FonctionRepository fonctionRepository;

    private CollabDTO collab1;
    private CollabDTO collab2;
    private RestaurantDTO resto1;
    private RestaurantDTO resto2;

    private NewAffectationDTO affectation1;
    private NewAffectationDTO affectation2;
    private NewAffectationDTO affectation3;

    @BeforeAll
    void setup() {
        NewCollabDTO newCollab1 = new NewCollabDTO("Martin","Toto","toto@gmail.com", LocalDate.of(2022,5,23),true,"test1234");
        NewCollabDTO newCollab2 = new NewCollabDTO("Douche","Marie","marie@gmail.com", LocalDate.of(2020,9,5),false,null);
        this.collab1 = collaborateurService.create(newCollab1);
        this.collab2 = collaborateurService.create(newCollab2);

        NewRestaurantDTO newResto1 = new NewRestaurantDTO("Chez Toto","3 rue picard","64000","Paris");
        NewRestaurantDTO newResto2 = new NewRestaurantDTO("La Bonne Table","26 rue lafontaine","78000","Marseille");
        this.resto1 = restaurantService.create(newResto1);
        this.resto2 = restaurantService.create(newResto2);

        NewFonctionDTO newFonction1 = new NewFonctionDTO("cuisinier");
        NewFonctionDTO newFonction2 = new NewFonctionDTO("serveur");
        FonctionDTO fonction1 = fonctionService.create(newFonction1);
        FonctionDTO fonction2 = fonctionService.create(newFonction2);

        this.affectation1 = new NewAffectationDTO(LocalDate.of(2020,5,1),LocalDate.of(2022,9,30),resto1.getId(),collab1.getId(),fonction1.getId());
        this.affectation2 = new NewAffectationDTO(LocalDate.of(2024,3,15),null,resto2.getId(),collab2.getId(),fonction2.getId());
        this.affectation3 = new NewAffectationDTO(LocalDate.of(2022,10,15),LocalDate.of(2024,7,30),resto2.getId(),collab1.getId(),fonction1.getId());
    }

    @BeforeEach
    void setup2() {
        affectationService.deleteAll();
    }

    @Test
    void noAffectationsYet() throws Exception {
        assertEquals(0, affectationService.findAllForView().size());
    }

    @Test
    void createAffectation() throws Exception {
        affectationService.create(affectation1);
        affectationService.create(affectation2);

        assertEquals(2, affectationService.findAllForView().size());
    }

    @Test
    void getAffectation() throws Exception {
        AffectationDTO created = affectationService.create(affectation1);

        AffectationDTO affectation = affectationService.findById(created.getId());
        assertNotNull(affectation);
        assertEquals(created.getId(), affectation.getId());
    }

    @Test
    void updateAffectation() throws Exception {
        AffectationDTO affectation = affectationService.create(affectation1);

        EditAffectationDTO updatedAffectation = new EditAffectationDTO(
                affectation.getId(),
                affectation.getDateDebut(),
                LocalDate.now(),
                affectation.getRestaurant().getId(),
                collab2.getId(),
                affectation.getFonction().getId()
        );
        affectationService.edit(affectation.getId(),updatedAffectation);

        AffectationDTO result = affectationService.findById(affectation.getId());
        assertNotNull(result);
        assertEquals(collab2.getId(), result.getCollaborateur().getId());
        assertNotEquals(affectation.getCollaborateur().getId(), result.getCollaborateur().getId());
    }

    @Test
    void getFilteredAffectations() throws Exception {
        AffectationDTO expected = affectationService.create(affectation1);
        affectationService.create(affectation2);

        NewAffectationDTO filter = new NewAffectationDTO(null,null,null,collab1.getId(),null);
        List<AffectationDTO> filtered = affectationService.findAllForViewFiltered(filter);
        assertEquals(1, filtered.size());
        AffectationDTO result = filtered.getFirst();
        assertEquals(expected.getId(),result.getId());

        //aucune affectation ne correspond à ce filtre
        NewAffectationDTO filter2 = new NewAffectationDTO(null,null,null,null,999L);
        List<AffectationDTO> filtered2 = affectationService.findAllForViewFiltered(filter2);
        assertEquals(0, filtered2.size());
    }

    @Test
    void getRestaurantCollabs() {
        affectationService.create(affectation1);
        AffectationDTO expectedCurrentCollabs = affectationService.create(affectation2);
        AffectationDTO expectedHistoryCollabs = affectationService.create(affectation3);
        CollaborateurAffectationFilterDTO emptyFilter = new CollaborateurAffectationFilterDTO();

        //Collaborateur actuels du restaurant
        List<RestaurantCollaborateurDTO> resto2CurrentCollabs = restaurantService.findCurrentCollabsFiltered(resto2.getId(), emptyFilter);
        assertEquals(1, resto2CurrentCollabs.size());
        RestaurantCollaborateurDTO resultCurrent = resto2CurrentCollabs.getFirst();
        assertEquals(expectedCurrentCollabs.getCollaborateur().getId(),resultCurrent.getCollabId());

        //Ancien collaborateur du restaurant
        List<RestaurantCollaborateurDTO> resto2HistoryCollabs = restaurantService.findHistoryCollabsFiltered(resto2.getId(), emptyFilter);
        assertEquals(1, resto2HistoryCollabs.size());
        RestaurantCollaborateurDTO resultHistory = resto2HistoryCollabs.getFirst();
        assertEquals(expectedHistoryCollabs.getCollaborateur().getId(),resultHistory.getCollabId());
    }

    @Test
    void getCollabAffectations() {
        List<AffectationDTO> expectedCollab1HistoryAffectations = new ArrayList<>(List.of());
        expectedCollab1HistoryAffectations.add(affectationService.create(affectation1));
        AffectationDTO expectedCollab2CurrentAffectation = affectationService.create(affectation2);
        expectedCollab1HistoryAffectations.add(affectationService.create(affectation3));
        CollaborateurAffectationFilterDTO emptyFilter = new CollaborateurAffectationFilterDTO();

        //Collab1 :
        //current (pas d'affectation en cours)
        List<AffectationDTO> resultCollab1CurrentAffectations = collaborateurService.findCurrentAffectationsForView(collab1.getId());
        assertEquals(0, resultCollab1CurrentAffectations.size());
        //history (2 anciennes affectations)
        List<AffectationDTO> resultCollab1HistoryAffectations = collaborateurService.findHistoryAffectationsForViewFiltred(collab1.getId(), emptyFilter);
        assertEquals(2, resultCollab1HistoryAffectations.size());
        Set<Long> expectedIds = expectedCollab1HistoryAffectations.stream()
                .map(AffectationDTO::getId)
                .collect(Collectors.toSet());
        Set<Long> actualIds = resultCollab1HistoryAffectations.stream()
                .map(AffectationDTO::getId)
                .collect(Collectors.toSet());
        assertEquals(expectedIds, actualIds);   //vérifie que les ids des affectations reçus, correspondent à celles attendues

        //Collab2 :
        //current (une affectation)
        List<AffectationDTO> resultCollab2CurrentAffectations = collaborateurService.findCurrentAffectationsForView(collab2.getId());
        assertEquals(1, resultCollab2CurrentAffectations.size());
        AffectationDTO resultCollab2CurrentAffectation = resultCollab2CurrentAffectations.getFirst();
        assertEquals(expectedCollab2CurrentAffectation.getId(),resultCollab2CurrentAffectation.getId());
        //History (pas d'anciennes affectations)
        List<AffectationDTO> resultCollab2HistoryAffectations = collaborateurService.findHistoryAffectationsForViewFiltred(collab2.getId(), emptyFilter);
        assertEquals(0, resultCollab2HistoryAffectations.size());
    }
}
