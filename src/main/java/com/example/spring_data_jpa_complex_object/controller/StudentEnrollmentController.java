package com.example.spring_data_jpa_complex_object.controller;

import com.example.spring_data_jpa_complex_object.entity.Course;
import com.example.spring_data_jpa_complex_object.entity.Enrollment;
import com.example.spring_data_jpa_complex_object.entity.Student;
import com.example.spring_data_jpa_complex_object.repository.CourseRepository;
import com.example.spring_data_jpa_complex_object.repository.EnrollmentRepository;
import com.example.spring_data_jpa_complex_object.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StudentEnrollmentController {

    private final StudentRepository studentRepo;
    private final CourseRepository courseRepo;
    private final EnrollmentRepository enrollmentRepo;

    @PostMapping("/students")
    public Student createStudent(@RequestBody Student student) {
        return studentRepo.save(student);
    }

    @PostMapping("/courses")
    public Course createCourse(@RequestBody Course course) {
        return courseRepo.save(course);
    }

    //Enroll a student into new course, studentId and courseId
    @PostMapping("/enroll")
    public Enrollment enrollStudent(
            @RequestParam Long studentId,
            @RequestParam Long courseId,
            @RequestParam String grade
    ) {

        Student student = studentRepo.findById(studentId).orElseThrow();
        Course course = courseRepo.findById(courseId).orElseThrow();

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrollmentDate(LocalDate.now());
        enrollment.setGrade(grade);

        return enrollmentRepo.save(enrollment);
    }

    @GetMapping("/students/{id}/enrollments")
    public List<Enrollment> getStudentsEnrollments(@PathVariable Long id) {
        return enrollmentRepo.findByStudentId(id);
    }
    @GetMapping("/courses/{id}/enrollments")
    public List<Enrollment> getCourseEnrollments(@PathVariable Long id) {

        return enrollmentRepo.findByCourseId(id);
    }
    @GetMapping("/enrollments/{id}")
    public Optional<Enrollment> getEnrollmentById(@PathVariable Long id) {
        return enrollmentRepo.findById(id);
    }
    @GetMapping("/enrollments")
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepo.findAll();
    }


}

