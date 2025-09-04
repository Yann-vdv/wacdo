package com.gdu.wacdo.entities;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Affectations")
@Data
public class Affectation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateDebut;
    private LocalDate dateFin;
    
    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;
    @ManyToOne
    @JoinColumn(name = "collaborateur_id", nullable = false)
    private Collaborateur collaborateur;
    @ManyToOne
    @JoinColumn(name = "fonction_id", nullable = false)
    private Fonction fonction;
}
