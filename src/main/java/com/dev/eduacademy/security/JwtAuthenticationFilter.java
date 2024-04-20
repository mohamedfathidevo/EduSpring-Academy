package com.dev.eduacademy.security;

import com.dev.eduacademy.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


/**
 * This class is a filter that handles JWT authentication.
 * It extends OncePerRequestFilter to ensure it is executed once per request.
 * It checks the Authorization header of the incoming request for a JWT token.
 * If a token is found, it validates the token, extracts the username from it,
 * and sets the authentication in the SecurityContext.
 *
 * @author mohamedfathidev
 * @version 1.0
 * @since 2023.3.5
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    /**
     * This method is overridden from OncePerRequestFilter class and is used to perform actual filter processing.
     * It checks the Authorization header of the incoming request for a JWT token.
     * If a token is found, it validates the token, extracts the username from it,
     * and sets the authentication in the SecurityContext.
     *
     * @param request     The HttpServletRequest object that contains the request the client made of the servlet.
     * @param response    The HttpServletResponse object that contains the response the servlet sends to the client.
     * @param filterChain The FilterChain object provided by the servlet container to the developer giving a view into the invocation chain of a filtered request for a resource.
     * @throws ServletException if the request for the GET could not be handled.
     * @throws IOException      if an input or output error is detected when the servlet handles the GET request.
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException{
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
