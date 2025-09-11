package com.gdu.wacdo.repositories;

import java.util.Date;
import java.util.List;

import com.gdu.wacdo.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gdu.wacdo.entities.Collaborateur;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CollaborateurRepository extends JpaRepository<Collaborateur, Long> {
    
    List<Collaborateur> findByNom(String nom);

    List<Collaborateur> findByAdministrateurTrue();

    @Query("SELECT c FROM Collaborateur c " +
            "WHERE (:nom IS NULL OR c.nom LIKE %:nom%) " +
            "AND (:prenom IS NULL OR c.prenom LIKE %:prenom%) " +
            "AND (:dateEmbauche IS NULL OR c.dateEmbauche = :dateEmbauche)")
    List<Collaborateur> findAllFiltered(@Param("nom") String nom,
                                     @Param("prenom") String prenom,
                                     @Param("dateEmbauche") Date dateEmbauche);
}
