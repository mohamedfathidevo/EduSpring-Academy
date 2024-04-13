package com.dev.eduacademy.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum Permission {
    ADMIN_GET_COURSE("admin_get_course"),
    ADMIN_GET_ALL_COURSES("admin_get_all_courses"),
    ADMIN_HIDE_COURSE("admin_hide_course"),
    ADMIN_PUBLISH_COURSE("admin_publish_course"),
    ADMIN_GET_STUDENT("admin_get_student"),
    ADMIN_GET_INSTRUCTOR("admin_get_instructor"),
    ADMIN_GET_ALL_INSTRUCTORS("admin_get_all_instructors"),
    STUDENT_CANCEL_ENROLLMENT_REQUEST("student_cancel_enrollment_request"),
    STUDENT_ADD_COURSE_REVIEW("student_add_course_review"),
    STUDENT_DELETE_COURSE_REVIEW("student_delete_course_review"),
    STUDENT_EDIT_COURSE_REVIEW("student_edit_course_review"),
    STUDENT_GET_ALL_ENROLLMENT_COURSES("student_get_all_enrollment_courses"),
    STUDENT_SEND_ENROLLMENT_REQUEST("student_send_enrollment_request"),
    STUDENT_SUBMIT_ASSIGNMENT_ANSWER("student_submit_assignment_answer"),
    INSTRUCTOR_ADD_COURSE("instructor_add_course"),
    INSTRUCTOR_EDIT_COURSE("instructor_edit_course"),
    INSTRUCTOR_GET_MY_COURSE("instructor_get_my_course"),
    INSTRUCTOR_DELETE_COURSE("instructor_delete_course"),
    INSTRUCTOR_ADD_COURSE_LESSON("instructor_add_course_lesson"),
    INSTRUCTOR_ACCEPT_ENROLLMENT_REQUEST("instructor_accept_enrollment_request"),
    INSTRUCTOR_ADD_COURSE_ASSIGNMENT("instructor_add_course_assignment"),
    INSTRUCTOR_DELETE_COURSE_ASSIGNMENT("instructor_delete_course_assignment"),
    INSTRUCTOR_DELETE_COURSE_LESSON("instructor_delete_course_lesson"),
    INSTRUCTOR_EDIT_COURSE_ASSIGNMENT("instructor_edit_course_assignment"),
    INSTRUCTOR_EDIT_COURSE_LESSON("instructor_edit_course_lesson"),
    INSTRUCTOR_GET_ALL_MY_COURSES("instructor_get_all_my_courses");

    private final String permission;
}
