package com.dev.eduacademy.repository;

import com.dev.eduacademy.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * GradeRepository is an interface that acts as a Spring Data JPA repository for Grade entities.
 * It extends JpaRepository which provides methods for CRUD operations, sorting, and pagination.
 * It has methods to find a grade by assignment id and grade id, find a grade by exam id and grade id,
 * find all grades by course id and assignment id, find all grades by course id and exam id, and find all grades by course id.
 */
public interface GradeRepository extends JpaRepository<Grade, Long> {

    /**
     * Finds a grade by the id of the assignment it belongs to and the id of the grade.
     *
     * @param assignmentId the id of the assignment
     * @param gradeId      the id of the grade
     * @return an Optional of Grade, which can be empty if no grade is found with the given assignment id and grade id
     */
    Optional<Grade> findByAssignmentIdAndId(Long assignmentId, Long gradeId);

    /**
     * Finds a grade by the id of the exam it belongs to and the id of the grade.
     *
     * @param examId  the id of the exam
     * @param gradeId the id of the grade
     * @return an Optional of Grade, which can be empty if no grade is found with the given exam id and grade id
     */
    Optional<Grade> findByExamIdAndId(Long examId, Long gradeId);

    /**
     * Finds all grades by the id of the course they belong to and the id of the assignment they belong to.
     *
     * @param courseId     the id of the course
     * @param assignmentId the id of the assignment
     * @return a list of grades belonging to the course with the specified id and the assignment with the specified id
     */
    List<Grade> findAllByCourseIdAndAssignmentId(Long courseId, Long assignmentId);

    /**
     * Finds all grades by the id of the course they belong to and the id of the exam they belong to.
     *
     * @param courseId the id of the course
     * @param examId   the id of the exam
     * @return a list of grades belonging to the course with the specified id and the exam with the specified id
     */
    List<Grade> findAllByCourseIdAndExamId(Long courseId, Long examId);

    /**
     * Finds all grades by the id of the course they belong to.
     *
     * @param courseId the id of the course
     * @return a list of grades belonging to the course with the specified id
     */
    List<Grade> findAllByCourseId(Long courseId);

}
