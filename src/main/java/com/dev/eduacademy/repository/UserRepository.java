package com.dev.eduacademy.repository;

import com.dev.eduacademy.entity.User;
import com.dev.eduacademy.util.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

/**
 * UserRepository is an interface that acts as a Spring Data JPA repository for User entities.
 * It extends JpaRepository which provides methods for CRUD operations, sorting, and pagination.
 * It has methods to find a user by email and to find all users by role.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their email.
     *
     * @param email the email of the user
     * @return an Optional of User, which can be empty if no user is found with the given email
     */
    Optional<User> findByEmail(String email);

    /**
     * Finds all users with a specific role.
     *
     * @param role the role of the users
     * @return a set of users with the specified role
     */
    @Query("SELECT u FROM User u WHERE u.role = ?1")
    Set<User> findAllByRole(Role role);
}