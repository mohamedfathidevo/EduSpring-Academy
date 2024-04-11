package com.dev.eduacademy.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/instructor")
@PreAuthorize("hasRole('INSTRUCTOR')")
public class InstructorController {
    @GetMapping
    @PreAuthorize("hasAuthority('instructor:get_all_my_courses')")
    public String get() {
        return "all courses that published  will appear here";
    }
}
