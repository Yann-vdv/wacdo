package com.gdu.wacdo.DTO;

import lombok.Data;

import java.sql.Date;

@Data
public class RestaurantDTO {
    private Long id;
    private String nom;
    private String adresse;
    private String codePostal;
    private String ville;
}
