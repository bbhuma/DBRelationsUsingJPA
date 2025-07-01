package com.example.spring_data_jpa_complex_object.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    // Checks if the authenticated user is the teacher of the student
    public boolean isTeacherOfStudent(Authentication authentication, Long studentId) {
        // Implement logic to check if the teacher (authentication.getName()) teaches the studentId
        // Example: query the database for teacher-student relationship
        return true; // TODO: Replace with real check
    }

    // Checks if the authenticated user is the student themselves
    public boolean isCurrentStudent(Authentication authentication, Long studentId) {
        // Implement logic to check if the student (authentication.getName()) matches studentId
        return true; // TODO: Replace with real check
    }
}
