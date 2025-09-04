package com.gdu.wacdo.DTO;

import com.gdu.wacdo.entities.Collaborateur;
import com.gdu.wacdo.entities.Fonction;
import com.gdu.wacdo.entities.Restaurant;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Data
@Slf4j
public class AffectationDTO {
    private Long id;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Restaurant restaurant;
    private Collaborateur collaborateur;
    private Fonction fonction;
}
