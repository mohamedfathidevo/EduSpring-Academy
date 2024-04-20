package com.dev.eduacademy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AssignmentRequest is a model class that represents the request to create a new assignment.
 * It contains the assignment's title and content.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentRequest {
    private String title;
    private String content;
}
