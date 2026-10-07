package com.charan.erp1.DTO;

import jakarta.validation.constraints.NotBlank;

public record AuthTokenRequest(
        @NotBlank(message = "Username is required") String username,
        @NotBlank(message = "Password is required") String password) {
}