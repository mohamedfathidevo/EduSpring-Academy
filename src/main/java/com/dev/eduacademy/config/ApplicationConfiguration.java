package com.dev.eduacademy.config;

import com.dev.eduacademy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * The ApplicationConfiguration class provides the configuration for the application.
 * It defines beans for UserDetailsService, AuthenticationProvider, AuthenticationManager, AuditorAware, and PasswordEncoder.
 *
 * @author mohamedfathidev
 * @version 1.0
 * @since 2023.3.5
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {

    private final UserRepository userRepository;

    /**
     * This method provides a UserDetailsService bean.
     * It uses the UserRepository to fetch user details by email.
     *
     * @return UserDetailsService bean.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow();
    }

    /**
     * This method provides an AuthenticationProvider bean.
     * It creates a DaoAuthenticationProvider and sets the UserDetailsService and PasswordEncoder.
     * The UserDetailsService is used to retrieve user-related data.
     * The PasswordEncoder is used to encode and validate passwords.
     *
     * @return AuthenticationProvider bean.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    /**
     * This method provides an AuthenticationManager bean.
     * It uses the AuthenticationConfiguration to get the AuthenticationManager.
     * The AuthenticationManager is used to authenticate a request within the Spring Security framework.
     *
     * @param authenticationConfiguration The AuthenticationConfiguration object used to get the AuthenticationManager.
     * @return AuthenticationManager bean.
     * @throws Exception if there is an error getting the AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * This method provides a PasswordEncoder bean.
     * It creates a BCryptPasswordEncoder, which is a password encoder that uses the BCrypt strong hashing function.
     *
     * @return PasswordEncoder bean.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
