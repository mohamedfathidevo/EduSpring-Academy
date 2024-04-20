package com.dev.eduacademy.repository;

import com.dev.eduacademy.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

/**
 * CourseRepository is an interface that acts as a Spring Data JPA repository for Course entities.
 * It extends JpaRepository which provides methods for CRUD operations, sorting, and pagination.
 * It has methods to find a course by id, find all published and unpublished courses, and find a published course by id.
 */
public interface CourseRepository extends JpaRepository<Course, Long> {

    /**
     * Finds a course by its id.
     *
     * @param id the id of the course
     * @return an Optional of Course, which can be empty if no course is found with the given id
     */
    Optional<Course> findCourseById(Long id);

    /**
     * Finds all published courses.
     *
     * @return a Set of Courses that are published
     */
    Set<Course> findByIsPublishedTrue();

    /**
     * Finds all unpublished courses.
     *
     * @return a Set of Courses that are not published
     */
    Set<Course> findByIsPublishedFalse();

    /**
     * Finds a published course by its id.
     *
     * @param id the id of the course
     * @return an Optional of Course, which can be empty if no published course is found with the given id
     */
    Optional<Course> findCourseByIdAndIsPublishedTrue(Long id);
}