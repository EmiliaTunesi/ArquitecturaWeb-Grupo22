package com.trabajointegrador.microserviciousuario.segurity;

import com.trabajointegrador.microserviciousuario.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SegurityConfig {

    private final CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Desactivar CSRF para peticiones POST (necesario en REST APIs)
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        // Permite acceso a Login y Registro (sin autenticación previa)
                        .requestMatchers(HttpMethod.POST, "/api/authenticate").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll()

                        // ACEPTAR TODAS LAS DEMÁS PETICIONES SIN REQUERIR AUTENTICACIÓN
                        // Esto es porque la autenticación (validación del Token y Rol)
                        // ya fue hecha por el API Gateway antes de que la petición llegara aquí.
                        .anyRequest().permitAll() // <--- ¡EL CAMBIO CLAVE!
                );

        return http.build();
    }


    // Bean para exponer el AuthenticationManager (necesario para el login)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}