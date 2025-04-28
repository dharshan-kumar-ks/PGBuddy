package com.example.pgbuddy.utils;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

// This class will handle the generation and validation of JWT tokens.
@Component
public class JwtUtil {
    // openssl rand -base64 32 => This will generate a random 256-bit key encoded in Base64.
    // algorithm: HS256
    private final String SECRET_KEY = "8hoi11f02TeZm+SeeUkYxE5AtScCmOmzbY9XHkFDYYU="; // Replace with a secure key for the hashing algorithm
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 hours

    // This method generates a JWT token using the provided userId, email, and role.
    public String generateToken(Long userId, String email, String role) {
        return Jwts.builder()
                .setSubject(email) // Set the email as the subject of the token; we can use getSubject() method to extract it from token later if needed
                .claim("userId", userId) // Add userId to the token payload
                .claim("role", role) // Add the role to the payload
                .setIssuedAt(new Date()) // Set the current date as the issued date
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Set the expiration date (to 10 hours from now)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // Sign the token with the secret key using HS256 algorithm
                .compact(); // Compact the token to a string
    }
    // Example JWT token (output): eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
    /*
    Header:
    {
      "alg": "HS256",
      "typ": "JWT",
    }
    payload:-
    {
      "sub": "user@example.com",
      "userId": 12345,
      "role": "USER",
      "iat": 1696500000,
      "exp": 1696536000
    }
    link to parse any given JWT token string:- https://jwt.is/
    */

    // This method extracts the email from the JWT token
    public String extractEmail(String token) {
        return Jwts.parser() // Parse the token to extract the claims (including the subject)
                .setSigningKey(SECRET_KEY) // Set the signing key to validate the token
                .parseClaimsJws(token) // Parse the token and validate its signature
                .getBody() // Get the claims body from the parsed token
                .getSubject(); // Extract the subject (email) from the token payload
    }

    // This method extracts the userId from the JWT token
    public Long extractUserId(String token) {
        return (Long) Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .get("userId", Long.class); // Extract the userId (& convert as Long) from the token payload
    }

    // This method extracts the user role from the JWT token
    public String extractRole(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class); // Extract the user role (& convert as String) from the token
    }

    // This method checks if the token is expired or not
    public boolean validateToken(String token) {
        try {
            // Check if the token is expired by parsing it and checking the expiration date
            Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token); // Internally validates the token signature and expiration date
            return true;
        } catch (JwtException | IllegalArgumentException e) { // Catch any JWT-related exceptions
            return false;
        }
    }
}

