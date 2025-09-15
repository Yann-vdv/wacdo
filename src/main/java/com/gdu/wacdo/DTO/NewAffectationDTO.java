package com.gdu.wacdo.DTO;

import com.gdu.wacdo.entities.Collaborateur;
import com.gdu.wacdo.entities.Fonction;
import com.gdu.wacdo.entities.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewAffectationDTO {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateDebut;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateFin;
    private Long restaurant;
    private Long collaborateur;
    private Long fonction;
}
