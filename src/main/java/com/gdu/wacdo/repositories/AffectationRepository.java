package com.gdu.wacdo.repositories;
import com.gdu.wacdo.DTO.CollaborateurAffectationFilterDTO;
import com.gdu.wacdo.DTO.RestaurantCollaborateurDTO;
import com.gdu.wacdo.entities.Collaborateur;
import com.gdu.wacdo.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.gdu.wacdo.entities.Affectation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AffectationRepository extends JpaRepository<Affectation, Long> {
    List<Affectation> findByCollaborateurId(Long collaborateurId);

    List<Affectation> findByRestaurantId(Long restaurantId);

    List<Affectation> findByFonctionId(Long fonctionId);

    List<Affectation> findByDateDebutBeforeAndDateFinAfter(LocalDate dateDebut, LocalDate dateFin);

    @Query("SELECT new com.gdu.wacdo.DTO.RestaurantCollaborateurDTO(" +
            "a.collaborateur.id," +
            "a.collaborateur.nom," +
            "a.collaborateur.prenom," +
            "a.dateDebut," +
            "a.dateFin," +
            "a.fonction.nom) " +
            "FROM Affectation a " +
            "WHERE a.restaurant.id = :restaurantId " +
            "AND a.dateDebut <= CURRENT_DATE " +
            "AND (a.dateFin IS NULL OR a.dateFin >= CURRENT_DATE) " +
            "AND (:collaborateurNom IS NULL OR a.collaborateur.nom LIKE %:collaborateurNom%) " +
            "AND (:collaborateurPrenom IS NULL OR a.collaborateur.prenom LIKE %:collaborateurPrenom%) " +
            "AND (:affectationDateDebut IS NULL OR a.dateDebut = :affectationDateDebut) " +
            "AND (:fonction IS NULL OR a.fonction.nom LIKE %:fonction%) " +
            "ORDER BY a.dateDebut DESC")
    List<RestaurantCollaborateurDTO> findCurrentEmployeesByRestaurantFiltered(@Param("restaurantId") Long restaurantId,
                                                                                     @Param("collaborateurNom") String collaborateurNom,
                                                                                     @Param("collaborateurPrenom") String collaborateurPrenom,
                                                                                     @Param("affectationDateDebut") LocalDate affectationDateDebut,
                                                                                     @Param("fonction") String fonction);

    @Query("SELECT new com.gdu.wacdo.DTO.RestaurantCollaborateurDTO(" +
            "a.collaborateur.id," +
            "a.collaborateur.nom," +
            "a.collaborateur.prenom," +
            "a.dateDebut," +
            "a.dateFin," +
            "a.fonction.nom) " +
            "FROM Affectation a " +
            "WHERE a.restaurant.id = :restaurantId " +
            "AND a.dateFin < CURRENT_DATE " +
            "AND (:collaborateurNom IS NULL OR a.collaborateur.nom LIKE %:collaborateurNom%) " +
            "AND (:collaborateurPrenom IS NULL OR a.collaborateur.prenom LIKE %:collaborateurPrenom%) " +
            "AND (:affectationDateDebut IS NULL OR a.dateDebut = :affectationDateDebut) " +
            "AND (:fonction IS NULL OR a.fonction.nom LIKE %:fonction%) " +
            "ORDER BY a.dateDebut DESC")
    List<RestaurantCollaborateurDTO> findHistoryEmployeesByRestaurantFiltered(@Param("restaurantId") Long restaurantId,
                                                                                     @Param("collaborateurNom") String collaborateurNom,
                                                                                     @Param("collaborateurPrenom") String collaborateurPrenom,
                                                                                     @Param("affectationDateDebut") LocalDate affectationDateDebut,
                                                                                     @Param("fonction") String fonction);

    @Query("SELECT a " +
            "FROM Affectation a " +
            "WHERE a.collaborateur.id = :collaborateurId " +
            "AND a.dateDebut <= CURRENT_DATE " +
            "AND (a.dateFin IS NULL OR a.dateFin >= CURRENT_DATE) " +
            "ORDER BY a.dateDebut DESC")
    List<Affectation> findCurrentByCollaborateur(@Param("collaborateurId") Long collaborateurId);

    @Query("SELECT a " +
            "FROM Affectation a " +
            "WHERE a.collaborateur.id = :collaborateurId " +
            "AND a.dateFin < CURRENT_DATE " +
            "AND (:collaborateurNom IS NULL OR a.collaborateur.nom LIKE %:collaborateurNom%) " +
            "AND (:collaborateurPrenom IS NULL OR a.collaborateur.prenom LIKE %:collaborateurPrenom%) " +
            "AND (:affectationDateDebut IS NULL OR a.dateDebut = :affectationDateDebut) " +
            "AND (:fonction IS NULL OR a.fonction.nom LIKE %:fonction%) " +
            "ORDER BY a.dateDebut DESC")
    List<Affectation> findHistoryByCollaborateurFiltered(@Param("collaborateurId") Long collaborateurId,
                                                         @Param("collaborateurNom") String collaborateurNom,
                                                         @Param("collaborateurPrenom") String collaborateurPrenom,
                                                         @Param("affectationDateDebut") LocalDate affectationDateDebut,
                                                         @Param("fonction") String fonction);

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
