package com.example.spring_data_jpa_complex_object.repository;

import com.example.spring_data_jpa_complex_object.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {}