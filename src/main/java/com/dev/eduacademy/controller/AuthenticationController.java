package com.dev.eduacademy.controller;

import com.dev.eduacademy.model.AuthenticationResponse;
import com.dev.eduacademy.model.LoginRequest;
import com.dev.eduacademy.model.RegisterRequest;
import com.dev.eduacademy.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * The AuthenticationController class handles the authentication-related HTTP requests.
 * It provides endpoints for user registration and login.
 *
 * @author mohamedfathidev
 * @version 1.0
 * @since 2023.3.5
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    /**
     * This method handles the HTTP POST request for user registration.
     * It takes a RegisterRequest object as input and returns a ResponseEntity with an AuthenticationResponse.
     *
     * @param request The RegisterRequest object containing the user's registration details.
     * @return ResponseEntity with the AuthenticationResponse containing the registration status and JWT token.
     */
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authenticationService.register(request));
    }

    /**
     * This method handles the HTTP POST request for user login.
     * It takes a LoginRequest object as input and returns a ResponseEntity with an AuthenticationResponse.
     *
     * @param request The LoginRequest object containing the user's login details.
     * @return ResponseEntity with the AuthenticationResponse containing the login status and JWT token.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(authenticationService.login(request));
    }
}
