package com.matias.taskly.security.config;

import com.matias.taskly.repository.UserRepository;
import com.matias.taskly.security.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt hashea el password con salt y múltiples rondas
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepo) {
        // Busca el usuario por email al autenticar — email es el identificador único
        return email -> userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Usuario no encontrado: " + email));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {

        return http
                // Habilita la configuración CORS definida en CorsConfig
                .cors(cors -> cors.configure(http))

                // Deshabilita CSRF — no necesario en APIs REST stateless con JWT
                .csrf(AbstractHttpConfigurer::disable)

                // STATELESS: Spring no crea ni usa sesiones HTTP
                // Cada request se autentica únicamente con el JWT del header
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth -> auth
                        // Endpoints públicos — no requieren JWT
                        .requestMatchers(
                                "/auth/login",
                                "/auth/register",
                                "/oauth2/**",
                                "/login/oauth2/**"
                        ).permitAll()
                        // Todo lo demás requiere JWT válido
                        .anyRequest().authenticated()
                )

                // OAuth2 login con Google — Spring maneja el flujo completo
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                )

                // Agrega el filtro JWT ANTES del filtro de autenticación por usuario/contraseña
                // Así cada request se verifica con el JWT antes de cualquier otra lógica
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

                .build();
    }
}