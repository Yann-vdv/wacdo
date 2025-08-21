package com.gdu.wacdo.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Fonctions")
@Data
public class Fonction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @OneToMany(mappedBy = "fonction", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Affectation> affectations;
    
}
