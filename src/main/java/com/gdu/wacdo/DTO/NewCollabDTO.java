package com.gdu.wacdo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewCollabDTO {
    private String nom;
    private String prenom;
    private String email;
    private LocalDate dateEmbauche;
    private boolean administrateur;
    private String passWord;
}
