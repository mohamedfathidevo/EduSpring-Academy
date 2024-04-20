package com.dev.eduacademy.service;


import com.dev.eduacademy.entity.Course;
import com.dev.eduacademy.entity.Lesson;
import com.dev.eduacademy.entity.User;
import com.dev.eduacademy.model.LessonRequest;
import com.dev.eduacademy.repository.CourseRepository;
import com.dev.eduacademy.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The LessonService class is a service layer in the Spring Boot application.
 * It is responsible for handling business logic related to lessons.
 * It interacts with the LessonRepository and CourseRepository for data access, and the UtilityService for utility functions.
 */
@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private final UtilityService utilityService;

    /**
     * Adds a new lesson to a course.
     * Checks if the current authenticated user is an instructor for the course.
     * If the user is an instructor, it creates a new lesson and adds it to the course.
     *
     * @param courseId      the id of the course
     * @param lessonRequest the request object containing lesson details
     */
    public void addLesson(Long courseId, LessonRequest lessonRequest) {
        User user = utilityService.getCurrentUserAuthenticated();
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new AuthenticationCredentialsNotFoundException("Course not found"));

        if (!course.getInstructors().contains(user)) {
            throw new AuthenticationCredentialsNotFoundException("You are not instructor for this course");
        }

        Lesson lesson = Lesson.builder()
                .title(lessonRequest.getTitle())
                .content(lessonRequest.getContent())
                .course(course)
                .build();
        course.getLessons().add(lesson);
        courseRepository.save(course);
        lessonRepository.save(lesson);
    }

    /**
     * Updates an existing lesson in a course.
     * Checks if the current authenticated user is an instructor for the course.
     * If the user is an instructor, it updates the lesson details and saves it.
     *
     * @param courseId      the id of the course
     * @param lessonId      the id of the lesson
     * @param lessonRequest the request object containing updated lesson details
     */
    public void updateLesson(Long courseId, Long lessonId, LessonRequest lessonRequest) {
        User user = utilityService.getCurrentUserAuthenticated();
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new AuthenticationCredentialsNotFoundException("Course not found"));
        Lesson lesson = lessonRepository.findByIdAndCourseId(lessonId, courseId).orElseThrow(() -> new AuthenticationCredentialsNotFoundException("Lesson not found"));

        if (!course.getInstructors().contains(user)) {
            throw new AuthenticationCredentialsNotFoundException("You are not instructor for this course");
        }

        lesson.setTitle(lessonRequest.getTitle());
        lesson.setContent(lessonRequest.getContent());
        course.getLessons().remove(lesson);
        course.getLessons().add(lesson);
        courseRepository.save(course);
        lessonRepository.save(lesson);
    }

    /**
     * Deletes a lesson from a course.
     * Checks if the current authenticated user is an instructor for the course.
     * If the user is an instructor, it removes the lesson from the course and deletes it.
     *
     * @param lessonId the id of the lesson
     * @param courseId the id of the course
     */
    public void deleteLesson(Long lessonId, Long courseId) {
        User user = utilityService.getCurrentUserAuthenticated();
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new AuthenticationCredentialsNotFoundException("Course not found"));
        Lesson lesson = lessonRepository.findByIdAndCourseId(lessonId, courseId).orElseThrow(() -> new AuthenticationCredentialsNotFoundException("Lesson not found"));

        if (!course.getInstructors().contains(user)) {
            throw new AuthenticationCredentialsNotFoundException("You are not instructor for this course");
        }

        course.getLessons().remove(lesson);
        courseRepository.save(course);
        lessonRepository.deleteByIdAndCourseId(lessonId, courseId);
    }

    /**
     * Retrieves all lessons for a specific course.
     *
     * @param courseId the id of the course
     * @return a list of lessons
     */
    public List<Lesson> getAllLessonsByCourseId(Long courseId) {
        return lessonRepository.findAllByCourseId(courseId);
    }

    /**
     * Retrieves a specific lesson by its id and the id of its course.
     * If the lesson does not exist, an exception is thrown.
     *
     * @param lessonId the id of the lesson
     * @param courseId the id of the course
     * @return the lesson
     */
    public Lesson getLessonByIdAndCourseId(Long lessonId, Long courseId) {
        return lessonRepository.findByIdAndCourseId(lessonId, courseId).orElseThrow(() -> new AuthenticationCredentialsNotFoundException("Lesson not found"));
    }


}
