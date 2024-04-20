package com.dev.eduacademy.repository;

import com.dev.eduacademy.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * AssignmentRepository is an interface that acts as a Spring Data JPA repository for Assignment entities.
 * It extends JpaRepository which provides methods for CRUD operations, sorting, and pagination.
 * It has methods to find an assignment by id and course id, delete an assignment by id and course id, and find all assignments by course id.
 */
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    /**
     * Finds an assignment by its id and the id of the course it belongs to.
     *
     * @param id       the id of the assignment
     * @param courseId the id of the course the assignment belongs to
     * @return an Optional of Assignment, which can be empty if no assignment is found with the given id and course id
     */
    Optional<Assignment> findByIdAndCourseId(Long id, Long courseId);

    /**
     * Finds all assignments by the id of the course they belong to.
     *
     * @param courseId the id of the course
     * @return a list of assignments belonging to the course with the specified id
     */
    List<Assignment> findAllByCourseId(Long courseId);

    /**
     * Deletes an assignment by its id and the id of the course it belongs to.
     *
     * @param id       the id of the assignment
     * @param courseId the id of the course the assignment belongs to
     */
    void deleteByIdAndCourseId(Long id, Long courseId);
}