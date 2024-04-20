package com.dev.eduacademy;

import com.dev.eduacademy.model.RegisterRequest;
import com.dev.eduacademy.service.AuthenticationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EduSpringAcademyApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduSpringAcademyApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(AuthenticationService authenticationService) {
        return args -> {
            var admin = RegisterRequest.builder()
                    .userName("mohamed_admin")
                    .email("mohamed.admin@gmail.com")
                    .password("12345")
                    .role("admin")
                    .build();
            System.out.println("Admin token: " + authenticationService.register(admin).getJwt());

            var student = RegisterRequest.builder()
                    .userName("mohamed_student")
                    .email("mohamed.student@gmail.com")
                    .password("12345")
                    .role("student")
                    .build();
            System.out.println("Student token: " + authenticationService.register(student).getJwt());

            var instructor = RegisterRequest.builder()
                    .userName("mohamed_instructor")
                    .email("mohamed.instructor@gmail.com")
                    .password("12345")
                    .role("instructor")
                    .build();
            System.out.println("instructor token: " + authenticationService.register(instructor).getJwt());
        };
    }

}
