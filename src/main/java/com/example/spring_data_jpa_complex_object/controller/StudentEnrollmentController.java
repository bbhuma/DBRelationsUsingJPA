package com.example.spring_data_jpa_complex_object.controller;

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
    public List<Enrollment> getEnrollments(@PathVariable Long id) {
        return enrollmentRepo.findByStudentId(id);
    }

    @GetMapping("/enrollments/{id}/students")
    public List<Enrollment> getEnrollments(@PathVariable Long id) {
        return enrollmentRepo.findBy
    }
}

