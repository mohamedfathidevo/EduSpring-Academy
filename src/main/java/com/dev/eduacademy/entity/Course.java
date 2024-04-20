package com.dev.eduacademy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * Course is an entity class that represents a course in the system.
 * It contains the course's name, description, and whether it is published.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Boolean isPublished;

    @JsonIgnore
    @ManyToMany(mappedBy = "instructedCourses")
    private Set<User> instructors = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "enrolledCourses")
    private Set<User> students = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "course")
    private Set<EnrollmentRequest> enrollmentRequests = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "course")
    private Set<Lesson> lessons = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "course")
    private Set<Assignment> assignments = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "course")
    private Set<Exam> exams = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "course")
    private Set<Grade> grades = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course course)) return false;

        return getId() != null ? getId().equals(course.getId()) : course.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}