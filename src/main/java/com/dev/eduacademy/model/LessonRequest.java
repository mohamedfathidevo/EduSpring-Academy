package com.dev.eduacademy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * LessonRequest is a model class that represents the request to create a new lesson.
 * It contains the lesson's title and content.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonRequest {
    private String title;
    private String content;
}
