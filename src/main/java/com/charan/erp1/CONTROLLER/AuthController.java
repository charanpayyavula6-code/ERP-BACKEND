package com.charan.erp1.CONTROLLER;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.charan.erp1.DTO.AuthTokenRequest;
import com.charan.erp1.DTO.AuthTokenResponse;
import com.charan.erp1.DTO.RefreshTokenRequest;
import com.charan.erp1.SERVICES.AuthTokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    private final AuthTokenService authTokenService;

    public AuthController(AuthTokenService authTokenService) {
        this.authTokenService = authTokenService;
    }

    @PostMapping(value = "/token", consumes = "application/json", produces = "application/json")
    public ResponseEntity<AuthTokenResponse> createToken(@Valid @RequestBody AuthTokenRequest request) {
        return ResponseEntity.ok(authTokenService.login(request.username(), request.password()));
    }

    @PostMapping(value = "/refresh", consumes = "application/json", produces = "application/json")
    public ResponseEntity<AuthTokenResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(authTokenService.refresh(request.refreshToken()));
    }

    @PostMapping(value = "/logout", consumes = "application/json")
    public ResponseEntity<Void> logout(@Valid @RequestBody RefreshTokenRequest request) {
        authTokenService.logout(request.refreshToken());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}