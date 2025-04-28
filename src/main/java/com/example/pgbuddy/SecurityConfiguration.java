package com.example.pgbuddy;

import com.example.pgbuddy.filters.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

// Enables Spring Security's web security features and integrates with Spring MVC.
@EnableWebSecurity
// Marks this class as a source of bean definitions for the Spring application context.
@Configuration
// This class configures security settings for the application, including authentication and authorization rules.
public class SecurityConfiguration {
    // Register the JwtFilter in our Spring Security configuration
    private final JwtFilter jwtFilter;

    public SecurityConfiguration(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    // Configures security rules for HTTP requests.
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // disable csrf for all endpoints (for testing)
//        http
//                .cors(withDefaults())
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()); // Allow all requests without authentication
//        return http.build();

      // Configures security rules for HTTP requests.
      http.cors(withDefaults())
              // Disables CSRF (Cross-Site Request Forgery) protection for the application.
              // Requests to endpoints like /signup will not require a CSRF token
              .csrf(csrf -> csrf.disable())
              // Configures authorization rules for HTTP requests.
              .authorizeHttpRequests(auth -> auth
              .requestMatchers("/", "/index.html", "/static/**").permitAll() // Allow access to static resources
              .requestMatchers("/api/signin", "/api/signup").permitAll() // Public endpoints (can access without any authentication)
              .requestMatchers("/ws/**", "/chat/**").permitAll() // Allow WebSocket connections (without authentication)
              .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll() // Allow Swagger UI and API docs (without authentication)
              .requestMatchers("/actuator/**", "/manage/**").permitAll() // Allow Actuator endpoints (without authentication)
              //.requestMatchers("/api/notices").hasRole("RESIDENT") // Restrict access to users with the RESIDENT role
              .anyRequest().authenticated() // All other endpoints require authentication
              //.anyRequest().permitAll() // Allow all requests for now (uncomment this for debugging purposes)
          )
          .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter before the UsernamePasswordAuthenticationFilter
        return http.build(); // Builds the security filter chain
    }


    // Configures CORS (Cross-Origin Resource Sharing) settings for the application.
    @Bean
    public CorsFilter corsFilter() {
        // Creates a new CorsConfiguration object to define CORS settings.
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // Allow credentials (cookies, authorization headers, etc.) to be sent in CORS requests

        // Set allowed origins, methods, and headers for CORS requests.
        config.setAllowedOrigins(Arrays.asList("http://localhost:5173", "https://pg-buddy-front-end.vercel.app")); // Added frontend URL
        config.setAllowedMethods(Arrays.asList("*"));
        config.setAllowedHeaders(Arrays.asList("*"));

        // Uncomment the following lines to restrict CORS settings to specific origins, methods, and headers:-
        //config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization", "Upgrade", "Connection"));
        //config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Define CORS settings for all URL patterns (/**) or endpoints in the application.
        // (UrlBasedCorsConfigurationSource is a class in the Spring Framework used to configure CORS settings for specific URL patterns in a Spring application)
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); // Creates a new UrlBasedCorsConfigurationSource object -> to define CORS settings based on URL patterns.
        source.registerCorsConfiguration("/**", config); // Apply the CORS configuration to all endpoints.
        return new CorsFilter(source);
    }

    @Bean
    // Defines a password encoder to securely hash and verify user passwords using the BCrypt algorithm.
    // Passwords will be hashed using BCrypt before being stored in the database.
    public BCryptPasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }
    // passwords are never stored in plain text in the database (only hashed values are stored)
}
