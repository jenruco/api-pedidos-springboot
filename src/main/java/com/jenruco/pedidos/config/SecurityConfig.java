package com.jenruco.pedidos.config;

import com.jenruco.pedidos.security.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    /**
     * Configura la seguridad HTTP para la aplicación, definiendo las rutas públicas y protegidas, y añadiendo el filtro JWT.
     * 
     * @param http {@link HttpSecurity} objeto para configurar la seguridad HTTP
     * @return {@link SecurityFilterChain} con la configuración de seguridad HTTP
     * 
     * @author Henry Pérez 
     * @version 1.0
     * @since 05-02-2026
     * 
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authz -> authz
                // Rutas públicas (sin autenticación)
                .requestMatchers("/api/clientes/login").permitAll()
                // Rutas protegidas (requieren JWT válido)
                .requestMatchers("/api/**").authenticated()
                // Resto de rutas
                .anyRequest().authenticated()
            )
            // Añade el filtro JWT ANTES del filtro de autenticación estándar
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }

    /**
     * Codificador de contraseñas utilizando BCrypt.
     * 
     * @return {@link PasswordEncoder} con el codificador BCrypt configurado
     * 
     * @author Henry Pérez 
     * @version 1.0
     * @since 05-02-2026
     * 
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}