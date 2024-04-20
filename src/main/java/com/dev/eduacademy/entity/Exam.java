package com.dev.eduacademy.entity;

import com.dev.eduacademy.util.GradeStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Exam is an entity class that represents an exam in the database.
 * It contains the exam's title, content, start date, due date, student answer, grade status, course, and grades.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "exam")
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    @CreationTimestamp
    private LocalDateTime startDate;
    private LocalDateTime dueDate;
    private String studentAnswer;
    @Enumerated(EnumType.STRING)
    private GradeStatus gradeStatus;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @OneToMany(mappedBy = "exam")
    @JsonBackReference
    private List<Grade> grades;
}