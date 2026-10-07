package com.charan.erp1.SERVICES;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;

import com.charan.erp1.DTO.AuthTokenResponse;

@Service
public class AuthTokenService {
    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder refreshTokenDecoder;
    private final String issuer;
    private final long accessTokenTtlSeconds;
    private final long refreshTokenTtlSeconds;
    private final ConcurrentHashMap<String, Instant> activeRefreshTokens = new ConcurrentHashMap<>();

    public AuthTokenService(
            AuthenticationManager authenticationManager,
            JwtEncoder jwtEncoder,
            @Qualifier("refreshTokenDecoder") JwtDecoder refreshTokenDecoder,
            @Value("${app.security.jwt-issuer}") String issuer,
            @Value("${app.security.access-token-ttl-seconds}") long accessTokenTtlSeconds,
            @Value("${app.security.refresh-token-ttl-seconds}") long refreshTokenTtlSeconds) {
        this.authenticationManager = authenticationManager;
        this.jwtEncoder = jwtEncoder;
        this.refreshTokenDecoder = refreshTokenDecoder;
        this.issuer = issuer;
        this.accessTokenTtlSeconds = accessTokenTtlSeconds;
        this.refreshTokenTtlSeconds = refreshTokenTtlSeconds;
    }

    public AuthTokenResponse login(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
        return issueTokenPair(authentication.getName());
    }

    public AuthTokenResponse refresh(String refreshToken) {
        Jwt claims = decodeRefreshToken(refreshToken);
        String tokenId = claims.getId();
        Instant expiresAt = claims.getExpiresAt();
        Instant registeredExpiry = tokenId == null ? null : activeRefreshTokens.remove(tokenId);
        if (!"refresh".equals(claims.getClaimAsString("token_type"))
                || registeredExpiry == null
                || expiresAt == null
                || registeredExpiry.getEpochSecond() != expiresAt.getEpochSecond()
                || !expiresAt.isAfter(Instant.now())) {
            throw new org.springframework.security.authentication.BadCredentialsException("Refresh token is invalid or already used");
        }
        return issueTokenPair(claims.getSubject());
    }

    public void logout(String refreshToken) {
        try {
            Jwt claims = decodeRefreshToken(refreshToken);
            if ("refresh".equals(claims.getClaimAsString("token_type")) && claims.getId() != null) {
                activeRefreshTokens.remove(claims.getId());
            }
        } catch (AuthenticationException ignored) {
        }
    }

    private Jwt decodeRefreshToken(String refreshToken) {
        try {
            return refreshTokenDecoder.decode(refreshToken);
        } catch (JwtException ex) {
            throw new org.springframework.security.authentication.BadCredentialsException("Refresh token is invalid or expired", ex);
        }
    }

    private AuthTokenResponse issueTokenPair(String username) {
        Instant now = Instant.now();
        activeRefreshTokens.entrySet().removeIf(entry -> !entry.getValue().isAfter(now));

        String accessToken = encodeToken(username, "access", null, now, accessTokenTtlSeconds);
        String refreshId = UUID.randomUUID().toString();
        Instant refreshExpiry = now.plusSeconds(refreshTokenTtlSeconds);
        String refreshToken = encodeToken(username, "refresh", refreshId, now, refreshTokenTtlSeconds);
        activeRefreshTokens.put(refreshId, refreshExpiry);
        return new AuthTokenResponse(accessToken, refreshToken, "Bearer", accessTokenTtlSeconds);
    }

    private String encodeToken(String username, String tokenType, String tokenId, Instant issuedAt, long ttlSeconds) {
        var claimsBuilder = org.springframework.security.oauth2.jwt.JwtClaimsSet.builder()
                .issuer(issuer)
                .subject(username)
                .issuedAt(issuedAt)
                .expiresAt(issuedAt.plusSeconds(ttlSeconds))
                .claim("token_type", tokenType);
        if (tokenId != null) {
            claimsBuilder.id(tokenId);
        }
        return jwtEncoder.encode(JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS256).build(), claimsBuilder.build())).getTokenValue();
    }
}