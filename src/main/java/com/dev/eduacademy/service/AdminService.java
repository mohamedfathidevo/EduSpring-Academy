package com.dev.eduacademy.service;

import com.dev.eduacademy.entity.*;
import com.dev.eduacademy.repository.CourseRepository;
import com.dev.eduacademy.repository.UserRepository;
import com.dev.eduacademy.util.Role;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * The AdminService class is a service layer in the Spring Boot application.
 * It is responsible for handling business logic related to admin operations.
 * It interacts with the CourseRepository, UserRepository, LessonService, AssignmentService, ExamService, and GradeService for data access and utility functions.
 */
@Service
@RequiredArgsConstructor
public class AdminService {

    private final CourseRepository courseRepository;
    private final UtilityService utilityService;
    private final UserRepository userRepository;
    private final LessonService lessonService;
    private final AssignmentService assignmentService;
    private final ExamService examService;
    private final GradeService gradeService;

    /**
     * Retrieves all students in the system.
     *
     * @return a set of students
     */
    public Set<User> getAllStudentsInSystem() {
        return userRepository.findAllByRole(Role.STUDENT);
    }

    /**
     * Retrieves all instructors in the system.
     *
     * @return a set of instructors
     */
    public Set<User> getAllInstructorsInSystem() {
        return userRepository.findAllByRole(Role.INSTRUCTOR);
    }

    /**
     * Retrieves all admins in the system.
     *
     * @return a set of admins
     */
    public Set<User> getAllAdmins() {
        return userRepository.findAllByRole(Role.ADMIN);
    }

    /**
     * Retrieves all users in the system.
     *
     * @return a list of users
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Retrieves a user by id.
     *
     * @param userId the id of the user
     * @return the user
     * @throws EntityNotFoundException if the user is not found
     */
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
    }

    /**
     * Retrieves all published courses.
     *
     * @return a set of published courses
     */
    public Set<Course> getAllPublishedCourses() {
        return courseRepository.findByIsPublishedTrue();
    }

    /**
     * Retrieves all hidden courses.
     *
     * @return a set of hidden courses
     */
    public Set<Course> getAllHiddenCourses() {
        return courseRepository.findByIsPublishedFalse();
    }

    /**
     * Retrieves all courses in the system.
     *
     * @return a list of courses
     */
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    /**
     * Retrieves a course by id.
     *
     * @param courseId the id of the course
     * @return the course
     * @throws EntityNotFoundException if the course is not found
     */
    public Course getCourseById(Long courseId) {
        return courseRepository.findCourseById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + courseId));
    }

    /**
     * Publishes a course.
     *
     * @param courseId the id of the course
     * @return the updated course
     * @throws EntityNotFoundException if the course is not found or is already published
     */
    public Course publishCourse(Long courseId) {
        Course course = courseRepository.findCourseById(courseId).orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + courseId));
        if (course.getIsPublished())
            throw new EntityNotFoundException("Course is already published");
        course.setIsPublished(true);
        return courseRepository.save(course);
    }

    /**
     * Unpublishes a course.
     *
     * @param courseId the id of the course
     * @return the updated course
     * @throws EntityNotFoundException if the course is not found or is already unpublished
     */
    public Course unpublishCourse(Long courseId) {
        Course course = courseRepository.findCourseById(courseId).orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + courseId));
        if (!course.getIsPublished())
            throw new EntityNotFoundException("Course is already unpublished");
        course.setIsPublished(false);
        return courseRepository.save(course);
    }

    /**
     * Retrieves all lessons in a course.
     *
     * @param courseId the id of the course
     * @return a list of all lessons in the course
     */
    public List<Lesson> viewAllLessonsInCourse(Long courseId) {
        return lessonService.getAllLessonsByCourseId(courseId);
    }

    /**
     * Retrieves a lesson by its id and the id of its course.
     *
     * @param courseId the id of the course
     * @param lessonId the id of the lesson
     * @return the lesson
     */
    public Lesson viewLessonById(Long courseId, Long lessonId) {
        return lessonService.getLessonByIdAndCourseId(lessonId, courseId);
    }

    /**
     * Retrieves all assignments in a course.
     *
     * @param courseId the id of the course
     * @return a list of all assignments in the course
     */
    public List<Assignment> viewAllAssignmentsInCourse(Long courseId) {
        return assignmentService.getAllAssignmentsByCourseId(courseId);
    }

    /**
     * Retrieves an assignment by its id and the id of its course.
     *
     * @param courseId     the id of the course
     * @param assignmentId the id of the assignment
     * @return the assignment
     */
    public Assignment viewAssignmentById(Long courseId, Long assignmentId) {
        return assignmentService.getAssignmentByIdAndCourseId(assignmentId, courseId);
    }

    /**
     * Retrieves all exams in a course.
     *
     * @param courseId the id of the course
     * @return a list of all exams in the course
     */
    public List<Exam> viewAllExamsInCourse(Long courseId) {
        return examService.getAllExamsByCourseId(courseId);
    }

    /**
     * Retrieves an exam by its id and the id of its course.
     *
     * @param courseId the id of the course
     * @param examId   the id of the exam
     * @return the exam
     */
    public Exam viewExamById(Long courseId, Long examId) {
        return examService.getExamByIdAndCourseId(examId, courseId);
    }

    /**
     * Retrieves all grades in a course.
     *
     * @param courseId the id of the course
     * @return a list of all grades in the course
     */
    public List<Grade> viewAllGradesInCourse(Long courseId) {
        return gradeService.getAllGradesByCourseId(courseId);
    }

    /**
     * Retrieves all grades for an assignment.
     *
     * @param courseId     the id of the course
     * @param assignmentId the id of the assignment
     * @return a list of all grades for the assignment
     */
    public List<Grade> viewAllGradesForAssignment(Long courseId, Long assignmentId) {
        return gradeService.getGradesByAssignment(courseId, assignmentId);
    }

    /**
     * Retrieves all grades for an exam.
     *
     * @param courseId the id of the course
     * @param examId   the id of the exam
     * @return a list of all grades for the exam
     */
    public List<Grade> viewAllGradesForExam(Long courseId, Long examId) {
        return gradeService.getGradesByExam(courseId, examId);
    }

    /**
     * Deletes a course.
     *
     * @param courseId the id of the course
     * @throws EntityNotFoundException if the course is not found
     */
    public void deleteCourse(Long courseId) {
        Course course = courseRepository.findCourseById(courseId).orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + courseId));
        courseRepository.delete(course);
    }

    /**
     * Deletes a user.
     *
     * @param userId the id of the user
     * @throws EntityNotFoundException if the user is not found
     */
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        userRepository.delete(user);
    }
}
