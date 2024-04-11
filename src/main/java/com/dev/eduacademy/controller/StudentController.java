package com.dev.eduacademy.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/student")
@PreAuthorize("hasRole('STUDENT')")
public class StudentController {
    @GetMapping
    @PreAuthorize("hasAuthority('student:get_all_enrollment_courses')")
    public String get() {
        return "all courses enrollment will appear here";
    }
}
