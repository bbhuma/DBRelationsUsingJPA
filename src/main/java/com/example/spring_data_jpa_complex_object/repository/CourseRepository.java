package com.example.spring_data_jpa_complex_object.repository;

import com.example.spring_data_jpa_complex_object.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {}