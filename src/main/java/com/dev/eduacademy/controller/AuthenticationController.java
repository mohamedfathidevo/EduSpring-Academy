package com.dev.eduacademy.controller;

import com.dev.eduacademy.model.AuthenticationResponse;
import com.dev.eduacademy.model.LoginRequest;
import com.dev.eduacademy.model.RegisterRequest;
import com.dev.eduacademy.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @GetMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(authenticationService.login(request));
    }
}
