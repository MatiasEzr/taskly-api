package com.matias.taskly.security.config;

import com.matias.taskly.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt hashea el password con salt y múltiples rondas
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepo) {
        // Lambda de UserDetailsService — busca el usuario por email al autenticar
        return email -> userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Usuario no encontrado: " + email));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        // Rutas públicas — no requieren login
                        .requestMatchers("/register", "/login").permitAll()
                        // Todo lo demás requiere autenticación
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")          // vista de login personalizada
                        .defaultSuccessUrl("/home")   // redirige acá tras login exitoso
                        .permitAll()
                )
                .oauth2Login(oauth2->
                        oauth2.loginPage("/login"))

                .logout(logout -> logout
                        .logoutSuccessUrl("/login")   // redirige acá tras logout
                )
                .build();
    }
}