package com.gdu.wacdo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollaborateurAffectationFilterDTO {
    private String collaborateurNom;
    private String collaborateurPrenom;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate affectationDateDebut;
    private String fonction;
}
