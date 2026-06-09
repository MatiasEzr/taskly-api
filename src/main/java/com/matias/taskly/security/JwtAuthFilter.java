package com.matias.taskly.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro que se ejecuta UNA vez por request (OncePerRequestFilter).
 *
 * Su trabajo es:
 * 1. Leer el header Authorization de cada request
 * 2. Extraer el JWT ("Bearer <token>")
 * 3. Validar el token con JwtService
 * 4. Si es válido, cargar el usuario y autenticarlo en el SecurityContext
 *
 * Spring Security lee el SecurityContext después de este filtro
 * para saber si el usuario está autenticado.
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Leer el header Authorization
        String authHeader = request.getHeader("Authorization");

        // Si no hay header o no empieza con "Bearer ", dejamos pasar sin autenticar
        // Spring Security rechazará el request si el endpoint requiere autenticación
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extraer el token (quitar el prefijo "Bearer ")
        String jwt = authHeader.substring(7);

        // Validar el token antes de intentar extraer el email
        if (!jwtService.isTokenValid(jwt)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extraer el email del token
        String email = jwtService.extractEmail(jwt);

        // Si el email existe y el SecurityContext aún no tiene autenticación
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Cargar el usuario desde la BD usando el UserDetailsService
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            // Crear el objeto de autenticación de Spring Security
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,                          // credentials null — ya autenticamos con el JWT
                            userDetails.getAuthorities()
                    );

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Guardar la autenticación en el SecurityContext
            // A partir de acá Spring Security sabe que el usuario está autenticado
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        // Continuar con el resto de la filter chain
        filterChain.doFilter(request, response);
    }
}