package com.dev.eduacademy.service;

import com.dev.eduacademy.entity.Assignment;
import com.dev.eduacademy.entity.Course;
import com.dev.eduacademy.entity.User;
import com.dev.eduacademy.model.AssignmentAnswerRequest;
import com.dev.eduacademy.model.AssignmentRequest;
import com.dev.eduacademy.repository.AssignmentRepository;
import com.dev.eduacademy.repository.CourseRepository;
import com.dev.eduacademy.util.GradeStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentService {
    private final AssignmentRepository assignmentRepository;
    private final UtilityService utilityService;
    private final CourseRepository courseRepository;

    /**
     * Creates an assignment for a course.
     * Checks if the current authenticated user is an instructor for the course.
     * If the user is an instructor, it creates a new assignment and adds it to the course.
     *
     * @param courseId the id of the course
     * @param request  the request object containing assignment details
     */
    public void createAssignment(Long courseId, AssignmentRequest request) {
        User user = utilityService.getCurrentUserAuthenticated();
        Course course = courseRepository.findCourseById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + courseId));

        if (!course.getInstructors().contains(user)) {
            throw new AuthenticationCredentialsNotFoundException("You are not instructor for this course");
        }

        Assignment assignment = Assignment.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .startDate(LocalDateTime.now())
                .dueDate(LocalDateTime.now().plusDays(2))
                .course(course)
                .gradeStatus(GradeStatus.PENDING)
                .build();

        course.getAssignments().add(assignment);
        courseRepository.save(course);
        assignmentRepository.save(assignment);
    }

    /**
     * Updates an existing assignment in a course.
     * Checks if the current authenticated user is an instructor for the course.
     * If the user is an instructor, it updates the assignment details and saves it.
     *
     * @param courseId     the id of the course
     * @param assignmentId the id of the assignment
     * @param request      the request object containing assignment details
     */
    public void updateAssignment(Long courseId, Long assignmentId, AssignmentRequest request) {
        User user = utilityService.getCurrentUserAuthenticated();
        Course course = courseRepository.findCourseById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + courseId));

        if (!course.getInstructors().contains(user)) {
            throw new AuthenticationCredentialsNotFoundException("You are not instructor for this course");
        }

        Assignment assignment = assignmentRepository.findByIdAndCourseId(assignmentId, courseId)
                .orElseThrow(() -> new EntityNotFoundException("Assignment not found with id: " + assignmentId));

        assignment.setTitle(request.getTitle());
        assignment.setContent(request.getContent());
        course.getAssignments().remove(assignment);
        course.getAssignments().add(assignment);
        courseRepository.save(course);
        assignmentRepository.save(assignment);
    }

    /**
     * Deletes an assignment from a course.
     * Checks if the current authenticated user is an instructor for the course.
     * If the user is an instructor, it removes the assignment from the course and deletes it.
     *
     * @param courseId     the id of the course
     * @param assignmentId the id of the assignment
     */
    public void deleteAssignment(Long courseId, Long assignmentId) {
        User user = utilityService.getCurrentUserAuthenticated();
        Course course = courseRepository.findCourseById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + courseId));

        if (!course.getInstructors().contains(user)) {
            throw new AuthenticationCredentialsNotFoundException("You are not instructor for this course");
        }

        Assignment assignment = assignmentRepository.findByIdAndCourseId(assignmentId, courseId)
                .orElseThrow(() -> new EntityNotFoundException("Assignment not found with id: " + assignmentId));

        course.getAssignments().remove(assignment);
        courseRepository.save(course);
        assignmentRepository.deleteByIdAndCourseId(assignmentId, courseId);
    }

    /**
     * Retrieves a specific assignment from a course.
     * Checks if the current authenticated user is an instructor for the course.
     * If the user is an instructor, it returns the assignment.
     *
     * @param assignmentId the id of the assignment
     * @param courseId     the id of the course
     * @return the assignment
     * @throws EntityNotFoundException if the assignment is not found
     */
    public Assignment getAssignmentByIdAndCourseId(Long assignmentId, Long courseId) {
        return assignmentRepository.findByIdAndCourseId(assignmentId, courseId)
                .orElseThrow(() -> new EntityNotFoundException("Assignment not found with id: " + assignmentId));
    }

    /**
     * Retrieves all assignments for a specific course.
     *
     * @param courseId the id of the course
     * @return a list of assignments
     */
    public List<Assignment> getAllAssignmentsByCourseId(Long courseId) {
        return assignmentRepository.findAllByCourseId(courseId);
    }

    /**
     * Sends a student's answer for an assignment.
     * Checks if the current authenticated user is a student for the course.
     * If the user is a student, it saves the student's answer and sets the grade status to submitted.
     *
     * @param courseId     the id of the course
     * @param assignmentId the id of the assignment
     * @param request      the request object containing the student's answer
     */
    public void sendStudentAnswer(Long courseId, Long assignmentId, AssignmentAnswerRequest request) {
        User user = utilityService.getCurrentUserAuthenticated();
        Course course = courseRepository.findCourseById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + courseId));

        if (!course.getStudents().contains(user)) {
            throw new AuthenticationCredentialsNotFoundException("You are not student for this course");
        }

        Assignment assignment = assignmentRepository.findByIdAndCourseId(assignmentId, courseId)
                .orElseThrow(() -> new EntityNotFoundException("Assignment not found with id: " + assignmentId));

        assignment.setStudentAnswer(request.getStudentAnswer());
        assignment.setGradeStatus(GradeStatus.SUBMITTED);
        course.getAssignments().remove(assignment);
        course.getAssignments().add(assignment);
        courseRepository.save(course);
        assignmentRepository.save(assignment);
    }


}
