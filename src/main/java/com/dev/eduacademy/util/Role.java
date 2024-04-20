package com.dev.eduacademy.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;

/**
 * Represents the roles of the system.
 */
@RequiredArgsConstructor
public enum Role {
    ADMIN(
            Set.of(
                    Permission.ADMIN_GET_COURSE,
                    Permission.ADMIN_GET_ALL_COURSES,
                    Permission.ADMIN_HIDE_COURSE,
                    Permission.ADMIN_PUBLISH_COURSE,
                    Permission.ADMIN_GET_STUDENT,
                    Permission.ADMIN_GET_INSTRUCTOR,
                    Permission.ADMIN_GET_ALL_INSTRUCTORS
            )
    ),
    STUDENT(
            Set.of(
                    Permission.STUDENT_CANCEL_ENROLLMENT_REQUEST,
                    Permission.STUDENT_ADD_COURSE_REVIEW,
                    Permission.STUDENT_DELETE_COURSE_REVIEW,
                    Permission.STUDENT_EDIT_COURSE_REVIEW,
                    Permission.STUDENT_GET_ALL_ENROLLMENT_COURSES,
                    Permission.STUDENT_SEND_ENROLLMENT_REQUEST,
                    Permission.STUDENT_SUBMIT_ASSIGNMENT_ANSWER
            )
    ),
    INSTRUCTOR(
            Set.of(
                    Permission.INSTRUCTOR_ADD_COURSE,
                    Permission.INSTRUCTOR_EDIT_COURSE,
                    Permission.INSTRUCTOR_GET_MY_COURSE,
                    Permission.INSTRUCTOR_DELETE_COURSE,
                    Permission.INSTRUCTOR_ADD_COURSE_LESSON,
                    Permission.INSTRUCTOR_ACCEPT_ENROLLMENT_REQUEST,
                    Permission.INSTRUCTOR_ADD_COURSE_ASSIGNMENT,
                    Permission.INSTRUCTOR_DELETE_COURSE_ASSIGNMENT,
                    Permission.INSTRUCTOR_DELETE_COURSE_LESSON,
                    Permission.INSTRUCTOR_EDIT_COURSE_ASSIGNMENT,
                    Permission.INSTRUCTOR_EDIT_COURSE_LESSON,
                    Permission.INSTRUCTOR_GET_ALL_MY_COURSES
            )
    );


    @Getter
    private final Set<Permission> permissions;

    /**
     * Returns the authorities of the role.
     *
     * @return the authorities of the role
     */
    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = new java.util.ArrayList<>(permissions
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }

}
