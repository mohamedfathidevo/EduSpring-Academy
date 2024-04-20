package com.dev.eduacademy.repository;

import com.dev.eduacademy.entity.EnrollmentRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * EnrollmentRequestRepository is an interface that acts as a Spring Data JPA repository for EnrollmentRequest entities.
 * It extends JpaRepository which provides methods for CRUD operations, sorting, and pagination.
 * It has methods to find an enrollment request by id and course id, and find all enrollment requests by course id.
 */
public interface EnrollmentRequestRepository extends JpaRepository<EnrollmentRequest, Long> {

    /**
     * Finds an enrollment request by its id and the id of the course it belongs to.
     *
     * @param id       the id of the enrollment request
     * @param courseId the id of the course the enrollment request belongs to
     * @return an Optional of EnrollmentRequest, which can be empty if no enrollment request is found with the given id and course id
     */
    Optional<EnrollmentRequest> findByIdAndCourseId(Long id, Long courseId);

    /**
     * Finds all enrollment requests by the id of the course they belong to.
     *
     * @param courseId the id of the course
     * @return a list of enrollment requests belonging to the course with the specified id
     */
    List<EnrollmentRequest> findAllByCourseId(Long courseId);
}