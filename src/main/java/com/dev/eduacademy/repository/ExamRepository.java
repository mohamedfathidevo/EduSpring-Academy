package com.dev.eduacademy.repository;

import com.dev.eduacademy.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * ExamRepository is an interface that acts as a Spring Data JPA repository for Exam entities.
 * It extends JpaRepository which provides methods for CRUD operations, sorting, and pagination.
 * It has methods to find an exam by id and course id, and find all exams by course id.
 */
public interface ExamRepository extends JpaRepository<Exam, Long> {

    /**
     * Finds an exam by its id and the id of the course it belongs to.
     *
     * @param id       the id of the exam
     * @param courseId the id of the course the exam belongs to
     * @return an Optional of Exam, which can be empty if no exam is found with the given id and course id
     */
    Optional<Exam> findByIdAndCourseId(Long id, Long courseId);

    /**
     * Deletes an exam by its id and the id of the course it belongs to.
     *
     * @param id       the id of the exam
     * @param courseId the id of the course the exam belongs to
     */
    void deleteByIdAndCourseId(Long id, Long courseId);

    /**
     * Finds all exams by the id of the course they belong to.
     *
     * @param courseId the id of the course
     * @return a list of exams belonging to the course with the specified id
     */
    List<Exam> findAllByCourseId(Long courseId);
}