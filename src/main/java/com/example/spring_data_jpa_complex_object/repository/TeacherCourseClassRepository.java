package com.example.spring_data_jpa_complex_object.repository;

import com.example.spring_data_jpa_complex_object.entity.TeacherCourseClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherCourseClassRepository extends JpaRepository<TeacherCourseClass, Long> {
    Optional<TeacherCourseClass> findByTeacherIdAndCourseIdAndClassroomId(Long teacherId, Long courseId, Long classroomId);
}
