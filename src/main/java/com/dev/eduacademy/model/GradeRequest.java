package com.dev.eduacademy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * GradeRequest is a model class that represents the request to create a new grade.
 * It contains the grade's score.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GradeRequest {
    private Integer score;
}
