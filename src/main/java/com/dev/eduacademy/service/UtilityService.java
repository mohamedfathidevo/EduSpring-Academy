package com.dev.eduacademy.service;

import com.dev.eduacademy.entity.User;
import com.dev.eduacademy.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * The UtilityService class is a service layer in the Spring Boot application.
 * It provides utility functions that are used across the application.
 * Currently, it has a method to get the currently authenticated user.
 */
@Service
@RequiredArgsConstructor
public class UtilityService {

    private final UserRepository userRepository;

    /**
     * Retrieves the currently authenticated user.
     * If the user is not authenticated, it returns null.
     * If the user is authenticated but not found in the database, it throws an EntityNotFoundException.
     *
     * @return the authenticated user, or null if the user is not authenticated
     * @throws EntityNotFoundException if the authenticated user is not found in the database
     */
    public User getCurrentUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        var email = authentication.getName();
        return userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
}
