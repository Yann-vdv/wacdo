package com.gdu.wacdo.repositories;

import com.gdu.wacdo.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gdu.wacdo.entities.Fonction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FonctionRepository extends JpaRepository<Fonction, Long> {

    Fonction findByNom(String nom);

    @Query("SELECT f FROM Fonction f " +
            "WHERE (:nom IS NULL OR f.nom LIKE %:nom%)")
    List<Fonction> findAllFiltered(@Param("nom") String nom);
}
