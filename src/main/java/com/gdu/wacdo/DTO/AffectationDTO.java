package com.gdu.wacdo.DTO;

import com.gdu.wacdo.entities.Collaborateur;
import com.gdu.wacdo.entities.Fonction;
import com.gdu.wacdo.entities.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AffectationDTO {
    private Long id;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Restaurant restaurant;
    private Collaborateur collaborateur;
    private Fonction fonction;
}
