package com.matias.taskly.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Servicio responsable de generar y validar JWT.
 *
 * Genera un token firmado con la clave secreta del servidor.
 * Valida que el token sea auténtico y no haya expirado.
 * Lee el email (subject) del token para identificar al usuario.
 */
@Service
public class JwtService {

    // La clave secreta viene de application.properties — nunca hardcodeada en el código
    @Value("${jwt.secret}")
    private String secretKey;

    // Tiempo de vida del token: 24 horas en milisegundos
    private static final long EXPIRATION_MS = 1000L * 60 * 60 * 24;

    /**
     * Genera un JWT firmado con el email del usuario como subject.
     * El token incluye: subject (email), fecha de emisión, fecha de expiración y firma.
     */
    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Extrae el email (subject) del token.
     * Si el token es inválido o expiró, lanza una excepción.
     */
    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    /**
     * Verifica que el token sea válido: firma correcta y no expirado.
     */
    public boolean isTokenValid(String token) {
        try {
            extractClaims(token); //Si esto no lanza excepción, el metodo es valido
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Parsea el token y devuelve los claims (payload).
     * Lanza excepción si la firma es inválida o el token expiró.
     */
    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Convierte la clave secreta (String) en una SecretKey criptográfica para HMAC-SHA.
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }
}