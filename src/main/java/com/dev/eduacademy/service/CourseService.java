package com.dev.eduacademy.service;

import com.dev.eduacademy.entity.Course;
import com.dev.eduacademy.entity.User;
import com.dev.eduacademy.model.CourseRequest;
import com.dev.eduacademy.repository.CourseRepository;
import com.dev.eduacademy.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * The CourseService class is a service layer in the Spring Boot application.
 * It is responsible for handling business logic related to courses.
 * It interacts with the CourseRepository, UserRepository, and UtilityService for data access and utility functions.
 */
@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final UtilityService utilityService;

    /**
     * Creates a new course.
     * The current authenticated user is set as the instructor for the course.
     *
     * @param courseRequest the request object containing course details
     */
    public void createCourse(CourseRequest courseRequest) {
        User user = utilityService.getCurrentUserAuthenticated();

        Course course = Course.builder()
                .name(courseRequest.getName())
                .description(courseRequest.getDescription())
                .isPublished(false)
                .students(new HashSet<>())
                .instructors(new HashSet<>())
                .enrollmentRequests(new HashSet<>())
                .lessons(new HashSet<>())
                .assignments(new HashSet<>())
                .exams(new HashSet<>())
                .grades(new HashSet<>())
                .build();

        course.getInstructors().add(user);
        user.getInstructedCourses().add(course);
        userRepository.save(user);
        courseRepository.save(course);
    }

    /**
     * Updates an existing course.
     * Checks if the current authenticated user is an instructor for the course.
     * If the user is an instructor, it updates the course details and saves it.
     *
     * @param courseId      the id of the course
     * @param courseRequest the request object containing course details
     */
    public void updateCourse(Long courseId, CourseRequest courseRequest) {
        User user = utilityService.getCurrentUserAuthenticated();
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Course not found"));
        if (!course.getInstructors().contains(user)) {
            throw new EntityNotFoundException("You are not instructor for this course");
        }
        course.setName(courseRequest.getName());
        course.setDescription(courseRequest.getDescription());
        courseRepository.save(course);
    }

    /**
     * Deletes a course.
     * Checks if the current authenticated user is an instructor for the course.
     * If the user is an instructor, it removes the course from the students and instructors, and deletes the course.
     *
     * @param courseId the id of the course
     */
    public void deleteCourse(Long courseId) {
        User user = utilityService.getCurrentUserAuthenticated();
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Course not found"));
        if (!course.getInstructors().contains(user)) {
            throw new EntityNotFoundException("You are not instructor for this course");
        }
        course.getStudents().forEach(student -> student.getEnrolledCourses().remove(course));
        course.getInstructors().forEach(instructor -> instructor.getInstructedCourses().remove(course));
        courseRepository.save(course);
        courseRepository.delete(course);
    }

    /**
     * Publishes a course.
     * Checks if the current authenticated user is an instructor for the course.
     * If the user is an instructor, it publishes the course.
     */
    public Set<Course> getAllInstructorCourses() {
        User user = utilityService.getCurrentUserAuthenticated();
        return user.getInstructedCourses();
    }

    /**
     * Retrieves all courses for the current authenticated user.
     * If the user is an instructor, it returns the courses they are instructing.
     * If the user is a student, it returns the courses they are enrolled in.
     *
     * @return a set of courses
     */
    public Course getSpecialCourseFromInstructorCourses(Long courseId) {
        User user = utilityService.getCurrentUserAuthenticated();
        return user.getInstructedCourses().stream()
                .filter(course -> course.getId().equals(courseId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));
    }

    /**
     * Retrieves a specific course by its id.
     * Checks if the current authenticated user is an instructor for the course.
     * If the user is an instructor, it returns the course.
     *
     * @param courseId the id of the course
     * @return the course
     * @throws EntityNotFoundException if the course is not found or the user is not an instructor for the course
     */
    public Set<User> getStudentsEnrolledInCourse(Long courseId) {
        User user = utilityService.getCurrentUserAuthenticated();
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Course not found"));
        if (!course.getInstructors().contains(user)) {
            throw new EntityNotFoundException("You are not instructor for this course");
        }
        return course.getStudents();
    }

    /**
     * Retrieves a specific student enrolled in a course.
     * Checks if the current authenticated user is an instructor for the course.
     * If the user is an instructor, it returns the student.
     *
     * @param courseId  the id of the course
     * @param studentId the id of the student
     * @return the student
     * @throws EntityNotFoundException if the course is not found, the user is not an instructor for the course, or the student is not found
     */
    public User getStudentEnrolledInCourse(Long courseId, Long studentId) {
        User user = utilityService.getCurrentUserAuthenticated();
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Course not found"));
        if (!course.getInstructors().contains(user)) {
            throw new EntityNotFoundException("You are not instructor for this course");
        }
        return course.getStudents().stream()
                .filter(student -> student.getId().equals(studentId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Student not found in this course"));
    }

    /**
     * Enrolls a student in a course.
     * Checks if the current authenticated user is a student.
     * If the user is a student, it enrolls the student in the course.
     */
    public Set<Course> getStudentCourses() {
        User user = utilityService.getCurrentUserAuthenticated();
        return user.getEnrolledCourses();
    }

    /**
     * Retrieves all courses for the current authenticated user.
     * If the user is an instructor, it returns the courses they are instructing.
     * If the user is a student, it returns the courses they are enrolled in.
     *
     * @return a set of courses
     */
    public Course getStudentCourse(Long courseId) {
        User user = utilityService.getCurrentUserAuthenticated();
        return user.getEnrolledCourses().stream()
                .filter(course -> course.getId().equals(courseId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));
    }

    /**
     * Enrolls a student in a course.
     * Checks if the current authenticated user is a student.
     * If the user is a student, it enrolls the student in the course.
     *
     * @param courseId the id of the course
     */
    public void unEnrollStudentFromCourse(Long courseId) {
        User user = utilityService.getCurrentUserAuthenticated();
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Course not found"));
        if (!user.getEnrolledCourses().contains(course)) {
            throw new EntityNotFoundException("You are not enrolled in this course");
        }
        course.getStudents().remove(user);
        user.getEnrolledCourses().remove(course);
        userRepository.save(user);
        courseRepository.save(course);
    }

    /**
     * Publishes a course.
     * Checks if the current authenticated user is an instructor for the course.
     * If the user is an instructor, it publishes the course.
     */
    public Set<Course> getAllPublishedCourses() {
        return courseRepository.findByIsPublishedTrue();
    }

    /**
     * Retrieves all published courses.
     *
     * @return a set of published courses
     */
    public Course getPublishedCourseById(Long courseId) {
        return courseRepository.findCourseByIdAndIsPublishedTrue(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));
    }

}
