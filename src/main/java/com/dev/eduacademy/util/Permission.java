package com.dev.eduacademy.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {

    // admin permissions
    ADMIN_PUBLISH_COURSE("admin:add_publish_course"),
    ADMIN_HIDE_COURSE("admin:hide_course"),
    ADMIN_GET_ALL_COURSES("admin:get_all_course"),
    ADMIN_GET_COURSE("admin:get_course"),
    ADMIN_GET_ALL_STUDENTS("admin:get_all_students"),
    ADMIN_GET_STUDENT("admin:get_student"),
    ADMIN_GET_ALL_INSTRUCTORS("admin:get_all_instructors"),
    ADMIN_GET_INSTRUCTOR("admin:get_instructor"),

    // instructor permissions
    INSTRUCTOR_ADD_COURSE("instructor:add_course"),
    INSTRUCTOR_ADD_COURSE_LESSON("instructor:add_course_lesson"),
    INSTRUCTOR_ADD_COURSE_ASSIGNMENT("instructor:add_course_assignment"),
    INSTRUCTOR_EDIT_COURSE("instructor:edit_course"),
    INSTRUCTOR_EDIT_COURSE_LESSON("instructor:edit_course_lesson"),
    INSTRUCTOR_EDIT_COURSE_ASSIGNMENT("instructor:edit_course_assignment"),
    INSTRUCTOR_DELETE_COURSE("instructor:delete_course"),
    INSTRUCTOR_DELETE_COURSE_LESSON("instructor:delete_course_lesson"),
    INSTRUCTOR_DELETE_COURSE_ASSIGNMENT("instructor:delete_course_assignment"),
    INSTRUCTOR_GET_ALL_MY_COURSES("instructor:get_all_my_courses"),
    INSTRUCTOR_GET_MY_COURSE("instructor:get_course"),
    INSTRUCTOR_ACCEPT_ENROLLMENT_REQUEST("instructor:accept_enrollment_request"),
    INSTRUCTOR_REJECT_ENROLLMENT_REQUEST("instructor:reject_enrollment_request"),

    // student permissions
    STUDENT_SEND_ENROLLMENT_REQUEST("student:send_enrollment_request"),
    STUDENT_CANCEL_ENROLLMENT_REQUEST("student:cancel_enrollment_request"),
    STUDENT_ADD_COURSE_REVIEW("student:add_course_review"),
    STUDENT_EDIT_COURSE_REVIEW("student:edit_course_review"),
    STUDENT_DELETE_COURSE_REVIEW("student:delete_course_review"),
    STUDENT_SUBMIT_ASSIGNMENT_ANSWER("student:submit_assignment_answer"),
    STUDENT_DELETE_ASSIGNMENT_ANSWER("student:delete_assignment_answer"),
    STUDENT_GET_ALL_ENROLLMENT_COURSES("student:get_all_enrollment_courses"),
    ;

    private final String permission;
}
