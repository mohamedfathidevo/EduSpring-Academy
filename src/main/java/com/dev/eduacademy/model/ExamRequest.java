package com.dev.eduacademy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ExamRequest is a model class that represents the request to create a new exam.
 * It contains the exam's title and content.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamRequest {
    private String title;
    private String content;
}
