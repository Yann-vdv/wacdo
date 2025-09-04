package com.gdu.wacdo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class EditAffectationDTO {
    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateDebut;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateFin;
    private Long restaurant;
    private Long collaborateur;
    private Long fonction;

    public EditAffectationDTO(AffectationDTO affectationDTO) {
        log.info("construct editAffDTO : {}", affectationDTO);
        this.id = affectationDTO.getId();
        this.dateDebut = affectationDTO.getDateDebut();
        this.dateFin = affectationDTO.getDateFin();
        this.restaurant = affectationDTO.getRestaurant().getId();
        this.collaborateur = affectationDTO.getCollaborateur().getId();
        this.fonction = affectationDTO.getFonction().getId();
    }
}
