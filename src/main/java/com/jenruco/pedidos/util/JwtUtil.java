package com.jenruco.pedidos.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration}")
    private long jwtExpirationMs;

    /**
     * Genera un token JWT para un usuario.
     * 
     * @param email {@link String} email del usuario para el cual se genera el token
     * @return {@link String} con el token JWT generado
     * 
     * @author Henry Pérez 
     * @version 1.0
     * @since 05-02-2026
     * 
     */
    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, email);
    }

    /**
     * Crea el token JWT.
     * 
     * @param clailms {@link Map} con los claims (datos) a incluir en el token
     * @param subject {@link String} subject del token (en este caso, el email del usuario)
     * 
     * @return {@link String} con el token JWT generado
     * 
     * @author Henry Pérez 
     * @version 1.0
     * @since 05-02-2026
     * 
     */
    private String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extrae el email (subject) del token JWT.
     * 
     * @param token {@link String} token JWT del cual se extrae el email
     * @return {@link String} email extraído del token JWT
     * 
     * @author Henry Pérez 
     * @version 1.0
     * @since 05-02-2026
     * 
     */
    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    /**
     * Valida si el token JWT es válido (no expirado y con firma correcta).
     * 
     * @param token {@link String} token JWT del cual se extrae el email
     * @return {@link boolean} true si el token es válido, false en caso contrario
     * 
     * @author Henry Pérez 
     * @version 1.0
     * @since 05-02-2026
     * 
     */
    public boolean isTokenValid(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (Exception e) {
            log.warn("Error al validar token: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Extrae todos los claims/datos del token JWT.
     * 
     * @param token {@link String} token JWT del cual se extraen los claims
     * @return {@link Claims} con los claims extraídos del token JWT
     * 
     * @author Henry Pérez 
     * @version 1.0
     * @since 05-02-2026
     * 
     */
    private Claims extractAllClaims(String token) {
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
