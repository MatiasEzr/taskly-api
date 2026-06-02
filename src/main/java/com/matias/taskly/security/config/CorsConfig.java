package com.matias.taskly.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Marks this class as a Spring configuration class
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    // Overrides the method used to configure CORS rules
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        // Applies the CORS configuration to all endpoints/routes
        registry.addMapping("/**")

                // Allows requests only from this origin
                // In this case, from a frontend running with Vite
                .allowedOrigins("http://localhost:5173")

                // Specifies the allowed HTTP methods
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")

                // Allows all request headers
                .allowedHeaders("*");
    }
}