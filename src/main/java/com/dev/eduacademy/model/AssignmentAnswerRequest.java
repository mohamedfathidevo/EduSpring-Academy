package com.dev.eduacademy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AssignmentAnswerRequest is a model class that represents the request to submit an answer to an assignment.
 * It contains the student's answer.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssignmentAnswerRequest {
    private String studentAnswer;
}
