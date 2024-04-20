package com.dev.eduacademy.service;

import com.dev.eduacademy.entity.User;
import com.dev.eduacademy.model.AuthenticationResponse;
import com.dev.eduacademy.model.LoginRequest;
import com.dev.eduacademy.model.RegisterRequest;
import com.dev.eduacademy.repository.UserRepository;
import com.dev.eduacademy.util.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * The AuthenticationService class is a service layer in the Spring Boot application.
 * It is responsible for handling business logic related to user authentication.
 * It interacts with the UserRepository, PasswordEncoder, JwtService, and AuthenticationManager for data access and utility functions.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Registers a new user.
     * It encodes the password, saves the user to the database, and generates a JWT token for the user.
     *
     * @param request the request object containing user details
     * @return the authentication response containing the JWT token
     */
    public AuthenticationResponse register(RegisterRequest request) {
        Role requestRole = switch (request.getRole()) {
            case "admin" -> Role.ADMIN;
            case "instructor" -> Role.INSTRUCTOR;
            case "student" -> Role.STUDENT;
            default -> throw new RuntimeException("Unexpected value: " + request.getRole());
        };
        User user = User.builder()
                .username(request.getUserName())
                .email(request.getEmail())
                .role(requestRole)
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        User savedUser = userRepository.save(user);
        String token = jwtService.generateToken(savedUser);
        return AuthenticationResponse.builder()
                .jwt(token)
                .build();
    }

    /**
     * Logs in a user.
     * It authenticates the user, generates a JWT token, and returns the authentication response.
     *
     * @param request the request object containing user credentials
     * @return the authentication response containing the JWT token
     */
    public AuthenticationResponse login(LoginRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        String jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .jwt(jwt)
                .build();
    }
}
