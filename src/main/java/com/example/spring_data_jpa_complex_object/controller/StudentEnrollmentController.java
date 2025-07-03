package com.example.spring_data_jpa_complex_object.controller;

import com.example.spring_data_jpa_complex_object.entity.Course;
import com.example.spring_data_jpa_complex_object.entity.Enrollment;
import com.example.spring_data_jpa_complex_object.entity.Student;
import com.example.spring_data_jpa_complex_object.entity.Teacher;
import com.example.spring_data_jpa_complex_object.entity.Classroom;
import com.example.spring_data_jpa_complex_object.repository.CourseRepository;
import com.example.spring_data_jpa_complex_object.repository.EnrollmentRepository;
import com.example.spring_data_jpa_complex_object.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StudentEnrollmentController {

    private final StudentRepository studentRepo;
    private final CourseRepository courseRepo;
    private final EnrollmentRepository enrollmentRepo;

    @Autowired
    private com.example.spring_data_jpa_complex_object.repository.TeacherRepository teacherRepo;
    @Autowired
    private com.example.spring_data_jpa_complex_object.repository.ClassroomRepository classroomRepo;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/students")
    public Student createStudent(@RequestBody Student student) {
        return studentRepo.save(student);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/students/{id}")
    public Optional<Student> getStudents(@PathVariable Long id) {
        return studentRepo.findById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping ("/students/{id}")
    public Student updateStudents(@PathVariable Long id,@RequestBody Student student) {
        Student student1 = studentRepo.findById(id).orElseThrow(()->  new RuntimeException("Student with Id"+ id +"not found"));
        student1.setId(id);
        student1.setName(student.getName());

        return studentRepo.save(student1);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping ("/students/{id}")
    public ResponseEntity<Void> deleteStudents(@PathVariable Long id) {
        studentRepo.deleteById(id);
         return ResponseEntity.noContent().build();
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/courses/{id}")
    public Optional<Course> getCourseById(@PathVariable Long id) {
        return courseRepo.findById(id);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/courses")
    public List<Course> getAllCourses(@RequestBody Course course) {
        return courseRepo.findAll();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/courses")
    public Course createCourse(@RequestBody Course course) {
        return courseRepo.save(course);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping ("/courses/{id}")
    public Course updateCourse(@PathVariable Long id,@RequestBody Course course) {
        Course course1 = courseRepo.findById(id).orElseThrow(()->  new RuntimeException("Student with Id"+ id +"not found"));
        course1.setId(id);
        course1.setTitle(course.getTitle());
        return courseRepo.save(course1);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping ("/courses/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/enroll-student")
    public Enrollment enrollStudent(
            @RequestParam Long studentId,
            @RequestParam Long courseId,
            @RequestParam Long teacherId,
            @RequestParam Long classroomId
    ) {
        Student student = studentRepo.findById(studentId).orElseThrow();
        Course course = courseRepo.findById(courseId).orElseThrow();
        Teacher teacher = teacherRepo.findById(teacherId).orElseThrow();
        Classroom classroom = classroomRepo.findById(classroomId).orElseThrow();
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setTeacher(teacher);
        enrollment.setClassroom(classroom);
        enrollment.setMarks(null); // No marks at enrollment
        return enrollmentRepo.save(enrollment);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/enrollments/{enrollmentId}/marks")
    public Enrollment updateEnrollmentMarks(@PathVariable Long enrollmentId, @RequestParam Long marks) {
        Enrollment enrollment = enrollmentRepo.findById(enrollmentId).orElseThrow();
        enrollment.setMarks(marks);
        return enrollmentRepo.save(enrollment);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/enrollments/{id}")
    public Optional<Enrollment> getEnrollmentById(@PathVariable Long id) {
        return enrollmentRepo.findById(id);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/enrollments")
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepo.findAll();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/studentsdb/cleanup")
    @Transactional
    public ResponseEntity<String> cleanDatabase() {
        // Delete child tables first to avoid FK constraint violations
        enrollmentRepo.deleteAll();
        courseRepo.deleteAll();
        studentRepo.deleteAll();
        return ResponseEntity.ok("Database cleaned.");
    }

}

