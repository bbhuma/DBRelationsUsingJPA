package com.example.spring_data_jpa_complex_object.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name= "Student_Enrollment")
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate enrollmentDate;
    private Long marks;

    @ManyToOne
    @JoinColumn(name = "student_id")
//    @JsonIgnore
    @JsonIgnoreProperties("enrollments")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
//    @JsonIgnore
    @JsonIgnoreProperties("enrollments")
    private Course course ;

}
