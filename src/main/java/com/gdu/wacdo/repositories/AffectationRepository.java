package com.gdu.wacdo.repositories;
import com.gdu.wacdo.entities.Collaborateur;
import com.gdu.wacdo.entities.Restaurant;
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

    @Query("SELECT a FROM Affectation a " +
            "WHERE a.collaborateur.id = :collaborateurId " +
            "AND a.dateDebut <= CURRENT_DATE " +
            "AND (a.dateFin IS NULL OR a.dateFin >= CURRENT_DATE)")
    List<Affectation> findCurrentByCollaborateurId(@Param("collaborateurId") Long collaborateurId);

    @Query("SELECT a FROM Affectation a " +
            "WHERE a.collaborateur.id = :collaborateurId " +
            "AND a.dateFin < CURRENT_DATE " +
            "ORDER BY a.dateDebut DESC")
    List<Affectation> findHistoryByCollaborateurId(@Param("collaborateurId") Long collaborateurId);

    @Query("SELECT a FROM Affectation a " +
            "WHERE a.collaborateur.id = :collaborateurId " +
            "ORDER BY a.dateDebut DESC")
    List<Affectation> findByCollaborateurIdOrdered(@Param("collaborateurId") Long collaborateurId);

    @Query("SELECT a FROM Affectation a " +
            "WHERE (:dateDebut IS NULL OR a.dateDebut = :dateDebut) " +
            "AND (:dateFin IS NULL OR a.dateFin = :dateFin) " +
            "AND (:restaurantId IS NULL OR a.restaurant.id = :restaurantId) " +
            "AND (:collaborateurId IS NULL OR a.collaborateur.id = :collaborateurId) " +
            "AND (:fonctionId IS NULL OR a.fonction.id = :fonctionId)")
    List<Affectation> findAllFiltered(@Param("dateDebut") LocalDate dateDebut,
                                      @Param("dateFin") LocalDate dateFin,
                                     @Param("restaurantId") Long restaurantId,
                                     @Param("collaborateurId") Long collaborateurId,
                                     @Param("fonctionId") Long fonctionId);
}
