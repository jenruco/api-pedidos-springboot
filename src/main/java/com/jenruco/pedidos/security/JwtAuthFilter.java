package com.jenruco.pedidos.security;

import com.jenruco.pedidos.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro que intercepta todas las peticiones HTTP y valida el JWT token.
 * Se ejecuta una única vez por petición (OncePerRequestFilter).
 */
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthFilter.class);

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            // Extrae el token del header "Authorization: Bearer <token>"
            String token = extractTokenFromRequest(request);

            if (token != null && jwtUtil.isTokenValid(token)) {
                // Token válido: extrae el email y establece autenticación
                String email = jwtUtil.extractEmail(token);
                log.info("Token válido para usuario: {}", email);

                // Crea un objeto de autenticación sin contraseña (ya validamos el token)
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(email, null, null);
                
                // Establece la autenticación en el contexto de Spring Security
                SecurityContextHolder.getContext().setAuthentication(auth);
            } else {
                log.warn("Token ausente o inválido");
            }
        } catch (Exception e) {
            log.error("Error procesando token JWT", e);
        }

        // Continúa con el siguiente filtro en la cadena
        filterChain.doFilter(request, response);
    }

    /**
     * Extrae el token del header "Authorization: Bearer <token>"
     */
    private String extractTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);  // Quita "Bearer " (7 caracteres)
        }

        return null;
    }
}
