package com.gdu.wacdo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gdu.wacdo.entities.Fonction;

public interface FonctionRepository extends JpaRepository<Fonction, Long> {

    Fonction findByNom(String nom);
    
}
