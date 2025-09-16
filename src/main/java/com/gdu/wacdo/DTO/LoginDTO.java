package com.gdu.wacdo.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LoginDTO {
    private String nom;
    private String prenom;
    private String password;
}
