package com.dev.eduacademy.service;


import com.dev.eduacademy.entity.Course;
import com.dev.eduacademy.entity.User;
import com.dev.eduacademy.repository.CourseRepository;
import com.dev.eduacademy.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * The UserService class is a service layer in the Spring Boot application.
 * It is responsible for handling business logic related to users.
 * It interacts with the UserRepository and CourseRepository for data access, the PasswordEncoder for password encoding, and the UtilityService for utility functions.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CourseRepository courseRepository;
    private final UtilityService utilityService;

    /**
     * Retrieves all students for a specific course.
     * Checks if the current authenticated user is an instructor for the course.
     * If the user is an instructor, it returns the students of the course.
     *
     * @param courseId the id of the course
     * @return a set of students
     * @throws EntityNotFoundException if the course is not found or the user is not an instructor for the course
     */
    public Set<User> getAllStudentForCourse(Long courseId) {
        User user = utilityService.getCurrentUserAuthenticated();
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Course not found"));
        if (!course.getInstructors().contains(user)) {
            throw new EntityNotFoundException("You are not instructor for this course");
        }
        return course.getStudents();
    }

    /**
     * Retrieves a specific student from the students of a course.
     * Checks if the current authenticated user is an instructor for the course.
     * If the user is an instructor, it returns the student.
     *
     * @param courseId  the id of the course
     * @param studentId the id of the student
     * @return the student
     * @throws EntityNotFoundException if the course is not found, the user is not an instructor for the course, or the student is not found
     */
    public User getSpecialStudentFromCourseStudents(Long courseId, Long studentId) {
        User user = utilityService.getCurrentUserAuthenticated();
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Course not found"));
        if (!course.getInstructors().contains(user)) {
            throw new EntityNotFoundException("You are not instructor for this course");
        }
        return course.getStudents().stream()
                .filter(student -> student.getId().equals(studentId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
    }

}
