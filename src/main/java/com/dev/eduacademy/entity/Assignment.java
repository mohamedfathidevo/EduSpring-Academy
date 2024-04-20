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
 * Assignment is an entity class that represents an assignment in the database.
 * It contains the assignment's title, content, start date, due date, student answer, grade status, course, and grades.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "assignment")
public class Assignment {
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

    @OneToMany(mappedBy = "assignment")
    @JsonBackReference
    private List<Grade> grades;
}