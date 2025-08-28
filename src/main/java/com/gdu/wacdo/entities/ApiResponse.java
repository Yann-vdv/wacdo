package com.gdu.wacdo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private Status status;
    private T data;
    private Boolean notification;
    private String message;

    public static <T> ApiResponse<T> success(T data, Boolean notification, String message) {
        return new ApiResponse<>(Status.SUCCESS, data, notification, message);
    }

    public static <T> ApiResponse<T> failure(Boolean notification, String message) {
        return new ApiResponse<>(Status.FAILURE, null, notification, message);
    }

    public static <T> ApiResponse<T> error(Boolean notification, String message) {
        return new ApiResponse<>(Status.ERROR, null, notification, message);
    }
}