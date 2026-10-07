package com.charan.erp1.DTO;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(@NotBlank(message = "Refresh token is required") String refreshToken) {
}