package com.charan.erp1.DTO;

public record AuthTokenResponse(String accessToken, String refreshToken, String tokenType, long expiresIn) {
}