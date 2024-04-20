package com.dev.eduacademy.controller;

import com.dev.eduacademy.model.*;
import com.dev.eduacademy.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * The InstructorController class handles the instructor-related HTTP requests.
 * It provides endpoints for creating, updating, and deleting courses, lessons, assignments, exams, and grades.
 * It also provides endpoints for getting all courses, lessons, assignments, exams, and grades.
 * The endpoints are secured with the INSTRUCTOR role.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/instructor")
@PreAuthorize("hasRole('INSTRUCTOR')")
public class InstructorController {

    private final CourseService courseService;
    private final LessonService lessonService;
    private final AssignmentService assignmentService;
    private final ExamService examService;
    private final UserService userService;
    private final GradeService gradeService;
    private final EnrollmentRequestService enrollmentRequestService;

    /**
     * This method handles the HTTP POST request for creating a course.
     * It takes a CourseRequest object as input and returns a ResponseEntity with a ResponseModel.
     *
     * @param courseRequest The CourseRequest object containing the course details.
     * @return ResponseEntity with the ResponseModel containing the status, success, and data.
     */
    @PostMapping("/courses")
    public ResponseEntity<ResponseModel> createCourse(@RequestBody CourseRequest courseRequest) {
        courseService.createCourse(courseRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.CREATED)
                        .success(true)
                        .data("Course created successfully")
                        .build()
                );
    }

    /**
     * This method handles the HTTP GET request for getting all courses.
     * It returns a ResponseEntity with a ResponseModel containing the status, success, and data.
     *
     * @return ResponseEntity with the ResponseModel containing the status, success, and data.
     */
    @GetMapping("/courses")
    public ResponseEntity<ResponseModel> getMyCourses() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(courseService.getAllInstructorCourses())
                        .build()
                );
    }

    /**
     * This method handles the HTTP GET request for getting a specific course.
     * It takes the courseId as input and returns a ResponseEntity with a ResponseModel.
     *
     * @param courseId The courseId of the course to get.
     * @return ResponseEntity with the ResponseModel containing the status, success, and data.
     */
    @GetMapping("/courses/{courseId}")
    public ResponseEntity<ResponseModel> getMyCourse(@PathVariable Long courseId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(courseService.getSpecialCourseFromInstructorCourses(courseId))
                        .build()
                );
    }

    /**
     * This method handles the HTTP PUT request for updating a course.
     * It takes the courseId and CourseRequest as input and returns a ResponseEntity with a ResponseModel.
     *
     * @param courseId      The courseId of the course to update.
     * @param courseRequest The CourseRequest object containing the updated course details.
     * @return ResponseEntity with the ResponseModel containing the status, success, and data.
     */
    @PutMapping("/courses/{courseId}")
    public ResponseEntity<ResponseModel> updateCourse(@PathVariable Long courseId, @RequestBody CourseRequest courseRequest) {
        courseService.updateCourse(courseId, courseRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data("Course updated successfully")
                        .build()
                );
    }

    /**
     * This method handles the HTTP DELETE request for deleting a course.
     * It takes the courseId as input and returns a ResponseEntity with a ResponseModel.
     *
     * @param courseId The courseId of the course to delete.
     * @return ResponseEntity with the ResponseModel containing the status, success, and data.
     */
    @DeleteMapping("/courses/{courseId}")
    public ResponseEntity<ResponseModel> deleteCourse(@PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.ACCEPTED)
                        .success(true)
                        .data("Course deleted successfully")
                        .build()
                );
    }

    /**
     * This method handles the HTTP GET request for getting all students enrolled in a specific course.
     * It takes the courseId as input and returns a ResponseEntity with a ResponseModel.
     *
     * @param courseId The courseId of the course to get the students from.
     * @return ResponseEntity with the ResponseModel containing the status, success, and data.
     */
    @GetMapping("/courses/{courseId}/students")
    public ResponseEntity<ResponseModel> getStudents(@PathVariable Long courseId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(userService.getAllStudentForCourse(courseId))
                        .build()
                );
    }

    /**
     * This method handles the HTTP GET request for getting a specific student enrolled in a specific course.
     * It takes the courseId and studentId as input and returns a ResponseEntity with a ResponseModel.
     *
     * @param courseId  The courseId of the course to get the student from.
     * @param studentId The studentId of the student to get.
     * @return ResponseEntity with the ResponseModel containing the status, success, and data.
     */
    @GetMapping("/courses/{courseId}/students/{studentId}")
    public ResponseEntity<ResponseModel> getCourseStudent(@PathVariable Long courseId, @PathVariable Long studentId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(userService.getSpecialStudentFromCourseStudents(courseId, studentId))
                        .build()
                );
    }

    /**
     * This method handles the HTTP GET request for getting all enrollment requests for a specific course.
     * It takes the courseId as input and returns a ResponseEntity with a ResponseModel.
     *
     * @param courseId The courseId of the course to get the enrollment requests from.
     * @return ResponseEntity with the ResponseModel containing the status, success, and data.
     */
    @GetMapping("/courses/{courseId}/requests")
    public ResponseEntity<ResponseModel> getEnrollmentRequests(@PathVariable Long courseId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(enrollmentRequestService.getEnrollmentRequestsForCourse(courseId))
                        .build()
                );
    }

    /**
     * This method handles the HTTP GET request for getting a specific enrollment request for a specific course.
     * It takes the courseId and requestId as input and returns a ResponseEntity with a ResponseModel.
     *
     * @param courseId  The courseId of the course to get the enrollment request from.
     * @param requestId The requestId of the enrollment request to get.
     * @return ResponseEntity with the ResponseModel containing the status, success, and data.
     */
    @GetMapping("/courses/{courseId}/requests/{requestId}")
    public ResponseEntity<ResponseModel> getEnrollmentRequest(@PathVariable Long courseId, @PathVariable Long requestId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(enrollmentRequestService.getEnrollmentRequestForCourse(courseId, requestId))
                        .build()
                );
    }

    /**
     * This method handles the HTTP DELETE request for approving an enrollment request for a specific course.
     * It takes the courseId and requestId as input and returns a ResponseEntity with a ResponseModel.
     *
     * @param courseId  The courseId of the course to approve the enrollment request for.
     * @param requestId The requestId of the enrollment request to approve.
     * @return ResponseEntity with the ResponseModel containing the status, success, and data.
     */
    @DeleteMapping("/courses/{courseId}/requests/{requestId}/approve")
    public ResponseEntity<ResponseModel> approveEnrollmentRequest(@PathVariable Long courseId, @PathVariable Long requestId) {
        enrollmentRequestService.approveEnrollmentRequest(courseId, requestId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data("Request approved successfully")
                        .build()
                );
    }

    /**
     * This method handles the HTTP DELETE request for rejecting an enrollment request for a specific course.
     * It takes the courseId and requestId as input and returns a ResponseEntity with a ResponseModel.
     *
     * @param courseId  The courseId of the course to reject the enrollment request for.
     * @param requestId The requestId of the enrollment request to reject.
     * @return ResponseEntity with the ResponseModel containing the status, success, and data.
     */
    @DeleteMapping("/courses/{courseId}/requests/{requestId}/reject")
    public ResponseEntity<ResponseModel> rejectEnrollmentRequest(@PathVariable Long courseId, @PathVariable Long requestId) {
        enrollmentRequestService.rejectEnrollmentRequest(courseId, requestId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data("Request rejected successfully")
                        .build()
                );
    }

    /**
     * This method handles the HTTP GET request for getting all lessons for a specific course.
     * It takes the courseId as input and returns a ResponseEntity with a ResponseModel.
     *
     * @param courseId The courseId of the course to get the lessons from.
     * @return ResponseEntity with the ResponseModel containing the status, success, and data.
     */
    @GetMapping("/courses/{courseId}/lessons")
    public ResponseEntity<ResponseModel> getLessons(@PathVariable Long courseId) {
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
     * This method handles the HTTP GET request for getting a specific lesson for a specific course.
     * It takes the courseId and lessonId as input and returns a ResponseEntity with a ResponseModel.
     *
     * @param courseId The courseId of the course to get the lesson from.
     * @param lessonId The lessonId of the lesson to get.
     * @return ResponseEntity with the ResponseModel containing the status, success, and data.
     */
    @GetMapping("/courses/{courseId}/lessons/{lessonId}")
    public ResponseEntity<ResponseModel> getLesson(@PathVariable Long courseId, @PathVariable Long lessonId) {
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
     * This method handles the HTTP POST request for adding a lesson to a specific course.
     * It takes the courseId and LessonRequest as input and returns a ResponseEntity with a ResponseModel.
     *
     * @param courseId      The courseId of the course to add the lesson to.
     * @param lessonRequest The LessonRequest object containing the lesson details.
     * @return ResponseEntity with the ResponseModel containing the status, success, and data.
     */
    @PostMapping("/courses/{courseId}/lessons")
    public ResponseEntity<ResponseModel> addLesson(@PathVariable Long courseId, @RequestBody LessonRequest lessonRequest) {
        lessonService.addLesson(courseId, lessonRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.CREATED)
                        .success(true)
                        .data("Lesson added successfully")
                        .build()
                );
    }

    /**
     * This method handles the HTTP PUT request for updating a lesson for a specific course.
     * It takes the courseId, lessonId, and LessonRequest as input and returns a ResponseEntity with a ResponseModel.
     *
     * @param courseId      The courseId of the course to update the lesson for.
     * @param lessonId      The lessonId of the lesson to update.
     * @param lessonRequest The LessonRequest object containing the updated lesson details.
     * @return ResponseEntity with the ResponseModel containing the status, success, and data.
     */
    @PutMapping("/courses/{courseId}/lessons/{lessonId}")
    public ResponseEntity<ResponseModel> updateLesson(@PathVariable Long courseId, @PathVariable Long lessonId, @RequestBody LessonRequest lessonRequest) {
        lessonService.updateLesson(courseId, lessonId, lessonRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data("Lesson updated successfully")
                        .build()
                );
    }

    /**
     * This method handles the HTTP DELETE request for deleting a lesson for a specific course.
     * It takes the courseId and lessonId as input and returns a ResponseEntity with a ResponseModel.
     *
     * @param courseId The courseId of the course to delete the lesson from.
     * @param lessonId The lessonId of the lesson to delete.
     * @return ResponseEntity with the ResponseModel containing the status, success, and data.
     */
    @DeleteMapping("/courses/{courseId}/lessons/{lessonId}")
    public ResponseEntity<ResponseModel> deleteLesson(@PathVariable Long courseId, @PathVariable Long lessonId) {
        lessonService.deleteLesson(courseId, lessonId);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.ACCEPTED)
                        .success(true)
                        .data("Lesson deleted successfully")
                        .build()
                );
    }

    /**
     * This method handles the HTTP GET request for getting all assignments for a specific course.
     * It takes the courseId as input and returns a ResponseEntity with a ResponseModel.
     *
     * @param courseId The courseId of the course to get the assignments from.
     * @return ResponseEntity with the ResponseModel containing the status, success, and data.
     */
    @GetMapping("/courses/{courseId}/assignments")
    public ResponseEntity<ResponseModel> getAssignments(@PathVariable Long courseId) {
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
     * This method handles the HTTP GET request for getting a specific assignment for a specific course.
     * It takes the courseId and assignmentId as input and returns a ResponseEntity with a ResponseModel.
     *
     * @param courseId     The courseId of the course to get the assignment from.
     * @param assignmentId The assignmentId of the assignment to get.
     * @return ResponseEntity with the ResponseModel containing the status, success, and data.
     */
    @GetMapping("/courses/{courseId}/assignments/{assignmentId}")
    public ResponseEntity<ResponseModel> getAssignment(@PathVariable Long courseId, @PathVariable Long assignmentId) {
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
     * This method handles the HTTP POST request for adding an assignment to a specific course.
     * It takes the courseId and AssignmentRequest as input and returns a ResponseEntity with a ResponseModel.
     *
     * @param courseId          The courseId of the course to add the assignment to.
     * @param assignmentRequest The AssignmentRequest object containing the assignment details.
     * @return ResponseEntity with the ResponseModel containing the status, success, and data.
     */
    @PostMapping("/courses/{courseId}/assignments")
    public ResponseEntity<ResponseModel> addAssignment(@PathVariable Long courseId, @RequestBody AssignmentRequest assignmentRequest) {
        assignmentService.createAssignment(courseId, assignmentRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.CREATED)
                        .success(true)
                        .data("Assignment added successfully")
                        .build()
                );
    }

    /**
     * This method handles the HTTP PUT request for updating an assignment for a specific course.
     * It takes the courseId, assignmentId, and AssignmentRequest as input and returns a ResponseEntity with a ResponseModel.
     *
     * @param courseId          The courseId of the course to update the assignment for.
     * @param assignmentId      The assignmentId of the assignment to update.
     * @param assignmentRequest The AssignmentRequest object containing the updated assignment details.
     * @return ResponseEntity with the ResponseModel containing the status, success, and data.
     */
    @PutMapping("/courses/{courseId}/assignments/{assignmentId}")
    public ResponseEntity<ResponseModel> updateAssignment(@PathVariable Long courseId, @PathVariable Long assignmentId, @RequestBody AssignmentRequest assignmentRequest) {
        assignmentService.updateAssignment(courseId, assignmentId, assignmentRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data("Assignment updated successfully")
                        .build()
                );
    }

    /**
     * This method handles the HTTP DELETE request for deleting an assignment for a specific course.
     * It takes the courseId and assignmentId as input and returns a ResponseEntity with a ResponseModel.
     *
     * @param courseId     The courseId of the course to delete the assignment from.
     * @param assignmentId The assignmentId of the assignment to delete.
     * @return ResponseEntity with the ResponseModel containing the status, success, and data.
     */
    @DeleteMapping("/courses/{courseId}/assignments/{assignmentId}")
    public ResponseEntity<ResponseModel> deleteAssignment(@PathVariable Long courseId, @PathVariable Long assignmentId) {
        assignmentService.deleteAssignment(courseId, assignmentId);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.ACCEPTED)
                        .success(true)
                        .data("Assignment deleted successfully")
                        .build()
                );
    }

    /**
     * This method handles the HTTP GET request for getting all exams for a specific course.
     * It takes the courseId as input and returns a ResponseEntity with a ResponseModel.
     *
     * @param courseId The courseId of the course to get the exams from.
     * @return ResponseEntity with the ResponseModel containing the status, success, and data.
     */
    @GetMapping("/courses/{courseId}/exams")
    public ResponseEntity<ResponseModel> getExams(@PathVariable Long courseId) {
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
     * This method handles the HTTP GET request for getting a specific exam for a specific course.
     * It takes the courseId and examId as input and returns a ResponseEntity with a ResponseModel.
     *
     * @param courseId The courseId of the course to get the exam from.
     * @param examId   The examId of the exam to get.
     * @return ResponseEntity with the ResponseModel containing the status, success, and data.
     */
    @GetMapping("/courses/{courseId}/exams/{examId}")
    public ResponseEntity<ResponseModel> getExam(@PathVariable Long courseId, @PathVariable Long examId) {
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
     * This method handles the HTTP POST request for adding an exam to a specific course.
     * It takes the courseId and ExamRequest as input and returns a ResponseEntity with a ResponseModel.
     *
     * @param courseId    The courseId of the course to add the exam to.
     * @param examRequest The ExamRequest object containing the exam details.
     * @return ResponseEntity with the ResponseModel containing the status, success, and data.
     */
    @PostMapping("/courses/{courseId}/exams")
    public ResponseEntity<ResponseModel> addExam(@PathVariable Long courseId, @RequestBody ExamRequest examRequest) {
        examService.createExam(courseId, examRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.CREATED)
                        .success(true)
                        .data("Exam added successfully")
                        .build()
                );
    }

    /**
     * This method handles the HTTP PUT request for updating an exam for a specific course.
     * It takes the courseId, examId, and ExamRequest as input and returns a ResponseEntity with a ResponseModel.
     *
     * @param courseId    The courseId of the course to update the exam for.
     * @param examId      The examId of the exam to update.
     * @param examRequest The ExamRequest object containing the updated exam details.
     * @return ResponseEntity with the ResponseModel containing the status, success, and data.
     */
    @PutMapping("/courses/{courseId}/exams/{examId}")
    public ResponseEntity<ResponseModel> updateExam(@PathVariable Long courseId, @PathVariable Long examId, @RequestBody ExamRequest examRequest) {
        examService.updateExam(courseId, examId, examRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data("Exam updated successfully")
                        .build()
                );
    }

    /**
     * This method handles the HTTP DELETE request for deleting an exam for a specific course.
     * It takes the courseId and examId as input and returns a ResponseEntity with a ResponseModel.
     *
     * @param courseId The courseId of the course to delete the exam from.
     * @param examId   The examId of the exam to delete.
     * @return ResponseEntity with the ResponseModel containing the status, success, and data.
     */
    @DeleteMapping("/courses/{courseId}/exams/{examId}")
    public ResponseEntity<ResponseModel> deleteExam(@PathVariable Long courseId, @PathVariable Long examId) {
        examService.deleteExam(courseId, examId);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.ACCEPTED)
                        .success(true)
                        .data("Exam deleted successfully")
                        .build()
                );
    }

    /**
     * This method handles the HTTP POST request for adding grades for an assignment.
     * It takes the courseId, assignmentId, and GradeRequest as input and returns a ResponseEntity with a ResponseModel.
     *
     * @param courseId     The courseId of the course to add the grades to.
     * @param assignmentId The assignmentId of the assignment to add the grades to.
     * @param gradeRequest The GradeRequest object containing the grade details.
     * @return ResponseEntity with the ResponseModel containing the status, success, and data.
     */
    @PostMapping("/courses/{courseId}/assignments/{assignmentId}/grades")
    public ResponseEntity<ResponseModel> addAssignmentGrades(@PathVariable Long courseId, @PathVariable Long assignmentId, @RequestBody GradeRequest gradeRequest) {
        gradeService.createGradeForAssignment(courseId, assignmentId, gradeRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.CREATED)
                        .success(true)
                        .data("Grades added successfully")
                        .build()
                );
    }

    /**
     * This method handles the HTTP PUT request for updating grades for an assignment.
     * It takes the courseId, assignmentId, gradeId, and GradeRequest as input and returns a ResponseEntity with a ResponseModel.
     *
     * @param courseId     The courseId of the course to update the grades for.
     * @param assignmentId The assignmentId of the assignment to update the grades for.
     * @param gradeId      The gradeId of the grade to update.
     * @param gradeRequest The GradeRequest object containing the updated grade details.
     * @return ResponseEntity with the ResponseModel containing the status, success, and data.
     */
    @PutMapping("/courses/{courseId}/assignments/{assignmentId}/grades/{gradeId}")
    public ResponseEntity<ResponseModel> updateAssignmentGrades(@PathVariable Long courseId, @PathVariable Long assignmentId, @PathVariable Long gradeId, @RequestBody GradeRequest gradeRequest) {
        gradeService.updateGradeForAssignment(courseId, assignmentId, gradeId, gradeRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data("Grades updated successfully")
                        .build()
                );
    }

    /**
     * This method handles the HTTP DELETE request for deleting grades for an assignment.
     * It takes the courseId, assignmentId, and gradeId as input and returns a ResponseEntity with a ResponseModel.
     *
     * @param courseId     The courseId of the course to delete the grades from.
     * @param assignmentId The assignmentId of the assignment to delete the grades from.
     * @param gradeId      The gradeId of the grade to delete.
     * @return ResponseEntity with the ResponseModel containing the status, success, and data.
     */
    @DeleteMapping("/courses/{courseId}/assignments/{assignmentId}/grades/{gradeId}")
    public ResponseEntity<ResponseModel> deleteAssignmentGrades(@PathVariable Long courseId, @PathVariable Long assignmentId, @PathVariable Long gradeId) {
        gradeService.deleteGradeForAssignment(courseId, assignmentId, gradeId);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.ACCEPTED)
                        .success(true)
                        .data("Grades deleted successfully")
                        .build()
                );
    }

    /**
     * This method handles the HTTP POST request for adding grades for an exam.
     * It takes the courseId, examId, and GradeRequest as input and returns a ResponseEntity with a ResponseModel.
     *
     * @param courseId     The courseId of the course to add the grades to.
     * @param examId       The examId of the exam to add the grades to.
     * @param gradeRequest The GradeRequest object containing the grade details.
     * @return ResponseEntity with the ResponseModel containing the status, success, and data.
     */
    @PostMapping("/courses/{courseId}/exams/{examId}/grades")
    public ResponseEntity<ResponseModel> addExamGrades(@PathVariable Long courseId, @PathVariable Long examId, @RequestBody GradeRequest gradeRequest) {
        gradeService.createGradeForExam(courseId, examId, gradeRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.CREATED)
                        .success(true)
                        .data("Grades added successfully")
                        .build()
                );
    }

    /**
     * This method handles the HTTP PUT request for updating grades for an exam.
     * It takes the courseId, examId, gradeId, and GradeRequest as input and returns a ResponseEntity with a ResponseModel.
     *
     * @param courseId     The courseId of the course to update the grades for.
     * @param examId       The examId of the exam to update the grades for.
     * @param gradeId      The gradeId of the grade to update.
     * @param gradeRequest The GradeRequest object containing the updated grade details.
     * @return ResponseEntity with the ResponseModel containing the status, success, and data.
     */
    @PutMapping("/courses/{courseId}/exams/{examId}/grades/{gradeId}")
    public ResponseEntity<ResponseModel> updateExamGrades(@PathVariable Long courseId, @PathVariable Long examId, @PathVariable Long gradeId, @RequestBody GradeRequest gradeRequest) {
        gradeService.updateGradeForExam(courseId, examId, gradeId, gradeRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data("Grades updated successfully")
                        .build()
                );
    }

    /**
     * This method handles the HTTP DELETE request for deleting grades for an exam.
     * It takes the courseId, examId, and gradeId as input and returns a ResponseEntity with a ResponseModel.
     *
     * @param courseId The courseId of the course to delete the grades from.
     * @param examId   The examId of the exam to delete the grades from.
     * @param gradeId  The gradeId of the grade to delete.
     * @return ResponseEntity with the ResponseModel containing the status, success, and data.
     */
    @DeleteMapping("/courses/{courseId}/exams/{examId}/grades/{gradeId}")
    public ResponseEntity<ResponseModel> deleteExamGrades(@PathVariable Long courseId, @PathVariable Long examId, @PathVariable Long gradeId) {
        gradeService.deleteGradeForExam(courseId, examId, gradeId);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.ACCEPTED)
                        .success(true)
                        .data("Grades deleted successfully")
                        .build()
                );
    }

    /**
     * This method handles the HTTP GET request for getting all grades for an assignment.
     * It takes the courseId and assignmentId as input and returns a ResponseEntity with a ResponseModel.
     *
     * @param courseId     The courseId of the course to get the grades from.
     * @param assignmentId The assignmentId of the assignment to get the grades from.
     * @return ResponseEntity with the ResponseModel containing the status, success, and data.
     */
    @GetMapping("/courses/{courseId}/assignments/{assignmentId}/grades")
    public ResponseEntity<ResponseModel> getAssignmentGrades(@PathVariable Long courseId, @PathVariable Long assignmentId) {
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
     * This method handles the HTTP GET request for getting all grades for an exam.
     * It takes the courseId and examId as input and returns a ResponseEntity with a ResponseModel.
     *
     * @param courseId The courseId of the course to get the grades from.
     * @param examId   The examId of the exam to get the grades from.
     * @return ResponseEntity with the ResponseModel containing the status, success, and data.
     */
    @GetMapping("/courses/{courseId}/exams/{examId}/grades")
    public ResponseEntity<ResponseModel> getExamGrades(@PathVariable Long courseId, @PathVariable Long examId) {
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
     * This method handles the HTTP GET request for getting a specific grade for an assignment.
     * It takes the courseId, assignmentId, and gradeId as input and returns a ResponseEntity with a ResponseModel.
     *
     * @param courseId     The courseId of the course to get the grade from.
     * @param assignmentId The assignmentId of the assignment to get the grade from.
     * @param gradeId      The gradeId of the grade to get.
     * @return ResponseEntity with the ResponseModel containing the status, success, and data.
     */
    @GetMapping("/courses/{courseId}/assignments/{assignmentId}/grades/{gradeId}")
    public ResponseEntity<ResponseModel> getAssignmentGrade(@PathVariable Long courseId, @PathVariable Long assignmentId, @PathVariable Long gradeId) {
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
     * This method handles the HTTP GET request for getting a specific grade for an exam.
     * It takes the courseId, examId, and gradeId as input and returns a ResponseEntity with a ResponseModel.
     *
     * @param courseId The courseId of the course to get the grade from.
     * @param examId   The examId of the exam to get the grade from.
     * @param gradeId  The gradeId of the grade to get.
     * @return ResponseEntity with the ResponseModel containing the status, success, and data.
     */
    @GetMapping("/courses/{courseId}/exams/{examId}/grades/{gradeId}")
    public ResponseEntity<ResponseModel> getExamGrade(@PathVariable Long courseId, @PathVariable Long examId, @PathVariable Long gradeId) {
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
}
