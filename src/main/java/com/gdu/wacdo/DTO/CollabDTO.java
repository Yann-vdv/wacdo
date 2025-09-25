package com.gdu.wacdo.DTO;

import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;

@Data
public class CollabDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private LocalDate dateEmbauche;
}
