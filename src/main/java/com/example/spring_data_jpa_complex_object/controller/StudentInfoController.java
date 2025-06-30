package com.example.spring_data_jpa_complex_object.controller;

import com.example.spring_data_jpa_complex_object.dto.StudentFullInfoDTO;
import com.example.spring_data_jpa_complex_object.entity.*;
import com.example.spring_data_jpa_complex_object.repository.StudentRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@Tag(name = "Student APIs", description = "Endpoints for student, class, course, and teacher queries")
@RestController
@RequestMapping("/api/students")
public class StudentInfoController {
    @Autowired
    private StudentRepository studentRepository;

    @Operation(summary = "Get full info for a student", description = "Returns all details for a student, including class, courses, marks, and teachers.")
    @GetMapping("/{id}/full-info")
    public StudentFullInfoDTO getStudentFullInfo(@PathVariable Long id) {
        return studentRepository.findById(id).map(this::toFullInfo).orElse(null);
    }

    @Operation(summary = "Get full info for all students", description = "Returns a list of all students with their class, courses, marks, and teachers.")
    @GetMapping("/full-info")
    public List<StudentFullInfoDTO> getAllStudentsFullInfo() {
        return studentRepository.findAll().stream().map(this::toFullInfo).collect(Collectors.toList());
    }

    @Operation(summary = "Get students by class", description = "Returns all students in a specific class, with their full info.")
    @GetMapping("/by-class/{classId}")
    public List<StudentFullInfoDTO> getByClass(@PathVariable Long classId) {
        return studentRepository.findAll().stream()
            .filter(s -> s.getClassroom() != null && Objects.equals(s.getClassroom().getId(), classId))
            .map(this::toFullInfo).collect(Collectors.toList());
    }

    @Operation(summary = "Get students by course", description = "Returns all students enrolled in a specific course, with their full info.")
    @GetMapping("/by-course/{courseId}")
    public List<StudentFullInfoDTO> getByCourse(@PathVariable Long courseId) {
        return studentRepository.findAll().stream()
            .filter(s -> s.getEnrollments().stream().anyMatch(e -> e.getCourse().getId().equals(courseId)))
            .map(this::toFullInfo).collect(Collectors.toList());
    }

    @Operation(summary = "Get students by teacher", description = "Returns all students taught by a specific teacher (in any course/class), with their full info.")
    @GetMapping("/by-teacher/{teacherId}")
    public List<StudentFullInfoDTO> getByTeacher(@PathVariable Long teacherId) {
        return studentRepository.findAll().stream()
            .filter(s -> s.getEnrollments().stream().anyMatch(e ->
                e.getCourse().getTeacherCourseClasses().stream().anyMatch(tcc -> tcc.getTeacher().getId().equals(teacherId))
            ))
            .map(this::toFullInfo).collect(Collectors.toList());
    }

    @Operation(summary = "Get students by class, course, and teacher", description = "Returns all students in a specific class, for a specific course, taught by a specific teacher, with their full info.")
    @GetMapping("/by-class/{classId}/by-course/{courseId}/by-teacher/{teacherId}")
    public List<StudentFullInfoDTO> getByClassCourseTeacher(@PathVariable Long classId, @PathVariable Long courseId, @PathVariable Long teacherId) {
        return studentRepository.findAll().stream()
            .filter(s ->
                s.getClassroom() != null && Objects.equals(s.getClassroom().getId(), classId) &&
                s.getEnrollments().stream().anyMatch(e ->
                    e.getCourse().getId().equals(courseId) &&
                    e.getCourse().getTeacherCourseClasses().stream().anyMatch(tcc -> tcc.getTeacher().getId().equals(teacherId) && tcc.getClassroom().getId().equals(classId))
                )
            )
            .map(this::toFullInfo)
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
                s -> s.getEnrollments().stream().map(e -> e.getCourse().getTitle()).distinct().collect(Collectors.toList())
            ));
    }

    @Operation(summary = "Get marks scored by a student in all courses", description = "Returns a mapping of course title to marks for a given student.")
    @GetMapping("/{studentId}/marks-by-course")
    public Map<String, Integer> getMarksByCourse(@PathVariable Long studentId) {
        return studentRepository.findById(studentId)
            .map(s -> s.getEnrollments().stream().collect(
                Collectors.toMap(
                    e -> e.getCourse().getTitle(),
                    e -> e.getMarks() != null ? e.getMarks().intValue() : null
                )
            ))
            .orElse(Collections.emptyMap());
    }

    @Operation(summary = "List all students of a teacher with class info", description = "Returns all students taught by a teacher, with their class info, sorted by class and student name.")
    @GetMapping("/by-teacher/{teacherId}/students-with-class")
    public List<Map<String, Object>> getStudentsOfTeacherWithClass(@PathVariable Long teacherId) {
        return studentRepository.findAll().stream()
            .filter(s -> s.getEnrollments().stream().anyMatch(e ->
                e.getCourse().getTeacherCourseClasses().stream().anyMatch(tcc -> tcc.getTeacher().getId().equals(teacherId))
            ))
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
        long passed = studentRepository.findAll().stream()
            .filter(s -> s.getEnrollments().stream().anyMatch(e -> e.getMarks() != null && e.getMarks() >= 40)).count();
        long failed = studentRepository.findAll().stream()
            .filter(s -> s.getEnrollments().stream().anyMatch(e -> e.getMarks() != null && e.getMarks() < 40)).count();
        Map<String, Long> result = new HashMap<>();
        result.put("passed", passed);
        result.put("failed", failed);
        return result;
    }

    @Operation(summary = "Top scoring students per course", description = "Returns the student(s) with the highest marks in a given course.")
    @GetMapping("/top-scorers-by-course/{courseId}")
    public List<Map<String, Object>> getTopScorersByCourse(@PathVariable Long courseId) {
        List<Student> students = studentRepository.findAll();
        int max = students.stream()
            .flatMap(s -> s.getEnrollments().stream())
            .filter(e -> e.getCourse().getId().equals(courseId) && e.getMarks() != null)
            .mapToInt(e -> e.getMarks().intValue()).max().orElse(-1);
        return students.stream()
            .filter(s -> s.getEnrollments().stream().anyMatch(e -> e.getCourse().getId().equals(courseId) && e.getMarks() != null && e.getMarks().intValue() == max))
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
        Map<String, List<Integer>> courseMarks = studentRepository.findAll().stream()
            .flatMap(s -> s.getEnrollments().stream())
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
        Map<String, Long> courseCounts = studentRepository.findAll().stream()
            .flatMap(s -> s.getEnrollments().stream())
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
        List<Student> students = studentRepository.findAll().stream()
            .filter(s -> s.getClassroom() != null && s.getClassroom().getId().equals(classId))
            .toList();
        long a = students.stream().filter(s -> s.getEnrollments().stream().anyMatch(e -> e.getMarks() != null && e.getMarks() >= 80)).count();
        long b = students.stream().filter(s -> s.getEnrollments().stream().anyMatch(e -> e.getMarks() != null && e.getMarks() >= 60 && e.getMarks() < 80)).count();
        long c = students.stream().filter(s -> s.getEnrollments().stream().anyMatch(e -> e.getMarks() != null && e.getMarks() >= 40 && e.getMarks() < 60)).count();
        long f = students.stream().filter(s -> s.getEnrollments().stream().anyMatch(e -> e.getMarks() != null && e.getMarks() < 40)).count();
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
        studentRepository.findAll().stream()
            .flatMap(s -> s.getEnrollments().stream())
            .forEach(e -> e.getCourse().getTeacherCourseClasses().forEach(tcc -> {
                teacherMap.computeIfAbsent(tcc.getTeacher().getId(), k -> new HashMap<>())
                    .computeIfAbsent("classes", k -> new HashSet<>()).add(tcc.getClassroom().getName());
                teacherMap.get(tcc.getTeacher().getId())
                    .computeIfAbsent("courses", k -> new HashSet<>()).add(e.getCourse().getTitle());
            }));
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
        List<Student> students = studentRepository.findAll();
        if (classId != null) {
            students = students.stream().filter(s -> s.getClassroom() != null && s.getClassroom().getId().equals(classId)).toList();
        }
        if (teacherId != null) {
            students = students.stream().filter(s -> s.getEnrollments().stream().anyMatch(e -> e.getCourse().getTeacherCourseClasses().stream().anyMatch(tcc -> tcc.getTeacher().getId().equals(teacherId)))).toList();
        }
        long passed = students.stream().filter(s -> s.getEnrollments().stream().anyMatch(e -> (courseId == null || e.getCourse().getId().equals(courseId)) && e.getMarks() != null && e.getMarks() >= 40)).count();
        long failed = students.stream().filter(s -> s.getEnrollments().stream().anyMatch(e -> (courseId == null || e.getCourse().getId().equals(courseId)) && e.getMarks() != null && e.getMarks() < 40)).count();
        Map<String, Object> result = new HashMap<>();
        result.put("passed", passed);
        result.put("failed", failed);
        return result;
    }

    @Operation(summary = "Dashboard: average marks in a class", description = "Returns the average marks of students in a class.")
    @GetMapping("/dashboard/average-marks-by-class/{classId}")
    public Double getAverageMarksByClass(@PathVariable Long classId) {
        List<Integer> marks = studentRepository.findAll().stream()
            .filter(s -> s.getClassroom() != null && s.getClassroom().getId().equals(classId))
            .flatMap(s -> s.getEnrollments().stream())
            .filter(e -> e.getMarks() != null)
            .map(e -> e.getMarks().intValue())
            .toList();
        return marks.stream().mapToInt(i -> i).average().orElse(0);
    }

    @Operation(summary = "Dashboard: all marks in a class", description = "Returns all marks scored by students in a class.")
    @GetMapping("/dashboard/marks-in-class/{classId}")
    public List<Map<String, Object>> getAllMarksInClass(@PathVariable Long classId) {
        return studentRepository.findAll().stream()
            .filter(s -> s.getClassroom() != null && s.getClassroom().getId().equals(classId))
            .flatMap(s -> s.getEnrollments().stream().map(e -> Map.of(
                "studentId", s.getId(),
                "studentName", s.getName(),
                "course", e.getCourse().getTitle(),
                "marks", e.getMarks()
            )))
            .collect(Collectors.toList());
    }

    @Operation(summary = "Dashboard: topper, top 10, last 10 in class", description = "Returns the topper, top 10, and last 10 students in a class by average marks. Supports pagination.")
    @GetMapping("/dashboard/class-ranking/{classId}")
    public Map<String, Object> getClassRanking(@PathVariable Long classId,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "5") int size) {
        List<Map<String, Object>> ranked = studentRepository.findAll().stream()
            .filter(s -> s.getClassroom() != null && s.getClassroom().getId().equals(classId))
            .map(s -> {
                double avg = s.getEnrollments().stream().filter(e -> e.getMarks() != null).mapToInt(e -> e.getMarks().intValue()).average().orElse(0);
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("studentId", s.getId());
                map.put("studentName", s.getName());
                map.put("averageMarks", avg);
                return map;
            })
            .sorted((a, b) -> Double.compare((double) b.get("averageMarks"), (double) a.get("averageMarks")))
            .toList();
        Map<String, Object> result = new HashMap<>();
        result.put("topper", ranked.isEmpty() ? null : ranked.get(0));
        result.put("top10", ranked.stream().limit(10).toList());
        result.put("last10", ranked.stream().skip(Math.max(0, ranked.size() - 10)).toList());
        int from = page * size;
        int to = Math.min(from + size, ranked.size());
        result.put("page", ranked.subList(Math.min(from, ranked.size()), to));
        result.put("total", ranked.size());
        return result;
    }

    // --- DASHBOARD ENDPOINTS ---

    @Operation(summary = "Pass/Fail count by course", description = "Returns the number of students who passed and failed in a given course. Pass mark is 40.")
    @GetMapping("/dashboard/pass-fail-by-course/{courseId}")
    public Map<String, Long> getPassFailByCourse(@PathVariable Long courseId) {
        long passed = studentRepository.findAll().stream()
            .filter(s -> s.getEnrollments().stream().anyMatch(e -> e.getCourse().getId().equals(courseId) && e.getMarks() != null && e.getMarks() >= 40)).count();
        long failed = studentRepository.findAll().stream()
            .filter(s -> s.getEnrollments().stream().anyMatch(e -> e.getCourse().getId().equals(courseId) && e.getMarks() != null && e.getMarks() < 40)).count();
        Map<String, Long> result = new HashMap<>();
        result.put("passed", passed);
        result.put("failed", failed);
        return result;
    }

    @Operation(summary = "Pass/Fail count by class", description = "Returns the number of students who passed and failed in a given class. Pass mark is 40.")
    @GetMapping("/dashboard/pass-fail-by-class/{classId}")
    public Map<String, Long> getPassFailByClass(@PathVariable Long classId) {
        long passed = studentRepository.findAll().stream()
            .filter(s -> s.getClassroom() != null && s.getClassroom().getId().equals(classId) && s.getEnrollments().stream().anyMatch(e -> e.getMarks() != null && e.getMarks() >= 40)).count();
        long failed = studentRepository.findAll().stream()
            .filter(s -> s.getClassroom() != null && s.getClassroom().getId().equals(classId) && s.getEnrollments().stream().anyMatch(e -> e.getMarks() != null && e.getMarks() < 40)).count();
        Map<String, Long> result = new HashMap<>();
        result.put("passed", passed);
        result.put("failed", failed);
        return result;
    }

    @Operation(summary = "Pass/Fail count by teacher", description = "Returns the number of students who passed and failed for a given teacher. Pass mark is 40.")
    @GetMapping("/dashboard/pass-fail-by-teacher/{teacherId}")
    public Map<String, Long> getPassFailByTeacher(@PathVariable Long teacherId) {
        long passed = studentRepository.findAll().stream()
            .filter(s -> s.getEnrollments().stream().anyMatch(e -> e.getCourse().getTeacherCourseClasses().stream().anyMatch(tcc -> tcc.getTeacher().getId().equals(teacherId)) && e.getMarks() != null && e.getMarks() >= 40)).count();
        long failed = studentRepository.findAll().stream()
            .filter(s -> s.getEnrollments().stream().anyMatch(e -> e.getCourse().getTeacherCourseClasses().stream().anyMatch(tcc -> tcc.getTeacher().getId().equals(teacherId)) && e.getMarks() != null && e.getMarks() < 40)).count();
        Map<String, Long> result = new HashMap<>();
        result.put("passed", passed);
        result.put("failed", failed);
        return result;
    }

    @Operation(summary = "Average marks in a class", description = "Returns the average marks of all students in a class (across all courses). Null marks are ignored.")
    @GetMapping("/dashboard/average-marks-in-class/{classId}")
    public Double getAverageMarksInClass(@PathVariable Long classId) {
        List<Integer> marks = studentRepository.findAll().stream()
            .filter(s -> s.getClassroom() != null && s.getClassroom().getId().equals(classId))
            .flatMap(s -> s.getEnrollments().stream())
            .filter(e -> e.getMarks() != null)
            .map(e -> e.getMarks().intValue())
            .collect(Collectors.toList());
        return marks.isEmpty() ? null : marks.stream().mapToInt(i -> i).average().orElse(0);
    }

    @Operation(summary = "All marks in a class", description = "Returns all students in a class with their marks for each course.")
    @GetMapping("/dashboard/all-marks-in-class/{classId}")
    public List<Map<String, Object>> getAllMarksInClass(@PathVariable Long classId) {
        return studentRepository.findAll().stream()
            .filter(s -> s.getClassroom() != null && s.getClassroom().getId().equals(classId))
            .map(s -> {
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("studentId", s.getId());
                map.put("studentName", s.getName());
                map.put("marks", s.getEnrollments().stream().collect(
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
        List<Student> students = studentRepository.findAll().stream()
            .filter(s -> s.getClassroom() != null && s.getClassroom().getId().equals(classId))
            .collect(Collectors.toList());
        List<Map<String, Object>> ranked = students.stream()
            .map(s -> {
                int total = s.getEnrollments().stream().filter(e -> e.getMarks() != null).mapToInt(e -> e.getMarks().intValue()).sum();
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("studentId", s.getId());
                map.put("studentName", s.getName());
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

    private StudentFullInfoDTO toFullInfo(Student student) {
        StudentFullInfoDTO dto = new StudentFullInfoDTO();
        dto.studentId = student.getId();
        dto.studentName = student.getName();
        dto.className = student.getClassroom() != null ? student.getClassroom().getName() : null;
        dto.courses = student.getEnrollments().stream().map(e -> {
            StudentFullInfoDTO.CourseInfo ci = new StudentFullInfoDTO.CourseInfo();
            ci.courseId = e.getCourse().getId();
            ci.courseTitle = e.getCourse().getTitle();
            ci.marks = e.getMarks() != null ? e.getMarks().intValue() : null;
            ci.teachers = e.getCourse().getTeacherCourseClasses().stream()
                .filter(tcc -> tcc.getClassroom().getId().equals(student.getClassroom() != null ? student.getClassroom().getId() : null))
                .map(tcc -> tcc.getTeacher().getName()).distinct().collect(Collectors.toList());
            return ci;
        }).collect(Collectors.toList());
        return dto;
    }
}
