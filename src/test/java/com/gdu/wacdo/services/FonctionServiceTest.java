package com.gdu.wacdo.services;

import com.gdu.wacdo.DTO.NewFonctionDTO;
import com.gdu.wacdo.DTO.FonctionDTO;
import com.gdu.wacdo.repositories.FonctionRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ActiveProfiles("test")
public class FonctionServiceTest {

    @Autowired
    private FonctionService fonctionService;

    private FonctionRepository fonctionRepository;

    @BeforeEach
    void setup() {
        fonctionService.deleteAll();
    }

    NewFonctionDTO fonction1 = new NewFonctionDTO("cuisinier");
    NewFonctionDTO fonction2 = new NewFonctionDTO("serveur");

    @Test
    void noFonctionsYet() throws Exception {
        assertEquals(0, fonctionService.findAllForView().size());
    }

    @Test
    void createFonction() throws Exception {
        fonctionService.create(fonction1);
        fonctionService.create(fonction2);

        assertEquals(2, fonctionService.findAllForView().size());
    }

    @Test
    void getFonction() throws Exception {
        FonctionDTO created = fonctionService.create(fonction1);

        FonctionDTO fonction = fonctionService.findById(created.getId());
        assertNotNull(fonction);
        assertEquals(created, fonction);
    }

    @Test
    void updateFonction() throws Exception {
        FonctionDTO fonction = fonctionService.create(fonction1);

        FonctionDTO updatedFonction = new FonctionDTO(
                fonction.getId(),
                "livreur"           // nouveau nom
        );
        fonctionService.edit(fonction.getId(),updatedFonction);

        FonctionDTO result = fonctionService.findById(fonction.getId());
        assertNotNull(result);
        assertEquals("livreur", result.getNom());
        assertNotEquals(fonction.getNom(), result.getNom());
    }

    @Test
    void getFilteredFonctions() throws Exception {
        fonctionService.create(fonction1);
        fonctionService.create(fonction2);

        FonctionDTO expected = new FonctionDTO(null,"serveur");
        FonctionDTO filter = new FonctionDTO(null,"eu");
        List<FonctionDTO> filtered = fonctionService.findAllForViewFiltered(filter);
        assertEquals(1, filtered.size());
        FonctionDTO result = filtered.getFirst();
        assertEquals(expected.getNom(),result.getNom());

        //aucune fonction ne correspond à ce filtre
        FonctionDTO filter2 = new FonctionDTO(null,"ABCD");
        List<FonctionDTO> filtered2 = fonctionService.findAllForViewFiltered(filter2);
        assertEquals(0, filtered2.size());
    }
}
