package com.dev.eduacademy.controller;

import com.dev.eduacademy.model.ResponseModel;
import com.dev.eduacademy.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * The AdminController class handles the admin-related HTTP requests.
 * It provides endpoints for managing users, courses, lessons, assignments, exams, and grades.
 * Only users with the ADMIN role can access these endpoints.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    /**
     * This method handles the HTTP GET request to get all users in the system.
     * It returns a ResponseEntity with a ResponseModel containing the list of users.
     *
     * @return ResponseEntity with the list of users in the system.
     */
    @GetMapping("/users")
    public ResponseEntity<ResponseModel> getAllUsers() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(adminService.getAllUsers())
                        .build()
                );
    }

    /**
     * This method handles the HTTP GET request to get a user by ID.
     * It takes the user ID as a path variable and returns a ResponseEntity with a ResponseModel containing the user details.
     *
     * @param userId The ID of the user to retrieve.
     * @return ResponseEntity with the user details.
     */
    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseModel> getUserById(@PathVariable Long userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(adminService.getUserById(userId))
                        .build()
                );
    }

    /**
     * This method handles the HTTP DELETE request to delete a user by ID.
     * It takes the user ID as a path variable and returns a ResponseEntity with a ResponseModel containing the delete status.
     *
     * @param userId The ID of the user to delete.
     * @return ResponseEntity with the delete status.
     */
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<ResponseModel> deleteUserById(@PathVariable Long userId) {
        adminService.deleteUser(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data("User deleted successfully")
                        .build()
                );
    }

    /**
     * This method handles the HTTP GET request to get all students in the system.
     * It returns a ResponseEntity with a ResponseModel containing the list of students.
     *
     * @return ResponseEntity with the list of students in the system.
     */
    @GetMapping("/users/students")
    public ResponseEntity<ResponseModel> getAllStudents() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(adminService.getAllStudentsInSystem())
                        .build()
                );
    }

    /**
     * This method handles the HTTP GET request to get all instructors in the system.
     * It returns a ResponseEntity with a ResponseModel containing the list of instructors.
     *
     * @return ResponseEntity with the list of instructors in the system.
     */
    @GetMapping("/users/instructors")
    public ResponseEntity<ResponseModel> getAllInstructors() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(adminService.getAllInstructorsInSystem())
                        .build()
                );
    }

    /**
     * This method handles the HTTP GET request to get all admins in the system.
     * It returns a ResponseEntity with a ResponseModel containing the list of admins.
     *
     * @return ResponseEntity with the list of admins in the system.
     */
    @GetMapping("/users/admins")
    public ResponseEntity<ResponseModel> getAllAdmins() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(adminService.getAllAdmins())
                        .build()
                );
    }

    /**
     * This method handles the HTTP GET request to get all courses in the system.
     * It returns a ResponseEntity with a ResponseModel containing the list of courses.
     *
     * @return ResponseEntity with the list of courses in the system.
     */
    @GetMapping("/courses")
    public ResponseEntity<ResponseModel> getAllCourses() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(adminService.getAllCourses())
                        .build()
                );
    }

    /**
     * This method handles the HTTP GET request to get a course by ID.
     * It takes the course ID as a path variable and returns a ResponseEntity with a ResponseModel containing the course details.
     *
     * @param courseId The ID of the course to retrieve.
     * @return ResponseEntity with the course details.
     */
    @GetMapping("/courses/{courseId}")
    public ResponseEntity<ResponseModel> getCourseById(@PathVariable Long courseId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(adminService.getCourseById(courseId))
                        .build()
                );
    }

    /**
     * This method handles the HTTP DELETE request to delete a course by ID.
     * It takes the course ID as a path variable and returns a ResponseEntity with a ResponseModel containing the delete status.
     *
     * @param courseId The ID of the course to delete.
     * @return ResponseEntity with the delete status.
     */
    @DeleteMapping("/courses/{courseId}")
    public ResponseEntity<ResponseModel> deleteCourseById(@PathVariable Long courseId) {
        adminService.deleteCourse(courseId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data("Course deleted successfully")
                        .build()
                );
    }

    /**
     * This method handles the HTTP GET request to get all lessons in a course.
     * It takes the course ID as a path variable and returns a ResponseEntity with a ResponseModel containing the list of lessons.
     *
     * @param courseId The ID of the course to retrieve lessons from.
     * @return ResponseEntity with the list of lessons in the course.
     */
    @GetMapping("/courses/{courseId}/lessons")
    public ResponseEntity<ResponseModel> getAllLessonsForCourse(@PathVariable Long courseId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(adminService.viewAllLessonsInCourse(courseId))
                        .build()
                );
    }

    /**
     * This method handles the HTTP GET request to get a lesson by ID.
     * It takes the course ID and lesson ID as path variables and returns a ResponseEntity with a ResponseModel containing the lesson details.
     *
     * @param courseId The ID of the course containing the lesson.
     * @param lessonId The ID of the lesson to retrieve.
     * @return ResponseEntity with the lesson details.
     */
    @GetMapping("/courses/{courseId}/lessons/{lessonId}")
    public ResponseEntity<ResponseModel> getLessonById(@PathVariable Long courseId, @PathVariable Long lessonId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(adminService.viewLessonById(courseId, lessonId))
                        .build()
                );
    }

    /**
     * This method handles the HTTP GET request to get all assignments in a course.
     * It takes the course ID as a path variable and returns a ResponseEntity with a ResponseModel containing the list of assignments.
     *
     * @param courseId The ID of the course to retrieve assignments from.
     * @return ResponseEntity with the list of assignments in the course.
     */
    @GetMapping("/courses/{courseId}/assignments")
    public ResponseEntity<ResponseModel> getAllAssignmentsForCourse(@PathVariable Long courseId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(adminService.viewAllAssignmentsInCourse(courseId))
                        .build()
                );
    }

    /**
     * This method handles the HTTP GET request to get an assignment by ID.
     * It takes the course ID and assignment ID as path variables and returns a ResponseEntity with a ResponseModel containing the assignment details.
     *
     * @param courseId     The ID of the course containing the assignment.
     * @param assignmentId The ID of the assignment to retrieve.
     * @return ResponseEntity with the assignment details.
     */
    @GetMapping("/courses/{courseId}/assignments/{assignmentId}")
    public ResponseEntity<ResponseModel> getAssignmentById(@PathVariable Long courseId, @PathVariable Long assignmentId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(adminService.viewAssignmentById(courseId, assignmentId))
                        .build()
                );
    }

    /**
     * This method handles the HTTP GET request to get all exams in a course.
     * It takes the course ID as a path variable and returns a ResponseEntity with a ResponseModel containing the list of exams.
     *
     * @param courseId The ID of the course to retrieve exams from.
     * @return ResponseEntity with the list of exams in the course.
     */
    @GetMapping("/courses/{courseId}/exams")
    public ResponseEntity<ResponseModel> getAllExamsForCourse(@PathVariable Long courseId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(adminService.viewAllExamsInCourse(courseId))
                        .build()
                );
    }

    /**
     * This method handles the HTTP GET request to get an exam by ID.
     * It takes the course ID and exam ID as path variables and returns a ResponseEntity with a ResponseModel containing the exam details.
     *
     * @param courseId The ID of the course containing the exam.
     * @param examId   The ID of the exam to retrieve.
     * @return ResponseEntity with the exam details.
     */
    @GetMapping("/courses/{courseId}/exams/{examId}")
    public ResponseEntity<ResponseModel> getExamById(@PathVariable Long courseId, @PathVariable Long examId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(adminService.viewExamById(courseId, examId))
                        .build()
                );
    }

    /**
     * This method handles the HTTP GET request to get all grades for an assignment.
     * It takes the course ID and assignment ID as path variables and returns a ResponseEntity with a ResponseModel containing the list of grades.
     *
     * @param courseId     The ID of the course containing the assignment.
     * @param assignmentId The ID of the assignment to retrieve grades for.
     * @return ResponseEntity with the list of grades for the assignment.
     */
    @GetMapping("/courses/{courseId}/assignments/{assignmentId}/grades")
    public ResponseEntity<ResponseModel> getAllGradesForAssignment(@PathVariable Long courseId, @PathVariable Long assignmentId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(adminService.viewAllGradesForAssignment(courseId, assignmentId))
                        .build()
                );
    }

    /**
     * This method handles the HTTP GET request to get all grades for an exam.
     * It takes the course ID and exam ID as path variables and returns a ResponseEntity with a ResponseModel containing the list of grades.
     *
     * @param courseId The ID of the course containing the exam.
     * @param examId   The ID of the exam to retrieve grades for.
     * @return ResponseEntity with the list of grades for the exam.
     */
    @GetMapping("/courses/{courseId}/exams/{examId}/grades")
    public ResponseEntity<ResponseModel> getAllGradesForExam(@PathVariable Long courseId, @PathVariable Long examId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(adminService.viewAllGradesForExam(courseId, examId))
                        .build()
                );
    }

    /**
     * This method handles the HTTP GET request for getting all published courses in the system.
     * It returns a ResponseEntity with a ResponseModel containing the list of published courses.
     *
     * @return ResponseEntity with the ResponseModel containing the status, success, and list of published courses.
     */
    @GetMapping("/courses/published")
    public ResponseEntity<ResponseModel> getAllPublishedCourses() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(adminService.getAllPublishedCourses())
                        .build()
                );
    }

    /**
     * This method handles the HTTP GET request for getting all unpublished courses in the system.
     * It returns a ResponseEntity with a ResponseModel containing the list of unpublished courses.
     *
     * @return ResponseEntity with the ResponseModel containing the status, success, and list of unpublished courses.
     */
    @GetMapping("/courses/hidden")
    public ResponseEntity<ResponseModel> getAllUnpublishedCourses() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(adminService.getAllHiddenCourses())
                        .build()
                );
    }

    /**
     * This method handles the HTTP PUT request for publishing a course.
     * It takes the course ID as a path variable and returns a ResponseEntity with a ResponseModel containing the published course details.
     *
     * @param courseId The ID of the course to publish.
     * @return ResponseEntity with the ResponseModel containing the status, success, and published course details.
     */
    @PutMapping("/courses/{courseId}/publish")
    public ResponseEntity<ResponseModel> publishCourse(@PathVariable Long courseId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(adminService.publishCourse(courseId))
                        .build()
                );
    }

    /**
     * This method handles the HTTP PUT request for hiding a course.
     * It takes the course ID as a path variable and returns a ResponseEntity with a ResponseModel containing the hidden course details.
     *
     * @param courseId The ID of the course to hide.
     * @return ResponseEntity with the ResponseModel containing the status, success, and hidden course details.
     */
    @PutMapping("/courses/{courseId}/hide")
    public ResponseEntity<ResponseModel> unPublishCourse(@PathVariable Long courseId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(adminService.unpublishCourse(courseId))
                        .build()
                );
    }

}
