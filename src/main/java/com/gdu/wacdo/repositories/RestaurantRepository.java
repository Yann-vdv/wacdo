package com.gdu.wacdo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gdu.wacdo.entities.Restaurant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    
    List<Restaurant> findByVille(String ville);

    List<Restaurant> findByCodePostal(String codePostal);

    @Query("SELECT r FROM Restaurant r " +
            "WHERE (:nom IS NULL OR r.nom LIKE %:nom%) " +
            "AND (:adresse IS NULL OR r.adresse LIKE %:adresse%) " +
            "AND (:codePostal IS NULL OR r.codePostal LIKE %:codePostal%) " +
            "AND (:ville IS NULL OR r.ville LIKE %:ville%)")
    List<Restaurant> findAllFiltered(@Param("nom") String nom,
                                            @Param("adresse") String adresse,
                                            @Param("codePostal") String  codePostal,
                                            @Param("ville") String ville);
}
