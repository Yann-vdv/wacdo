package com.gdu.wacdo.DTO;

import lombok.Data;

import java.sql.Date;

@Data
public class CollabDTO {
    private Long id;
    private String nom;
    private String prenom;
    private Date dateEmbauche;
}
