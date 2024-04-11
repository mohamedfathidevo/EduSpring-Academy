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

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        Role requestRole = switch (request.getRole()) {
            case "admin" -> Role.ADMIN;
            case "instructor" -> Role.INSTRUCTOR;
            case "student" -> Role.STUDENT;
            default -> throw new IllegalStateException("Unexpected value: " + request.getRole());
        };
        User user = User.builder()
                .username(request.getUserName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(requestRole)
                .build();
        User savedUser = userRepository.save(user);
        String token = jwtService.generateToken(savedUser);
        return AuthenticationResponse.builder()
                .jwt(token)
                .build();
    }

    public AuthenticationResponse login(LoginRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        String jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .jwt(jwt)
                .build();
    }
}
