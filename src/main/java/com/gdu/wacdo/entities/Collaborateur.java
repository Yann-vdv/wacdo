package com.gdu.wacdo.entities;

import jakarta.persistence.*;
import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "Collaborateurs")
@Data
public class Collaborateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;

    private Date dateEmbauche;
    
    private boolean administrateur;
    private String passWord;

    @OneToMany(mappedBy = "collaborateur", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Affectation> affectations;
}
