package com.gdu.wacdo.services;

import com.gdu.wacdo.DTO.NewCollabDTO;
import com.gdu.wacdo.DTO.CollabDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ActiveProfiles("test")
public class CollaborateurServiceTest {

    @Autowired
    private CollaborateurService collaborateurService;

    @BeforeEach
    void setup() {
        collaborateurService.deleteAll();
    }

    NewCollabDTO collab1 = new NewCollabDTO("Martin","Toto","toto@gmail.com", LocalDate.of(2022,5,23),true,"test1234");
    NewCollabDTO collab2 = new NewCollabDTO("Douche","Marie","marie@gmail.com", LocalDate.of(2020,9,5),false,null);

    @Test
    void noCollaborateursYet() throws Exception {
        assertEquals(0, collaborateurService.findAllForView().size());
    }

    @Test
    void createCollaborateur() throws Exception {
        collaborateurService.create(collab1);
        collaborateurService.create(collab2);

        assertEquals(2, collaborateurService.findAllForView().size());
    }

    @Test
    void getCollaborateur() throws Exception {
        CollabDTO created = collaborateurService.create(collab1);

        CollabDTO collab = collaborateurService.findById(created.getId());
        assertNotNull(collab);
        assertEquals(created, collab);
    }

    @Test
    void updateCollaborateur() throws Exception {
        CollabDTO collab = collaborateurService.create(collab1);

        CollabDTO updatedCollab = new CollabDTO(
                collab.getId(),
                "De fidele",          // nouveau nom
                collab.getPrenom(),
                collab.getEmail(),
                collab.getDateEmbauche()
        );
        collaborateurService.edit(collab.getId(),updatedCollab);

        CollabDTO result = collaborateurService.findById(collab.getId());
        assertNotNull(result);
        assertEquals("De fidele", result.getNom());
        assertNotEquals(collab.getNom(), result.getNom());
    }

    @Test
    void getFilteredCollaborateurs() throws Exception {
        collaborateurService.create(collab1);
        collaborateurService.create(collab2);

        CollabDTO expected = new CollabDTO(null,"Martin","Toto","toto@gmail.com", LocalDate.of(2022,5,23));
        CollabDTO filter = new CollabDTO(null,null,null,"toto",null);
        List<CollabDTO> filtered = collaborateurService.findAllForViewFiltered(filter);
        assertEquals(1, filtered.size());
        CollabDTO result = filtered.getFirst();
        assertEquals(expected.getNom(),result.getNom());
        assertEquals(expected.getPrenom(),result.getPrenom());
        assertEquals(expected.getEmail(),result.getEmail());
        assertEquals(expected.getDateEmbauche(),result.getDateEmbauche());

        //aucun collaborateur ne correspond à ce filtre
        CollabDTO filter2 = new CollabDTO(null,"ABCD",null,"1234",null);
        List<CollabDTO> filtered2 = collaborateurService.findAllForViewFiltered(filter2);
        assertEquals(0, filtered2.size());
    }
}
