package com.dev.eduacademy.controller;

import com.dev.eduacademy.model.ResponseModel;
import com.dev.eduacademy.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * The StudentController class is a REST controller in the Spring Boot application.
 * It is responsible for handling HTTP requests related to student operations.
 * It interacts with the CourseService, EnrollmentRequestService, AssignmentService, LessonService, ExamService, and GradeService for data access and utility functions.
 * All endpoints in this controller are secured and can only be accessed by users with the 'STUDENT' role.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/student")
@PreAuthorize("hasRole('STUDENT')")
public class StudentController {

    private final CourseService courseService;
    private final EnrollmentRequestService enrollmentRequestService;
    private final AssignmentService assignmentService;
    private final LessonService lessonService;
    private final ExamService examService;
    private final GradeService gradeService;

    /**
     * This method handles the HTTP GET request to get all published courses.
     * It returns a ResponseEntity with a ResponseModel containing the list of published courses.
     *
     * @return ResponseEntity with the ResponseModel containing the list of published courses.
     */
    @GetMapping("/courses")
    public ResponseEntity<ResponseModel> getAllCourses() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(courseService.getAllPublishedCourses())
                        .build()
                );
    }

    /**
     * This method handles the HTTP GET request to get a published course by ID.
     * It takes the course ID as a path variable and returns a ResponseEntity with a ResponseModel containing the course details.
     *
     * @param courseId The ID of the course to get.
     * @return ResponseEntity with the ResponseModel containing the course details.
     */
    @GetMapping("/courses/{courseId}")
    public ResponseEntity<ResponseModel> getCourseById(@PathVariable Long courseId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(courseService.getPublishedCourseById(courseId))
                        .build()
                );
    }

    /**
     * This method handles the HTTP GET request to get all courses that the student is currently enrolled in.
     * It returns a ResponseEntity with a ResponseModel containing the list of enrolled courses.
     *
     * @return ResponseEntity with the ResponseModel containing the list of enrolled courses.
     */
    @GetMapping("/courses/enrolled")
    public ResponseEntity<ResponseModel> getEnrolledCourses() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(courseService.getStudentCourses())
                        .build()
                );
    }

    /**
     * This method handles the HTTP GET request to get a specific course that the student is currently enrolled in.
     * It takes the course ID as a path variable and returns a ResponseEntity with a ResponseModel containing the course details.
     *
     * @param courseId The ID of the enrolled course to get.
     * @return ResponseEntity with the ResponseModel containing the course details.
     */
    @GetMapping("/courses/enrolled/{courseId}")
    public ResponseEntity<ResponseModel> getEnrolledCourseById(@PathVariable Long courseId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(courseService.getStudentCourse(courseId))
                        .build()
                );
    }

    /**
     * This method handles the HTTP GET request to get all lessons for a specific course that the student is currently enrolled in.
     * It takes the course ID as a path variable and returns a ResponseEntity with a ResponseModel containing the list of lessons.
     *
     * @param courseId The ID of the enrolled course.
     * @return ResponseEntity with the ResponseModel containing the list of lessons.
     */
    @GetMapping("/courses/enrolled/{courseId}/lessons")
    public ResponseEntity<ResponseModel> getLessonsForEnrolledCourse(@PathVariable Long courseId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(lessonService.getAllLessonsByCourseId(courseId))
                        .build()
                );
    }

    /**
     * This method handles the HTTP GET request to get a specific lesson for a specific course that the student is currently enrolled in.
     * It takes the course ID and lesson ID as path variables and returns a ResponseEntity with a ResponseModel containing the lesson details.
     *
     * @param courseId The ID of the enrolled course.
     * @param lessonId The ID of the lesson to get.
     * @return ResponseEntity with the ResponseModel containing the lesson details.
     */
    @GetMapping("/courses/enrolled/{courseId}/lessons/{lessonId}")
    public ResponseEntity<ResponseModel> getLessonForEnrolledCourse(@PathVariable Long courseId, @PathVariable Long lessonId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(lessonService.getLessonByIdAndCourseId(lessonId, courseId))
                        .build()
                );
    }

    /**
     * This method handles the HTTP GET request to get all assignments for a specific course that the student is currently enrolled in.
     * It takes the course ID as a path variable and returns a ResponseEntity with a ResponseModel containing the list of assignments.
     *
     * @param courseId The ID of the enrolled course.
     * @return ResponseEntity with the ResponseModel containing the list of assignments.
     */
    @GetMapping("/courses/enrolled/{courseId}/assignments")
    public ResponseEntity<ResponseModel> getAssignmentsForEnrolledCourse(@PathVariable Long courseId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(assignmentService.getAllAssignmentsByCourseId(courseId))
                        .build()
                );
    }

    /**
     * This method handles the HTTP GET request to get a specific assignment for a specific course that the student is currently enrolled in.
     * It takes the course ID and assignment ID as path variables and returns a ResponseEntity with a ResponseModel containing the assignment details.
     *
     * @param courseId     The ID of the enrolled course.
     * @param assignmentId The ID of the assignment to get.
     * @return ResponseEntity with the ResponseModel containing the assignment details.
     */
    @GetMapping("/courses/enrolled/{courseId}/assignments/{assignmentId}")
    public ResponseEntity<ResponseModel> getAssignmentForEnrolledCourse(@PathVariable Long courseId, @PathVariable Long assignmentId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(assignmentService.getAssignmentByIdAndCourseId(assignmentId, courseId))
                        .build()
                );
    }

    /**
     * This method handles the HTTP GET request to get all grades for a specific assignment for a specific course that the student is currently enrolled in.
     * It takes the course ID and assignment ID as path variables and returns a ResponseEntity with a ResponseModel containing the list of grades.
     *
     * @param courseId     The ID of the enrolled course.
     * @param assignmentId The ID of the assignment.
     * @return ResponseEntity with the ResponseModel containing the list of grades.
     */
    @GetMapping("/courses/enrolled/{courseId}/assignments/{assignmentId}/grades")
    public ResponseEntity<ResponseModel> getGradesForAssignment(@PathVariable Long courseId, @PathVariable Long assignmentId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(gradeService.getGradesByAssignment(courseId, assignmentId))
                        .build()
                );
    }

    /**
     * This method handles the HTTP GET request to get a specific grade for a specific assignment for a specific course that the student is currently enrolled in.
     * It takes the course ID, assignment ID, and grade ID as path variables and returns a ResponseEntity with a ResponseModel containing the grade details.
     *
     * @param courseId     The ID of the enrolled course.
     * @param assignmentId The ID of the assignment.
     * @param gradeId      The ID of the grade to get.
     * @return ResponseEntity with the ResponseModel containing the grade details.
     */
    @GetMapping("/courses/enrolled/{courseId}/assignments/{assignmentId}/grades/{gradeId}")
    public ResponseEntity<ResponseModel> getSpecialGradeForAssignment(@PathVariable Long courseId, @PathVariable Long assignmentId, @PathVariable Long gradeId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(gradeService.getAssignmentGrade(courseId, assignmentId, gradeId))
                        .build()
                );
    }

    /**
     * This method handles the HTTP GET request to get all exams for a specific course that the student is currently enrolled in.
     * It takes the course ID as a path variable and returns a ResponseEntity with a ResponseModel containing the list of exams.
     *
     * @param courseId The ID of the enrolled course.
     * @return ResponseEntity with the ResponseModel containing the list of exams.
     */
    @GetMapping("/courses/enrolled/{courseId}/exams")
    public ResponseEntity<ResponseModel> getExamsForEnrolledCourse(@PathVariable Long courseId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(examService.getAllExamsByCourseId(courseId))
                        .build()
                );
    }

    /**
     * This method handles the HTTP GET request to get a specific exam for a specific course that the student is currently enrolled in.
     * It takes the course ID and exam ID as path variables and returns a ResponseEntity with a ResponseModel containing the exam details.
     *
     * @param courseId The ID of the enrolled course.
     * @param examId   The ID of the exam to get.
     * @return ResponseEntity with the ResponseModel containing the exam details.
     */
    @GetMapping("/courses/enrolled/{courseId}/exams/{examId}")
    public ResponseEntity<ResponseModel> getExamForEnrolledCourse(@PathVariable Long courseId, @PathVariable Long examId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(examService.getExamByIdAndCourseId(examId, courseId))
                        .build()
                );
    }

    /**
     * This method handles the HTTP GET request to get all grades for a specific exam for a specific course that the student is currently enrolled in.
     * It takes the course ID and exam ID as path variables and returns a ResponseEntity with a ResponseModel containing the list of grades.
     *
     * @param courseId The ID of the enrolled course.
     * @param examId   The ID of the exam.
     * @return ResponseEntity with the ResponseModel containing the list of grades.
     */
    @GetMapping("/courses/enrolled/{courseId}/exams/{examId}/grades")
    public ResponseEntity<ResponseModel> getGradesForExam(@PathVariable Long courseId, @PathVariable Long examId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(gradeService.getGradesByExam(courseId, examId))
                        .build()
                );
    }

    /**
     * This method handles the HTTP GET request to get a specific grade for a specific exam for a specific course that the student is currently enrolled in.
     * It takes the course ID, exam ID, and grade ID as path variables and returns a ResponseEntity with a ResponseModel containing the grade details.
     *
     * @param courseId The ID of the enrolled course.
     * @param examId   The ID of the exam.
     * @param gradeId  The ID of the grade to get.
     * @return ResponseEntity with the ResponseModel containing the grade details.
     */
    @GetMapping("/courses/enrolled/{courseId}/exams/{examId}/grades/{gradeId}")
    public ResponseEntity<ResponseModel> getSpecialGradeForExam(@PathVariable Long courseId, @PathVariable Long examId, @PathVariable Long gradeId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(gradeService.getExamGrade(courseId, examId, gradeId))
                        .build()
                );
    }

    /**
     * This method handles the HTTP GET request to get all enrollment requests for the student.
     * It returns a ResponseEntity with a ResponseModel containing the list of enrollment requests.
     *
     * @return ResponseEntity with the ResponseModel containing the list of enrollment requests.
     */
    @GetMapping("/enrollment-requests")
    public ResponseEntity<ResponseModel> getEnrollmentRequests() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(enrollmentRequestService.getStudentEnrollmentRequests())
                        .build()
                );
    }

    /**
     * This method handles the HTTP GET request to get a specific enrollment request for the student.
     * It takes the enrollment request ID as a path variable and returns a ResponseEntity with a ResponseModel containing the enrollment request details.
     *
     * @param enrollmentRequestId The ID of the enrollment request to get.
     * @return ResponseEntity with the ResponseModel containing the enrollment request details.
     */
    @GetMapping("/enrollment-requests/{enrollmentRequestId}")
    public ResponseEntity<ResponseModel> getEnrollmentRequest(@PathVariable Long enrollmentRequestId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(enrollmentRequestService.getStudentEnrollmentRequest(enrollmentRequestId))
                        .build()
                );
    }

    /**
     * This method handles the HTTP PUT request to enroll the student in a course.
     * It takes the course ID as a path variable and returns a ResponseEntity with a ResponseModel containing the enrollment status.
     *
     * @param courseId The ID of the course to enroll in.
     * @return ResponseEntity with the ResponseModel containing the enrollment status.
     */
    @PutMapping("/courses/{courseId}/unenroll")
    public ResponseEntity<ResponseModel> unEnrollFromCourse(@PathVariable Long courseId) {
        courseService.unEnrollStudentFromCourse(courseId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data("UnEnrolled successfully")
                        .build()
                );
    }

    /**
     * This method handles the HTTP DELETE request to cancel an enrollment request for the student.
     * It takes the course ID as a path variable and returns a ResponseEntity with a ResponseModel containing the cancellation status.
     *
     * @param courseId The ID of the course to cancel the enrollment request for.
     * @return ResponseEntity with the ResponseModel containing the cancellation status.
     */
    @DeleteMapping("/courses/{courseId}/enroll")
    public ResponseEntity<ResponseModel> enrollInCourse(@PathVariable Long courseId) {
        enrollmentRequestService.sendEnrollmentRequestForStudent(courseId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data("Enrolled successfully")
                        .build()
                );
    }

    /**
     * This method handles the HTTP DELETE request to cancel an enrollment request for the student.
     * It takes the course ID as a path variable and returns a ResponseEntity with a ResponseModel containing the cancellation status.
     *
     * @param courseId The ID of the course to cancel the enrollment request for.
     * @return ResponseEntity with the ResponseModel containing the cancellation status.
     */
    @DeleteMapping("/courses/{courseId}/enroll")
    public ResponseEntity<ResponseModel> cancelEnrollmentRequest(@PathVariable Long courseId) {
        enrollmentRequestService.cancelEnrollmentRequestForStudent(courseId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data("Enrollment request canceled successfully")
                        .build()
                );
    }
}
