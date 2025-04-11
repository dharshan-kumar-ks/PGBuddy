package com.example.pgbuddy;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

// This class will handle the generation and validation of JWT tokens.
@Component
public class JwtUtil {
    // openssl rand -base64 32 => This will generate a random 256-bit key encoded in Base64.
    // algorithm: HS256
    private final String SECRET_KEY = "8hoi11f02TeZm+SeeUkYxE5AtScCmOmzbY9XHkFDYYU="; // Replace with a secure key
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 hours

    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role) // Add the role to the payload
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String extractRole(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class); // Extract the role from the token
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
