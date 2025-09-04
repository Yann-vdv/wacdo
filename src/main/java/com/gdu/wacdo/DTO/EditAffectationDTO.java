package com.gdu.wacdo.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Slf4j
public class EditAffectationDTO {
    private Long id;
    private LocalDate dateDebut;
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

    public EditAffectationDTO(Long id, LocalDate dateDebut, LocalDate dateFin, Long restaurant, Long collaborateur, Long fonction) {
        this.id = id;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.restaurant = restaurant;
        this.collaborateur = collaborateur;
        this.fonction = fonction;
    }
}
