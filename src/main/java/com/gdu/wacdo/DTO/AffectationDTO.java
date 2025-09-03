package com.gdu.wacdo.DTO;

import com.gdu.wacdo.entities.Collaborateur;
import com.gdu.wacdo.entities.Fonction;
import com.gdu.wacdo.entities.Restaurant;
import lombok.Data;

import java.sql.Date;

@Data
public class AffectationDTO {
    private Long id;
    private String nom;
    private Date dateDebut;
    private Date dateFin;
    private Restaurant restaurant;
    private Collaborateur collaborateur;
    private Fonction fonction;
}
