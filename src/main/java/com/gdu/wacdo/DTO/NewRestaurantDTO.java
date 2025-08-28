package com.gdu.wacdo.DTO;

import lombok.Data;

@Data
public class NewRestaurantDTO {
    private String nom;
    private String adresse;
    private String codePostal;
    private String ville;
}
