package com.dev.eduacademy.repository;

import com.dev.eduacademy.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * LessonRepository is an interface that acts as a Spring Data JPA repository for Lesson entities.
 * It extends JpaRepository which provides methods for CRUD operations, sorting, and pagination.
 * It has methods to find a lesson by id and course id, delete a lesson by id and course id, and find all lessons by course id.
 */
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    /**
     * Finds a lesson by its id and the id of the course it belongs to.
     *
     * @param id       the id of the lesson
     * @param courseId the id of the course the lesson belongs to
     * @return an Optional of Lesson, which can be empty if no lesson is found with the given id and course id
     */
    Optional<Lesson> findByIdAndCourseId(Long id, Long courseId);

    /**
     * Deletes a lesson by its id and the id of the course it belongs to.
     *
     * @param id       the id of the lesson
     * @param courseId the id of the course the lesson belongs to
     */
    void deleteByIdAndCourseId(Long id, Long courseId);

    /**
     * Finds all lessons by the id of the course they belong to.
     *
     * @param courseId the id of the course
     * @return a list of lessons belonging to the course with the specified id
     */
    List<Lesson> findAllByCourseId(Long courseId);

}