package com.dev.eduacademy.service;

import com.dev.eduacademy.entity.Course;
import com.dev.eduacademy.entity.Exam;
import com.dev.eduacademy.entity.User;
import com.dev.eduacademy.model.ExamAnswerRequest;
import com.dev.eduacademy.model.ExamRequest;
import com.dev.eduacademy.repository.CourseRepository;
import com.dev.eduacademy.repository.ExamRepository;
import com.dev.eduacademy.util.GradeStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The ExamService class is a service layer in the Spring Boot application.
 * It is responsible for handling business logic related to exams.
 * It interacts with the ExamRepository, UtilityService, and CourseRepository for data access and utility functions.
 */
@Service
@RequiredArgsConstructor
public class ExamService {
    private final ExamRepository examRepository;
    private final UtilityService utilityService;
    private final CourseRepository courseRepository;

    /**
     * Creates an exam for a course.
     * Checks if the current authenticated user is an instructor for the course.
     * If the user is an instructor, it creates a new exam and adds it to the course.
     *
     * @param courseId the id of the course
     * @param request  the request object containing exam details
     */
    public void createExam(Long courseId, ExamRequest request) {
        User user = utilityService.getCurrentUserAuthenticated();
        Course course = courseRepository.findCourseById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + courseId));

        if (!course.getInstructors().contains(user)) {
            throw new AuthenticationCredentialsNotFoundException("You are not instructor for this course");
        }

        Exam exam = Exam.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .startDate(LocalDateTime.now())
                .dueDate(LocalDateTime.now().plusDays(2))
                .course(course)
                .gradeStatus(GradeStatus.PENDING)
                .build();

        course.getExams().add(exam);
        courseRepository.save(course);
        examRepository.save(exam);
    }

    /**
     * Updates an exam for a course.
     * Checks if the current authenticated user is an instructor for the course.
     * If the user is an instructor, it updates the exam details and saves it.
     *
     * @param courseId the id of the course
     * @param examId   the id of the exam
     * @param request  the request object containing updated exam details
     */
    public void updateExam(Long courseId, Long examId, ExamRequest request) {
        User user = utilityService.getCurrentUserAuthenticated();
        Course course = courseRepository.findCourseById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + courseId));

        Exam exam = examRepository.findByIdAndCourseId(examId, courseId)
                .orElseThrow(() -> new EntityNotFoundException("Exam not found with id: " + examId));

        if (!course.getInstructors().contains(user)) {
            throw new AuthenticationCredentialsNotFoundException("You are not instructor for this course");
        }

        exam.setTitle(request.getTitle());
        exam.setContent(request.getContent());
        course.getExams().remove(exam);
        course.getExams().add(exam);
        courseRepository.save(course);
        examRepository.save(exam);
    }

    /**
     * Deletes an exam for a course.
     * Checks if the current authenticated user is an instructor for the course.
     * If the user is an instructor, it removes the exam from the course and deletes it.
     *
     * @param courseId the id of the course
     * @param examId   the id of the exam
     */
    public void deleteExam(Long courseId, Long examId) {
        User user = utilityService.getCurrentUserAuthenticated();
        Course course = courseRepository.findCourseById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + courseId));

        Exam exam = examRepository.findByIdAndCourseId(examId, courseId)
                .orElseThrow(() -> new EntityNotFoundException("Exam not found with id: " + examId));

        if (!course.getInstructors().contains(user)) {
            throw new AuthenticationCredentialsNotFoundException("You are not instructor for this course");
        }

        course.getExams().remove(exam);
        courseRepository.save(course);
        examRepository.deleteByIdAndCourseId(examId, courseId);
    }

    /**
     * Retrieves an exam by its id and the id of its course.
     * If the exam does not exist, an exception is thrown.
     *
     * @param examId   the id of the exam
     * @param courseId the id of the course
     * @return the exam
     */
    public Exam getExamByIdAndCourseId(Long examId, Long courseId) {
        return examRepository.findByIdAndCourseId(examId, courseId)
                .orElseThrow(() -> new EntityNotFoundException("Exam not found with id: " + examId));
    }

    /**
     * Retrieves all exams for a specific course.
     *
     * @param courseId the id of the course
     * @return a list of exams
     */
    public List<Exam> getAllExamsByCourseId(Long courseId) {
        return examRepository.findAllByCourseId(courseId);
    }

    /**
     * Submits a student's answer for an exam.
     * Checks if the current authenticated user is a student for the course.
     * If the user is a student, it saves the student's answer and sets the grade status to submitted.
     *
     * @param courseId the id of the course
     * @param examId   the id of the exam
     * @param request  the request object containing the student's answer
     */
    public void sendStudentAnswer(Long courseId, Long examId, ExamAnswerRequest request) {
        User user = utilityService.getCurrentUserAuthenticated();
        Course course = courseRepository.findCourseById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + courseId));

        Exam exam = examRepository.findByIdAndCourseId(examId, courseId)
                .orElseThrow(() -> new EntityNotFoundException("Exam not found with id: " + examId));

        if (!course.getStudents().contains(user)) {
            throw new AuthenticationCredentialsNotFoundException("You are not student for this course");
        }

        exam.setStudentAnswer(request.getStudentAnswer());
        exam.setGradeStatus(GradeStatus.SUBMITTED);
        course.getExams().remove(exam);
        course.getExams().add(exam);
        courseRepository.save(course);
        examRepository.save(exam);
    }
}
