package com.example.pgbuddy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

// Enables Spring Security's web security features and integrates with Spring MVC.
@EnableWebSecurity
// Marks this class as a source of bean definitions for the Spring application context.
@Configuration
public class SecurityConfiguration {
    @Bean
    // Configures security rules for HTTP requests.
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Disables CSRF (Cross-Site Request Forgery) protection for requests matching the path signup (e.g., /signup).
        // Requests to /signup will not require a CSRF token (useful for endpoints like user registration forms or APIs).
        //http.csrf(csrf -> csrf.ignoringRequestMatchers("signup"));

        // disable csrf for all endpoints (for testing)
        http
                .cors(withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()); // Allow all requests without authentication
        return http.build();
    }


    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
