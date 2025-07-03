package com.example.spring_data_jpa_complex_object.controller;

import com.example.spring_data_jpa_complex_object.dto.StudentFullInfoDTO;
import com.example.spring_data_jpa_complex_object.entity.*;
import com.example.spring_data_jpa_complex_object.repository.EnrollmentRepository;
import com.example.spring_data_jpa_complex_object.repository.StudentRepository;
import com.example.spring_data_jpa_complex_object.service.SecurityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@Tag(name = "Student APIs", description = "Endpoints for student, class, course, and teacher queries")
@RestController
@RequestMapping("/api/students")
public class StudentInfoController {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    @Autowired
    private SecurityService securityService;

    @Operation(summary = "Get full info for a student", description = "Returns all details for a student, including class, courses, marks, and teachers.")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('TEACHER') and @securityService.isTeacherOfStudent(authentication, #id)) or (hasRole('STUDENT') and @securityService.isCurrentStudent(authentication, #id))")
    @GetMapping("/{id}/full-info")
    public StudentFullInfoDTO getStudentFullInfo(@PathVariable Long id) {
        return studentRepository.findById(id)
            .map(student -> toFullInfo(student, enrollmentRepository.findByStudentId(id)))
            .orElse(null);
    }

    @Operation(summary = "Get full info for all students", description = "Returns a list of all students with their class, courses, marks, and teachers.")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/full-info")
    public List<StudentFullInfoDTO> getAllStudentsFullInfo() {
        return studentRepository.findAll().stream()
            .map(student -> toFullInfo(student, enrollmentRepository.findByStudentId(student.getId())))
            .collect(Collectors.toList());
    }

    @Operation(summary = "Get students by class", description = "Returns all students in a specific class, with their full info.")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('TEACHER') and @securityService.isTeacherOfStudent(authentication, #classId))")
    @GetMapping("/by-class/{classId}")
    public List<StudentFullInfoDTO> getByClass(@PathVariable Long classId) {
        return studentRepository.findAll().stream()
            .filter(s -> s.getClassroom() != null && Objects.equals(s.getClassroom().getId(), classId))
            .map(student -> toFullInfo(student, enrollmentRepository.findByStudentId(student.getId())))
            .collect(Collectors.toList());
    }

    @Operation(summary = "Get students by course", description = "Returns all students enrolled in a specific course, with their full info.")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('TEACHER') and @securityService.isTeacherOfStudent(authentication, #courseId))")
    @GetMapping("/by-course/{courseId}")
    public List<StudentFullInfoDTO> getByCourse(@PathVariable Long courseId) {
        return enrollmentRepository.findByCourseId(courseId).stream()
            .map(Enrollment::getStudent)
            .distinct()
            .map(student -> toFullInfo(student, enrollmentRepository.findByStudentId(student.getId())))
            .collect(Collectors.toList());
    }

    @Operation(summary = "Get students by teacher", description = "Returns all students taught by a specific teacher (in any course/class), with their full info.")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('TEACHER') and @securityService.isTeacherOfStudent(authentication, #teacherId))")
    @GetMapping("/by-teacher/{teacherId}")
    public List<StudentFullInfoDTO> getByTeacher(@PathVariable Long teacherId) {
        return enrollmentRepository.findAll().stream()
            .filter(e -> e.getTeacher() != null && e.getTeacher().getId().equals(teacherId))
            .map(Enrollment::getStudent)
            .distinct()
            .map(student -> toFullInfo(student, enrollmentRepository.findByStudentId(student.getId())))
            .collect(Collectors.toList());
    }

    @Operation(summary = "Get students by class, course, and teacher", description = "Returns all students in a specific class, for a specific course, taught by a specific teacher, with their full info.")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('TEACHER') and @securityService.isTeacherOfStudent(authentication, #classId))")
    @GetMapping("/by-class/{classId}/by-course/{courseId}/by-teacher/{teacherId}")
    public List<StudentFullInfoDTO> getByClassCourseTeacher(@PathVariable Long classId, @PathVariable Long courseId, @PathVariable Long teacherId) {
        return enrollmentRepository.findAll().stream()
            .filter(e -> e.getClassroom() != null && e.getClassroom().getId().equals(classId)
                && e.getCourse() != null && e.getCourse().getId().equals(courseId)
                && e.getTeacher() != null && e.getTeacher().getId().equals(teacherId))
            .map(Enrollment::getStudent)
            .distinct()
            .map(student -> toFullInfo(student, enrollmentRepository.findByStudentId(student.getId())))
            .collect(Collectors.toList());
    }

    @Operation(summary = "Get student count per class", description = "Returns the number of students in each class.")
    @GetMapping("/count-by-class")
    public Map<String, Long> getStudentCountByClass() {
        return studentRepository.findAll().stream()
            .filter(s -> s.getClassroom() != null)
            .collect(Collectors.groupingBy(s -> s.getClassroom().getName(), Collectors.counting()));
    }

    @Operation(summary = "Get courses taken by students in a class", description = "Returns a mapping of student names to their courses for a given class.")
    @GetMapping("/courses-by-class/{classId}")
    public Map<String, List<String>> getCoursesByClass(@PathVariable Long classId) {
        return studentRepository.findAll().stream()
            .filter(s -> s.getClassroom() != null && Objects.equals(s.getClassroom().getId(), classId))
            .collect(Collectors.toMap(
                Student::getName,
                s -> enrollmentRepository.findByStudentId(s.getId()).stream()
                        .map(e -> e.getCourse().getTitle())
                        .distinct()
                        .collect(Collectors.toList())
            ));
    }

    @Operation(summary = "Get marks scored by a student in all courses", description = "Returns a mapping of course title to marks for a given student.")
    @GetMapping("/{studentId}/marks-by-course")
    public Map<String, Integer> getMarksByCourse(@PathVariable Long studentId) {
        return enrollmentRepository.findByStudentId(studentId).stream()
            .collect(Collectors.toMap(
                e -> e.getCourse().getTitle(),
                e -> e.getMarks() != null ? e.getMarks().intValue() : null
            ));
    }

    @Operation(summary = "List all students of a teacher with class info", description = "Returns all students taught by a teacher, with their class info, sorted by class and student name.")
    @GetMapping("/by-teacher/{teacherId}/students-with-class")
    public List<Map<String, Object>> getStudentsOfTeacherWithClass(@PathVariable Long teacherId) {
        return enrollmentRepository.findAll().stream()
            .filter(e -> e.getTeacher() != null && e.getTeacher().getId().equals(teacherId))
            .map(Enrollment::getStudent)
            .distinct()
            .sorted(Comparator.comparing((Student s) -> s.getClassroom() != null ? s.getClassroom().getName() : "")
                .thenComparing(Student::getName))
            .map(s -> {
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("studentId", s.getId());
                map.put("studentName", s.getName());
                map.put("className", s.getClassroom() != null ? s.getClassroom().getName() : null);
                return map;
            })
            .collect(Collectors.toList());
    }

    @Operation(summary = "Get pass/fail count", description = "Returns the number of students who passed and failed. Pass mark is 40.")
    @GetMapping("/pass-fail-count")
    public Map<String, Long> getPassFailCount() {
        long passed = enrollmentRepository.findAll().stream()
            .filter(e -> e.getMarks() != null && e.getMarks() >= 40)
            .map(Enrollment::getStudent)
            .distinct().count();
        long failed = enrollmentRepository.findAll().stream()
            .filter(e -> e.getMarks() != null && e.getMarks() < 40)
            .map(Enrollment::getStudent)
            .distinct().count();
        Map<String, Long> result = new HashMap<>();
        result.put("passed", passed);
        result.put("failed", failed);
        return result;
    }

    @Operation(summary = "Top scoring students per course", description = "Returns the student(s) with the highest marks in a given course.")
    @GetMapping("/top-scorers-by-course/{courseId}")
    public List<Map<String, Object>> getTopScorersByCourse(@PathVariable Long courseId) {
        List<Enrollment> enrollments = enrollmentRepository.findByCourseId(courseId);
        int max = enrollments.stream()
            .filter(e -> e.getMarks() != null)
            .mapToInt(e -> e.getMarks().intValue()).max().orElse(-1);
        return enrollments.stream()
            .filter(e -> e.getMarks() != null && e.getMarks().intValue() == max)
            .map(Enrollment::getStudent)
            .distinct()
            .map(s -> {
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("studentId", s.getId());
                map.put("studentName", s.getName());
                map.put("marks", max);
                return map;
            })
            .collect(Collectors.toList());
    }

    @Operation(summary = "Average marks per course", description = "Returns the average marks for each course.")
    @GetMapping("/average-marks-by-course")
    public Map<String, Double> getAverageMarksByCourse() {
        Map<String, List<Integer>> courseMarks = enrollmentRepository.findAll().stream()
            .filter(e -> e.getMarks() != null)
            .collect(Collectors.groupingBy(e -> e.getCourse().getTitle(), Collectors.mapping(e -> e.getMarks().intValue(), Collectors.toList())));
        Map<String, Double> result = new HashMap<>();
        courseMarks.forEach((course, marks) -> {
            result.put(course, marks.stream().mapToInt(i -> i).average().orElse(0));
        });
        return result;
    }

    @Operation(summary = "Course popularity", description = "Returns courses sorted by number of enrolled students.")
    @GetMapping("/course-popularity")
    public List<Map<String, Object>> getCoursePopularity() {
        Map<String, Long> courseCounts = enrollmentRepository.findAll().stream()
            .collect(Collectors.groupingBy(e -> e.getCourse().getTitle(), Collectors.counting()));
        return courseCounts.entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .map(e -> {
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("course", e.getKey());
                map.put("enrollments", e.getValue());
                return map;
            })
            .collect(Collectors.toList());
    }

    @Operation(summary = "Class performance distribution", description = "Shows how many students in a class are in each grade band (A/B/C/Fail). A: >=80, B: 60-79, C: 40-59, Fail: <40")
    @GetMapping("/class-performance-distribution/{classId}")
    public Map<String, Long> getClassPerformanceDistribution(@PathVariable Long classId) {
        Map<String, Long> result = new HashMap<>();
        List<Enrollment> enrollments = enrollmentRepository.findAll().stream()
            .filter(e -> e.getClassroom() != null && e.getClassroom().getId().equals(classId))
            .toList();
        long a = enrollments.stream().filter(e -> e.getMarks() != null && e.getMarks() >= 80).map(Enrollment::getStudent).distinct().count();
        long b = enrollments.stream().filter(e -> e.getMarks() != null && e.getMarks() >= 60 && e.getMarks() < 80).map(Enrollment::getStudent).distinct().count();
        long c = enrollments.stream().filter(e -> e.getMarks() != null && e.getMarks() >= 40 && e.getMarks() < 60).map(Enrollment::getStudent).distinct().count();
        long f = enrollments.stream().filter(e -> e.getMarks() != null && e.getMarks() < 40).map(Enrollment::getStudent).distinct().count();
        result.put("A", a);
        result.put("B", b);
        result.put("C", c);
        result.put("Fail", f);
        return result;
    }

    @Operation(summary = "Teacher workload", description = "Returns the number of unique classes and courses each teacher is handling.")
    @GetMapping("/teacher-workload")
    public List<Map<String, Object>> getTeacherWorkload() {
        Map<Long, Map<String, Set<String>>> teacherMap = new HashMap<>();
        enrollmentRepository.findAll().stream()
            .forEach(e -> {
                if (e.getTeacher() != null) {
                    teacherMap.computeIfAbsent(e.getTeacher().getId(), k -> new HashMap<>())
                        .computeIfAbsent("classes", k -> new HashSet<>()).add(e.getClassroom().getName());
                    teacherMap.get(e.getTeacher().getId())
                        .computeIfAbsent("courses", k -> new HashSet<>()).add(e.getCourse().getTitle());
                }
            });
        List<Map<String, Object>> result = new ArrayList<>();
        teacherMap.forEach((teacherId, map) -> {
            Map<String, Object> entry = new LinkedHashMap<>();
            entry.put("teacherId", teacherId);
            entry.put("classes", map.getOrDefault("classes", Set.of()));
            entry.put("courses", map.getOrDefault("courses", Set.of()));
            result.add(entry);
        });
        return result;
    }

    @Operation(summary = "Dashboard: pass/fail count by course, class, teacher", description = "Returns pass/fail counts for a course, class, or teacher.")
    @GetMapping("/dashboard/pass-fail")
    public Map<String, Object> getPassFailDashboard(@RequestParam(required = false) Long courseId,
                                                   @RequestParam(required = false) Long classId,
                                                   @RequestParam(required = false) Long teacherId) {
        List<Enrollment> enrollments = enrollmentRepository.findAll();
        if (classId != null) {
            enrollments = enrollments.stream().filter(e -> e.getClassroom() != null && e.getClassroom().getId().equals(classId)).toList();
        }
        if (teacherId != null) {
            enrollments = enrollments.stream().filter(e -> e.getTeacher() != null && e.getTeacher().getId().equals(teacherId)).toList();
        }
        long passed = enrollments.stream().filter(e -> (courseId == null || (e.getCourse() != null && e.getCourse().getId().equals(courseId))) && e.getMarks() != null && e.getMarks() >= 40).map(Enrollment::getStudent).distinct().count();
        long failed = enrollments.stream().filter(e -> (courseId == null || (e.getCourse() != null && e.getCourse().getId().equals(courseId))) && e.getMarks() != null && e.getMarks() < 40).map(Enrollment::getStudent).distinct().count();
        Map<String, Object> result = new HashMap<>();
        result.put("passed", passed);
        result.put("failed", failed);
        return result;
    }

    @Operation(summary = "Dashboard: average marks in a class", description = "Returns the average marks of students in a class.")
    @GetMapping("/dashboard/average-marks-by-class/{classId}")
    public Double getAverageMarksByClass(@PathVariable Long classId) {
        List<Integer> marks = enrollmentRepository.findAll().stream()
            .filter(e -> e.getClassroom() != null && e.getClassroom().getId().equals(classId) && e.getMarks() != null)
            .map(e -> e.getMarks().intValue())
            .toList();
        return marks.stream().mapToInt(i -> i).average().orElse(0);
    }

    @Operation(summary = "Dashboard: all marks in a class", description = "Returns all marks scored by students in a class.")
    @GetMapping("/dashboard/marks-in-class/{classId}")
    public List<Map<String, Object>> getAllMarksScoredInClass(@PathVariable Long classId) {
        return enrollmentRepository.findAll().stream()
            .filter(e -> e.getClassroom() != null && e.getClassroom().getId().equals(classId))
            .map(e -> {
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("studentId", e.getStudent().getId());
                map.put("studentName", e.getStudent().getName());
                map.put("course", e.getCourse().getTitle());
                map.put("marks", e.getMarks());
                return map;
            })
            .collect(Collectors.toList());
    }

    @Operation(summary = "Pass/Fail count by course", description = "Returns the number of students who passed and failed in a given course. Pass mark is 40.")
    @GetMapping("/dashboard/pass-fail-by-course/{courseId}")
    public Map<String, Long> getPassFailByCourse(@PathVariable Long courseId) {
        long passed = enrollmentRepository.findByCourseId(courseId).stream()
            .filter(e -> e.getMarks() != null && e.getMarks() >= 40)
            .map(Enrollment::getStudent)
            .distinct().count();
        long failed = enrollmentRepository.findByCourseId(courseId).stream()
            .filter(e -> e.getMarks() != null && e.getMarks() < 40)
            .map(Enrollment::getStudent)
            .distinct().count();
        Map<String, Long> result = new HashMap<>();
        result.put("passed", passed);
        result.put("failed", failed);
        return result;
    }

    @Operation(summary = "Pass/Fail count by class", description = "Returns the number of students who passed and failed in a given class. Pass mark is 40.")
    @GetMapping("/dashboard/pass-fail-by-class/{classId}")
    public Map<String, Long> getPassFailByClass(@PathVariable Long classId) {
        long passed = enrollmentRepository.findAll().stream()
            .filter(e -> e.getClassroom() != null && e.getClassroom().getId().equals(classId) && e.getMarks() != null && e.getMarks() >= 40)
            .map(Enrollment::getStudent)
            .distinct().count();
        long failed = enrollmentRepository.findAll().stream()
            .filter(e -> e.getClassroom() != null && e.getClassroom().getId().equals(classId) && e.getMarks() != null && e.getMarks() < 40)
            .map(Enrollment::getStudent)
            .distinct().count();
        Map<String, Long> result = new HashMap<>();
        result.put("passed", passed);
        result.put("failed", failed);
        return result;
    }

    @Operation(summary = "Pass/Fail count by teacher", description = "Returns the number of students who passed and failed for a given teacher. Pass mark is 40.")
    @GetMapping("/dashboard/pass-fail-by-teacher/{teacherId}")
    public Map<String, Long> getPassFailByTeacher(@PathVariable Long teacherId) {
        long passed = enrollmentRepository.findAll().stream()
            .filter(e -> e.getTeacher() != null && e.getTeacher().getId().equals(teacherId) && e.getMarks() != null && e.getMarks() >= 40)
            .map(Enrollment::getStudent)
            .distinct().count();
        long failed = enrollmentRepository.findAll().stream()
            .filter(e -> e.getTeacher() != null && e.getTeacher().getId().equals(teacherId) && e.getMarks() != null && e.getMarks() < 40)
            .map(Enrollment::getStudent)
            .distinct().count();
        Map<String, Long> result = new HashMap<>();
        result.put("passed", passed);
        result.put("failed", failed);
        return result;
    }

    @Operation(summary = "Average marks in a class", description = "Returns the average marks of all students in a class (across all courses). Null marks are ignored.")
    @GetMapping("/dashboard/average-marks-in-class/{classId}")
    public Double getAverageMarksInClass(@PathVariable Long classId) {
        List<Integer> marks = enrollmentRepository.findAll().stream()
            .filter(e -> e.getClassroom() != null && e.getClassroom().getId().equals(classId) && e.getMarks() != null)
            .map(e -> e.getMarks().intValue())
            .collect(Collectors.toList());
        return marks.isEmpty() ? null : marks.stream().mapToInt(i -> i).average().orElse(0);
    }

    @Operation(summary = "All marks in a class", description = "Returns all students in a class with their marks for each course.")
    @GetMapping("/dashboard/all-marks-in-class/{classId}")
    public List<Map<String, Object>> getAllMarksInClass(@PathVariable Long classId) {
        return enrollmentRepository.findAll().stream()
            .filter(e -> e.getClassroom() != null && e.getClassroom().getId().equals(classId))
            .collect(Collectors.groupingBy(e -> e.getStudent().getId())).values().stream()
            .map(enrollments -> {
                Map<String, Object> map = new LinkedHashMap<>();
                Enrollment first = enrollments.get(0);
                map.put("studentId", first.getStudent().getId());
                map.put("studentName", first.getStudent().getName());
                map.put("marks", enrollments.stream().collect(
                    Collectors.toMap(
                        e -> e.getCourse().getTitle(),
                        e -> e.getMarks() != null ? e.getMarks().intValue() : null
                    )));
                return map;
            })
            .collect(Collectors.toList());
    }

    @Operation(summary = "Class ranking (topper, top 10, last 10) with pagination", description = "Returns students in a class ranked by total marks. Use page and size for pagination. Page is 0-based.")
    @GetMapping("/dashboard/class-ranking/{classId}")
    public Map<String, Object> getClassRanking(
            @PathVariable Long classId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Map<Long, List<Enrollment>> enrollmentsByStudent = enrollmentRepository.findAll().stream()
            .filter(e -> e.getClassroom() != null && e.getClassroom().getId().equals(classId))
            .collect(Collectors.groupingBy(e -> e.getStudent().getId()));
        List<Map<String, Object>> ranked = enrollmentsByStudent.values().stream()
            .map(enrollments -> {
                Enrollment first = enrollments.get(0);
                int total = enrollments.stream().filter(e -> e.getMarks() != null).mapToInt(e -> e.getMarks().intValue()).sum();
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("studentId", first.getStudent().getId());
                map.put("studentName", first.getStudent().getName());
                map.put("totalMarks", total);
                return map;
            })
            .sorted(Comparator.comparingInt((Map<String, Object> m) -> (int) m.get("totalMarks")).reversed())
            .collect(Collectors.toList());
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("topper", ranked.isEmpty() ? null : ranked.get(0));
        result.put("top10", ranked.stream().limit(10).collect(Collectors.toList()));
        result.put("last10", ranked.stream().skip(Math.max(0, ranked.size() - 10)).collect(Collectors.toList()));
        int from = page * size;
        int to = Math.min(from + size, ranked.size());
        result.put("page", page);
        result.put("size", size);
        result.put("total", ranked.size());
        result.put("students", from < to ? ranked.subList(from, to) : Collections.emptyList());
        return result;
    }

    private StudentFullInfoDTO toFullInfo(Student student, List<Enrollment> enrollments) {
        StudentFullInfoDTO dto = new StudentFullInfoDTO();
        dto.studentId = student.getId();
        dto.studentName = student.getName();
        dto.className = student.getClassroom() != null ? student.getClassroom().getName() : null;
        dto.courses = enrollments.stream().map(e -> {
            StudentFullInfoDTO.CourseInfo ci = new StudentFullInfoDTO.CourseInfo();
            ci.courseId = e.getCourse().getId();
            ci.courseTitle = e.getCourse().getTitle();
            ci.marks = e.getMarks() != null ? e.getMarks().intValue() : null;
            ci.teachers = Collections.singletonList(e.getTeacher().getName());
            return ci;
        }).collect(Collectors.toList());
        return dto;
    }
}
