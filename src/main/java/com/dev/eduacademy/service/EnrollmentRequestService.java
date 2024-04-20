package com.dev.eduacademy.service;

import com.dev.eduacademy.entity.Course;
import com.dev.eduacademy.entity.EnrollmentRequest;
import com.dev.eduacademy.entity.User;
import com.dev.eduacademy.repository.CourseRepository;
import com.dev.eduacademy.repository.EnrollmentRequestRepository;
import com.dev.eduacademy.repository.UserRepository;
import com.dev.eduacademy.util.EnrollmentStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * The EnrollmentRequestService class is a service layer in the Spring Boot application.
 * It is responsible for handling business logic related to enrollment requests.
 * It interacts with the EnrollmentRequestRepository, UtilityService, CourseRepository, and UserRepository for data access and utility functions.
 */
@Service
@RequiredArgsConstructor
public class EnrollmentRequestService {

    private final UtilityService utilityService;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final EnrollmentRequestRepository enrollmentRequestRepository;

    /**
     * Retrieves all enrollment requests for a specific course.
     * Checks if the current authenticated user is an instructor for the course.
     * If the user is an instructor, it returns the enrollment requests of the course.
     *
     * @param courseId the id of the course
     * @return a list of enrollment requests
     * @throws EntityNotFoundException if the course is not found or the user is not an instructor for the course
     */
    public List<EnrollmentRequest> getEnrollmentRequestsForCourse(Long courseId) {
        User user = utilityService.getCurrentUserAuthenticated();
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Course not found"));
        if (!course.getInstructors().contains(user)) {
            throw new EntityNotFoundException("You are not instructor for this course");
        }
        return enrollmentRequestRepository.findAllByCourseId(courseId);
    }

    /**
     * Retrieves a specific enrollment request from the enrollment requests of a course.
     * Checks if the current authenticated user is an instructor for the course.
     * If the user is an instructor, it returns the enrollment request.
     *
     * @param courseId  the id of the course
     * @param requestId the id of the enrollment request
     * @return the enrollment request
     * @throws EntityNotFoundException if the course is not found, the user is not an instructor for the course, or the enrollment request is not found
     */
    public EnrollmentRequest getEnrollmentRequestForCourse(Long courseId, Long requestId) {
        User user = utilityService.getCurrentUserAuthenticated();
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Course not found"));
        if (!course.getInstructors().contains(user)) {
            throw new EntityNotFoundException("You are not instructor for this course");
        }
        return enrollmentRequestRepository.findAllByCourseId(courseId).stream()
                .filter(enrollmentRequest -> enrollmentRequest.getId().equals(requestId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Enrollment request not found in this course"));
    }

    /**
     * Approves an enrollment request for a course.
     * Checks if the current authenticated user is an instructor for the course.
     * If the user is an instructor, it approves the enrollment request and adds the student to the course.
     *
     * @param courseId  the id of the course
     * @param requestId the id of the enrollment request
     * @throws EntityNotFoundException if the course is not found, the user is not an instructor for the course, or the enrollment request is not found
     */
    public void approveEnrollmentRequest(Long courseId, Long requestId) {
        User user = utilityService.getCurrentUserAuthenticated();
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Course not found"));
        if (!course.getInstructors().contains(user)) {
            throw new EntityNotFoundException("You are not instructor for this course");
        }
        EnrollmentRequest enrollmentRequest = enrollmentRequestRepository.findByIdAndCourseId(requestId, courseId).orElseThrow(() -> new EntityNotFoundException("Enrollment request not found in this course"));

        course.getStudents().add(enrollmentRequest.getUser());
        enrollmentRequest.getUser().getEnrolledCourses().add(course);
        course.getEnrollmentRequests().remove(enrollmentRequest);
        enrollmentRequest.getUser().getEnrollmentRequests().remove(enrollmentRequest);
        courseRepository.save(course);
        userRepository.save(enrollmentRequest.getUser());
        enrollmentRequestRepository.delete(enrollmentRequest);
    }

    /**
     * Rejects an enrollment request for a course.
     * Checks if the current authenticated user is an instructor for the course.
     * If the user is an instructor, it rejects the enrollment request.
     *
     * @param courseId  the id of the course
     * @param requestId the id of the enrollment request
     * @throws EntityNotFoundException if the course is not found, the user is not an instructor for the course, or the enrollment request is not found
     */
    public void rejectEnrollmentRequest(Long courseId, Long requestId) {
        User user = utilityService.getCurrentUserAuthenticated();
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Course not found"));
        if (!course.getInstructors().contains(user)) {
            throw new EntityNotFoundException("You are not instructor for this course");
        }
        EnrollmentRequest enrollmentRequest = enrollmentRequestRepository.findByIdAndCourseId(requestId, courseId).orElseThrow(() -> new EntityNotFoundException("Enrollment request not found in this course"));

        course.getEnrollmentRequests().remove(enrollmentRequest);
        enrollmentRequest.getUser().getEnrollmentRequests().remove(enrollmentRequest);
        courseRepository.save(course);
        userRepository.save(enrollmentRequest.getUser());
        enrollmentRequestRepository.delete(enrollmentRequest);
    }

    /**
     * Retrieves all enrollment requests for the current authenticated student.
     *
     * @return a set of enrollment requests
     */
    public Set<EnrollmentRequest> getStudentEnrollmentRequests() {
        User user = utilityService.getCurrentUserAuthenticated();
        return user.getEnrollmentRequests();
    }

    /**
     * Retrieves a specific enrollment request for the current authenticated student.
     *
     * @param requestId the id of the enrollment request
     * @return the enrollment request
     * @throws EntityNotFoundException if the enrollment request is not found
     */
    public EnrollmentRequest getStudentEnrollmentRequest(Long requestId) {
        User user = utilityService.getCurrentUserAuthenticated();
        return user.getEnrollmentRequests().stream()
                .filter(enrollmentRequest -> enrollmentRequest.getId().equals(requestId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Enrollment request not found"));
    }

    /**
     * Sends an enrollment request for a course by the current authenticated student.
     *
     * @param courseId the id of the course
     * @throws EntityNotFoundException if the course is not found, the student is already enrolled in the course, or the student has already sent an enrollment request for the course
     */
    public void sendEnrollmentRequestForStudent(Long courseId) {
        User user = utilityService.getCurrentUserAuthenticated();
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Course not found"));

        if (user.getEnrolledCourses().contains(course)) {
            throw new EntityNotFoundException("You are already enrolled in this course");
        }

        if (user.getEnrollmentRequests().stream().anyMatch(request -> request.getCourse().getId().equals(courseId))) {
            throw new EntityNotFoundException("You have already sent an enrollment request for this course");
        }

        EnrollmentRequest enrollmentRequest = EnrollmentRequest.builder()
                .status(EnrollmentStatus.PENDING)
                .user(user)
                .course(course)
                .build();
        user.getEnrollmentRequests().add(enrollmentRequest);
        userRepository.save(user);
        enrollmentRequestRepository.save(enrollmentRequest);
    }

    /**
     * Cancels an enrollment request for a course by the current authenticated student.
     *
     * @param courseId the id of the course
     * @throws EntityNotFoundException if the student has not sent an enrollment request for the course
     */
    public void cancelEnrollmentRequestForStudent(Long courseId) {
        User user = utilityService.getCurrentUserAuthenticated();
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Course not found"));

        if (user.getEnrollmentRequests().stream().noneMatch(request -> request.getCourse().getId().equals(courseId))) {
            throw new EntityNotFoundException("You have not sent an enrollment request for this course");
        }

        EnrollmentRequest enrollmentRequest = user.getEnrollmentRequests().stream()
                .filter(request -> request.getCourse().getId().equals(courseId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Enrollment request not found"));

        user.getEnrollmentRequests().remove(enrollmentRequest);
        userRepository.save(user);
        enrollmentRequestRepository.delete(enrollmentRequest);
    }

}
