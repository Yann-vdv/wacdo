package com.gdu.wacdo.DTO;

import lombok.Data;

import java.sql.Date;

@Data
public class NewCollabDTO {
    private String nom;
    private String prenom;
    private Date dateEmbauche;
    private boolean administrateur;
    private String passWord;
}
