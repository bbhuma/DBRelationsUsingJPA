package com.example.spring_data_jpa_complex_object.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "classroom")
    private Set<TeacherCourseClass> teacherCourseClasses;

    @OneToMany(mappedBy = "classroom")
    private Set<Student> students;
}
