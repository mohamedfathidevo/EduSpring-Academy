package com.dev.eduacademy.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.dev.eduacademy.util.Role.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/**
 * This class is responsible for the security configuration of the application.
 * It extends the WebSecurityConfigurerAdapter which provides a convenient base class for creating a WebSecurityConfigurer instance.
 * The configuration defines URL patterns that should be secured.
 *
 * @author mohamedfathidev
 * @version 1.0
 * @since 2023.3.5
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private static final String[] WHITE_LIST_END_POINTS = {
            "api/v1/auth/**"
    };

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    /**
     * This method configures the security filter chain.
     * It defines the security policy for HTTP requests.
     * It disables CSRF, sets session management to stateless, and adds the JWT authentication filter.
     *
     * @param http The HttpSecurity to modify.
     * @return The SecurityFilterChain.
     * @throws Exception if an error occurs when configuring the security policy.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests( req ->
                        req.requestMatchers(WHITE_LIST_END_POINTS)
                                .permitAll()
                                .requestMatchers("api/v1/student/**").hasAnyRole(STUDENT.name())
                                .requestMatchers("api/v1/admin/**").hasAnyRole(ADMIN.name())
                                .requestMatchers("api/v1/instructor/**").hasAnyRole(INSTRUCTOR.name())
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
