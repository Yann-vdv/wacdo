package com.gdu.wacdo.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

import com.gdu.wacdo.entities.Affectation;

public interface AffectationRepository extends JpaRepository<Affectation, Long> {
    List<Affectation> findByCollaborateurId(Long collaborateurId);

    List<Affectation> findByRestaurantId(Long restaurantId);

    List<Affectation> findByFonctionId(Long fonctionId);

    List<Affectation> findByDateDebutBeforeAndDateFinAfter(LocalDate dateDebut, LocalDate dateFin);
}
