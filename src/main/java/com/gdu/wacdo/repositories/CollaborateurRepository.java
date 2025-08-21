package com.gdu.wacdo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gdu.wacdo.entities.Collaborateur;

public interface CollaborateurRepository extends JpaRepository<Collaborateur, Long> {
    
    List<Collaborateur> findByNom(String nom);

    List<Collaborateur> findByAdministrateurTrue();
}
