package com.gdu.wacdo.DTO;

import com.gdu.wacdo.entities.Collaborateur;
import com.gdu.wacdo.entities.Fonction;
import com.gdu.wacdo.entities.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@Slf4j
public class CollaborateurAffectationsDTO {
    private List<AffectationDTO> currentAffectations;
    private List<AffectationDTO> history;
}
