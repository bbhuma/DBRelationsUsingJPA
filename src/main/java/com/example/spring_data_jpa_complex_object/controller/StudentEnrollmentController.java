package com.example.spring_data_jpa_complex_object.controller;

import com.example.spring_data_jpa_complex_object.entity.Course;
import com.example.spring_data_jpa_complex_object.entity.Enrollment;
import com.example.spring_data_jpa_complex_object.entity.Student;
import com.example.spring_data_jpa_complex_object.entity.TeacherCourseClass;
import com.example.spring_data_jpa_complex_object.repository.CourseRepository;
import com.example.spring_data_jpa_complex_object.repository.EnrollmentRepository;
import com.example.spring_data_jpa_complex_object.repository.StudentRepository;
import com.example.spring_data_jpa_complex_object.repository.TeacherCourseClassRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    private final TeacherCourseClassRepository teacherCourseClassRepo;

    @PostMapping("/students")
    public Student createStudent(@RequestBody Student student) {
        return studentRepo.save(student);
    }

    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }
    @GetMapping("/students/{id}")
    public Optional<Student> getStudents(@PathVariable Long id) {
        return studentRepo.findById(id);
    }

    @PutMapping ("/students/{id}")
    public Student updateStudents(@PathVariable Long id,@RequestBody Student student) {
        Student student1 = studentRepo.findById(id).orElseThrow(()->  new RuntimeException("Student with Id"+ id +"not found"));
        student1.getEnrollments().clear();
        student1.setId(id);
        student1.setName(student.getName());

        return studentRepo.save(student1);
    }

    @DeleteMapping ("/students/{id}")
    public ResponseEntity<Void> deleteStudents(@PathVariable Long id) {
        studentRepo.deleteById(id);
         return ResponseEntity.noContent().build();
    }
    @GetMapping("/courses/{id}")
    public Optional<Course> getCourseById(@PathVariable Long id) {
        return courseRepo.findById(id);
    }

    @GetMapping("/courses")
    public List<Course> getAllCourses(@RequestBody Course course) {
        return courseRepo.findAll();
    }

    @PostMapping("/courses")
    public Course createCourse(@RequestBody Course course) {
        return courseRepo.save(course);
    }

    @PutMapping ("/courses/{id}")
    public Course updateCourse(@PathVariable Long id,@RequestBody Course course) {
        Course course1 = courseRepo.findById(id).orElseThrow(()->  new RuntimeException("Student with Id"+ id +"not found"));
        course1.getEnrollments().clear();
        course1.setId(id);
        course1.setTitle(course.getTitle());

        return courseRepo.save(course1);
    }

    @DeleteMapping ("/courses/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    // Enroll a student in a course with a specific teacher (no marks at this stage)
    @PostMapping("/enroll-student")
    public Enrollment enrollStudentWithTeacher(
            @RequestParam Long studentId,
            @RequestParam Long courseId,
            @RequestParam Long teacherId,
            @RequestParam Long classroomId
    ) {
        Student student = studentRepo.findById(studentId).orElseThrow();
        Course course = courseRepo.findById(courseId).orElseThrow();
        // Find the TeacherCourseClass join entity
        Optional<TeacherCourseClass> tccOpt = teacherCourseClassRepo.findByTeacherIdAndCourseIdAndClassroomId(teacherId, courseId, classroomId);
        if (!tccOpt.isPresent()) throw new RuntimeException("No such teacher-course-class assignment");
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrollmentDate(LocalDate.now());
        enrollment.setMarks(null); // No marks at enrollment
        return enrollmentRepo.save(enrollment);
    }

    // Update marks for a student's enrollment (after exams)
    @PutMapping("/enrollments/{enrollmentId}/marks")
    public Enrollment updateEnrollmentMarks(@PathVariable Long enrollmentId, @RequestParam Long marks) {
        Enrollment enrollment = enrollmentRepo.findById(enrollmentId).orElseThrow();
        enrollment.setMarks(marks);
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

