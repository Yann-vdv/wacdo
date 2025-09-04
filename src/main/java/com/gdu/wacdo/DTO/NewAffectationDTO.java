package com.gdu.wacdo.DTO;

import com.gdu.wacdo.entities.Collaborateur;
import com.gdu.wacdo.entities.Fonction;
import com.gdu.wacdo.entities.Restaurant;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;

@Data
public class NewAffectationDTO {
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Long restaurant;
    private Long collaborateur;
    private Long fonction;
}
