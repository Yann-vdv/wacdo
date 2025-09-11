package com.gdu.wacdo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class RestaurantCollaborateurDTO {
    private Long collabId;
    private String collabNom;
    private String collabPrenom;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate affectationDateDebut;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate affectationDateFin;
    private String fonction;
}
