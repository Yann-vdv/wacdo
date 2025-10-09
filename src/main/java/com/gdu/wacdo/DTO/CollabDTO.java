package com.gdu.wacdo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollabDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private LocalDate dateEmbauche;
}
