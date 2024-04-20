package com.dev.eduacademy.service;

import com.dev.eduacademy.entity.*;
import com.dev.eduacademy.model.GradeRequest;
import com.dev.eduacademy.repository.AssignmentRepository;
import com.dev.eduacademy.repository.CourseRepository;
import com.dev.eduacademy.repository.ExamRepository;
import com.dev.eduacademy.repository.GradeRepository;
import com.dev.eduacademy.util.GradeStatus;
import com.dev.eduacademy.util.GradeUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The GradeService class is a service layer in the Spring Boot application.
 * It is responsible for handling business logic related to grades.
 * It interacts with the GradeRepository, UtilityService, CourseRepository, ExamRepository, and AssignmentRepository for data access and utility functions.
 */
@Service
@RequiredArgsConstructor
public class GradeService {

    private final GradeRepository gradeRepository;
    private final UtilityService utilityService;
    private final CourseRepository courseRepository;
    private final ExamRepository examRepository;
    private final AssignmentRepository assignmentRepository;

    /**
     * Creates a grade for an assignment.
     * Checks if the current authenticated user is an instructor for the course.
     * If the user is an instructor, it creates a new grade and adds it to the assignment and course.
     *
     * @param courseId     the id of the course
     * @param assignmentId the id of the assignment
     * @param request      the request object containing grade details
     */
    public void createGradeForAssignment(Long courseId, Long assignmentId, GradeRequest request) {
        User user = utilityService.getCurrentUserAuthenticated();
        Course course = courseRepository.findCourseById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + courseId));

        if (!course.getInstructors().contains(user)) {
            throw new IllegalArgumentException("User is not instructor in this course");
        }

        Assignment assignment = assignmentRepository.findByIdAndCourseId(assignmentId, courseId)
                .orElseThrow(() -> new EntityNotFoundException("Assignment not found with id: " + assignmentId));

        Grade grade = Grade.builder()
                .gradeName(GradeUtil.getGradeName(request.getScore()))
                .gradeLetter(GradeUtil.getGradeLetter(request.getScore()))
                .course(course)
                .assignment(assignment)
                .build();

        assignment.getGrades().add(grade);
        course.getGrades().add(grade);
        courseRepository.save(course);
        assignmentRepository.save(assignment);
        gradeRepository.save(grade);
    }

    /**
     * Creates a grade for an exam.
     * Checks if the current authenticated user is an instructor for the course.
     * If the user is an instructor, it creates a new grade and adds it to the exam and course.
     *
     * @param courseId the id of the course
     * @param examId   the id of the exam
     * @param request  the request object containing grade details
     */
    public void createGradeForExam(Long courseId, Long examId, GradeRequest request) {
        User user = utilityService.getCurrentUserAuthenticated();
        Course course = courseRepository.findCourseById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + courseId));

        if (!course.getInstructors().contains(user)) {
            throw new IllegalArgumentException("User is not instructor in this course");
        }

        Exam exam = examRepository.findByIdAndCourseId(examId, courseId)
                .orElseThrow(() -> new EntityNotFoundException("Exam not found with id: " + examId));
        exam.setGradeStatus(GradeStatus.GRADED);

        Grade grade = Grade.builder()
                .gradeName(GradeUtil.getGradeName(request.getScore()))
                .gradeLetter(GradeUtil.getGradeLetter(request.getScore()))
                .course(course)
                .exam(exam)
                .build();

        exam.getGrades().add(grade);
        course.getGrades().add(grade);
        courseRepository.save(course);
        examRepository.save(exam);
        gradeRepository.save(grade);
    }

    /**
     * Updates a grade for an assignment.
     * Checks if the current authenticated user is an instructor for the course.
     * If the user is an instructor, it updates the grade details and saves it.
     *
     * @param courseId     the id of the course
     * @param assignmentId the id of the assignment
     * @param gradeId      the id of the grade
     * @param request      the request object containing updated grade details
     */
    public void updateGradeForAssignment(
            Long courseId,
            Long assignmentId,
            Long gradeId,
            GradeRequest request
    ) {
        User user = utilityService.getCurrentUserAuthenticated();
        Course course = courseRepository.findCourseById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + courseId));
        Grade grade = gradeRepository.findByAssignmentIdAndId(assignmentId, gradeId)
                .orElseThrow(() -> new EntityNotFoundException("Grade not found with id: " + gradeId));

        // check if this user is instructor for this course
        if (!course.getInstructors().contains(user)) {
            throw new IllegalArgumentException("User is not instructor in this course");
        }

        grade.setGradeName(GradeUtil.getGradeName(request.getScore()));
        grade.setGradeLetter(GradeUtil.getGradeLetter(request.getScore()));
        grade.getAssignment().getGrades().remove(grade);
        grade.getAssignment().getGrades().add(grade);
        assignmentRepository.save(grade.getAssignment());
        course.getGrades().remove(grade);
        course.getGrades().add(grade);
        courseRepository.save(course);
        gradeRepository.save(grade);
    }

    /**
     * Updates a grade for an exam.
     * Checks if the current authenticated user is an instructor for the course.
     * If the user is an instructor, it updates the grade details and saves it.
     *
     * @param courseId the id of the course
     * @param examId   the id of the exam
     * @param gradeId  the id of the grade
     * @param request  the request object containing updated grade details
     */
    public void updateGradeForExam(
            Long courseId,
            Long examId,
            Long gradeId,
            GradeRequest request
    ) {
        User user = utilityService.getCurrentUserAuthenticated();
        Course course = courseRepository.findCourseById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + courseId));
        Grade grade = gradeRepository.findByExamIdAndId(examId, gradeId)
                .orElseThrow(() -> new EntityNotFoundException("Grade not found with id: " + gradeId));

        if (!course.getInstructors().contains(user)) {
            throw new IllegalArgumentException("User is not instructor in this course");
        }

        grade.setGradeName(GradeUtil.getGradeName(request.getScore()));
        grade.setGradeLetter(GradeUtil.getGradeLetter(request.getScore()));
        grade.getExam().getGrades().remove(grade);
        grade.getExam().getGrades().add(grade);
        examRepository.save(grade.getExam());
        course.getGrades().remove(grade);
        course.getGrades().add(grade);
        courseRepository.save(course);
        gradeRepository.save(grade);
    }

    /**
     * Deletes a grade for an assignment.
     * Checks if the current authenticated user is an instructor for the course.
     * If the user is an instructor, it removes the grade from the assignment and course and deletes it.
     *
     * @param courseId     the id of the course
     * @param assignmentId the id of the assignment
     * @param gradeId      the id of the grade
     */
    public void deleteGradeForAssignment(Long courseId, Long assignmentId, Long gradeId) {
        User user = utilityService.getCurrentUserAuthenticated();
        Course course = courseRepository.findCourseById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + courseId));
        Grade grade = gradeRepository.findByAssignmentIdAndId(assignmentId, gradeId)
                .orElseThrow(() -> new EntityNotFoundException("Grade not found with id: " + gradeId));

        if (!course.getInstructors().contains(user)) {
            throw new IllegalArgumentException("User is not instructor in this course");
        }

        grade.getAssignment().getGrades().remove(grade);
        assignmentRepository.save(grade.getAssignment());
        course.getGrades().remove(grade);
        courseRepository.save(grade.getCourse());
        gradeRepository.delete(grade);
    }

    /**
     * Deletes a grade for an exam.
     * Checks if the current authenticated user is an instructor for the course.
     * If the user is an instructor, it removes the grade from the exam and course and deletes it.
     *
     * @param courseId the id of the course
     * @param examId   the id of the exam
     * @param gradeId  the id of the grade
     */
    public void deleteGradeForExam(Long courseId, Long examId, Long gradeId) {
        User user = utilityService.getCurrentUserAuthenticated();
        Course course = courseRepository.findCourseById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + courseId));
        Grade grade = gradeRepository.findByExamIdAndId(examId, gradeId)
                .orElseThrow(() -> new EntityNotFoundException("Grade not found with id: " + gradeId));

        if (!course.getInstructors().contains(user)) {
            throw new IllegalArgumentException("User is not instructor in this course");
        }

        grade.getExam().getGrades().remove(grade);
        examRepository.save(grade.getExam());
        course.getGrades().remove(grade);
        courseRepository.save(grade.getCourse());
        gradeRepository.delete(grade);
    }

    /**
     * Retrieves all grades for a specific assignment.
     *
     * @param courseId     the id of the course
     * @param assignmentId the id of the assignment
     * @return a list of grades
     */
    public List<Grade> getGradesByAssignment(Long courseId, Long assignmentId) {
        return gradeRepository.findAllByCourseIdAndExamId(courseId, assignmentId);
    }

    /**
     * Retrieves all grades for a specific exam.
     *
     * @param courseId the id of the course
     * @param examId   the id of the exam
     * @return a list of grades
     */
    public List<Grade> getGradesByExam(Long courseId, Long examId) {
        return gradeRepository.findAllByCourseIdAndExamId(courseId, examId);
    }

    /**
     * Retrieves a specific grade by its id and the id of its assignment.
     * If the grade does not exist, an exception is thrown.
     *
     * @param courseId     the id of the course
     * @param assignmentId the id of the assignment
     * @param gradeId      the id of the grade
     * @return the grade
     */
    public Grade getAssignmentGrade(Long courseId, Long assignmentId, Long gradeId) {
        return gradeRepository.findAllByCourseIdAndAssignmentId(courseId, assignmentId).stream()
                .filter(grade -> grade.getId().equals(gradeId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Grade not found with id: " + gradeId));
    }

    /**
     * Retrieves a specific grade by its id and the id of its exam.
     * If the grade does not exist, an exception is thrown.
     *
     * @param courseId the id of the course
     * @param examId   the id of the exam
     * @param gradeId  the id of the grade
     * @return the grade
     */
    public Grade getExamGrade(Long courseId, Long examId, Long gradeId) {
        return gradeRepository.findAllByCourseIdAndAssignmentId(courseId, examId).stream()
                .filter(grade -> grade.getId().equals(gradeId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Grade not found with id: " + gradeId));
    }

    /**
     * Retrieves all grades for a specific course.
     *
     * @param courseId the id of the course
     * @return a list of grades
     */
    public List<Grade> getAllGradesByCourseId(Long courseId) {
        return gradeRepository.findAllByCourseId(courseId);
    }


}
