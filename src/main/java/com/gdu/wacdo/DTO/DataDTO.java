package com.gdu.wacdo.DTO;

import lombok.Data;

import java.util.List;

@Data
public class DataDTO {
    private List<CollabDTO> collabs;
    private List<RestaurantDTO> restaurants;
    private List<FonctionDTO> fonctions;

    public DataDTO(List<CollabDTO> collabs, List<RestaurantDTO> restaurants, List<FonctionDTO> fonctions) {
        this.restaurants = restaurants;
        this.collabs = collabs;
        this.fonctions = fonctions;
    }
}
