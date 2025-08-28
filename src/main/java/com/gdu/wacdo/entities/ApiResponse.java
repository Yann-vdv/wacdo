package com.gdu.wacdo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ApiResponse<T> {
    private final Status status;
    private final T data;
    private final Boolean notification;
    private final String message;

    public String getAlertClass() {
        return switch (status) {
            case SUCCESS -> "alert-success";
            case FAILURE -> "alert-warning";
            case ERROR -> "alert-danger";
        };
    }
}