package com.badminton.backend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${badminton.jwt.secret}")
    private String jwtSecret;

    @Value("${badminton.jwt.expirationMs}")
    private int jwtExpirationMs;

    @Value("${badminton.jwt.refreshExpirationMs}")
    private int jwtRefreshExpirationMs;

    @Value("${badminton.jwt.cookieName}")
    private String jwtCookie;

    public String generateJwtToken(String username) {
        return generateTokenFromUsername(username);
    }

    public String generateRefreshToken(String username) {
        return Jwts.builder()
                .subject(username)
                .claim("type", "refresh")
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtRefreshExpirationMs))
                .signWith(getSignKey())
                .compact();
    }

    public ResponseCookie createCookieRefreshToken(String refreshToken) {
        return ResponseCookie.from(jwtCookie, refreshToken)
                .httpOnly(true)
                .path("/api/auth/refresh-token")
                .maxAge(jwtRefreshExpirationMs / 1000)
                .build();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    private SecretKey getSignKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String getJwtFromHeader(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return null;
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Claims claims = Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(authToken).getPayload();
            String type = claims.get("type", String.class);

            if (!"access".equals(type)) {
                throw new JwtException("Invalid Token");
            }
            return true;
        } catch (JwtException e) {
            logger.error("JWT token validation error: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public boolean validateRefreshToken(String authToken) {
        try {
            Claims claims = Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(authToken).getPayload();
            String type = claims.get("type", String.class);

            if (!"refresh".equals(type)) {
                throw new JwtException("Invalid Token");
            }
            return true;
        } catch (JwtException e) {
            logger.error("JWT token validation error: {}", e.getMessage());
        }
        return false;
    }

    public String generateTokenFromUsername(String username) {
        return Jwts.builder()
                .subject(username)
                .claim("type", "access")
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(getSignKey())
                .compact();
    }
}
