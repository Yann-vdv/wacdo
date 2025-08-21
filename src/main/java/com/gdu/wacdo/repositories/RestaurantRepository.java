package com.gdu.wacdo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gdu.wacdo.entities.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    
    List<Restaurant> findByVille(String ville);

    List<Restaurant> findByCodePostal(String codePostal);

}
