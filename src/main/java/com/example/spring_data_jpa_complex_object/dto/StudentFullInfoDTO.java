package com.example.spring_data_jpa_complex_object.dto;

import java.util.List;

public class StudentFullInfoDTO {
    public Long studentId;
    public String studentName;
    public String className;
    public List<CourseInfo> courses;

    public static class CourseInfo {
        public Long courseId;
        public String courseTitle;
        public Integer marks;
        public List<String> teachers;
    }
}
