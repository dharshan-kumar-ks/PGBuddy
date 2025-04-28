package com.example.pgbuddy.filters;
import com.example.pgbuddy.utils.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collections;

// This filter will intercept requests and validate the JWT token.
@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;

    // This method is called for every request to validate the JWT token
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Extract the Authorization header from the request
        String authorizationHeader = request.getHeader("Authorization");

        // Check if the Authorization header is present and starts with "Bearer "
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);

            // This method will check if the token is valid and not expired & then extract the user details (email and role)
            // set authentication status in the SecurityContext - check & mark the user as authenticated
            if (jwtUtil.validateToken(token)) {
                // Extract the email and role from the token
                String email = jwtUtil.extractEmail(token);
                String role = jwtUtil.extractRole(token);
                // Log the extracted role for debugging
                System.out.println("Extracted Role: " + role);

                // Set user details in the security context - Create a SimpleGrantedAuthority object with the user's role
                // Spring Security expects roles to be prefixed with "ROLE_"
                // Example: If your role is "RESIDENT", Spring expects "ROLE_RESIDENT"
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
                // This tells Spring Security the user is authenticated with this role
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(email, null, Collections.singletonList(authority));
                // Set the authentication token in the SecurityContext to mark the user as authenticated
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continue the filter chain - this will allow the request to proceed to the next filter or controller
        filterChain.doFilter(request, response);
    }
}