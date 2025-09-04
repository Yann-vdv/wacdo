package com.gdu.wacdo.repositories;
import com.gdu.wacdo.entities.Collaborateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

import com.gdu.wacdo.entities.Affectation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AffectationRepository extends JpaRepository<Affectation, Long> {
    List<Affectation> findByCollaborateurId(Long collaborateurId);

    List<Affectation> findByRestaurantId(Long restaurantId);

    List<Affectation> findByFonctionId(Long fonctionId);

    List<Affectation> findByDateDebutBeforeAndDateFinAfter(LocalDate dateDebut, LocalDate dateFin);

    @Query("SELECT a.collaborateur FROM Affectation a " +
            "WHERE a.restaurant.id = :restaurantId " +
            "AND a.dateDebut <= CURRENT_DATE " +
            "AND (a.dateFin IS NULL OR a.dateFin >= CURRENT_DATE)")
    List<Collaborateur> findCurrentEmployeesByRestaurant(@Param("restaurantId") Long restaurantId);
}
