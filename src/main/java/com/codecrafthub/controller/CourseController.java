package com.codecrafthub.controller;

import com.codecrafthub.model.Course;
import com.codecrafthub.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;
    private static final List<String> VALID_STATUSES = Arrays.asList("Not Started", "In Progress", "Completed");

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Integer id) {
        List<Course> courses = courseService.getAllCourses();
        Optional<Course> course = courses.stream().filter(c -> c.getId().equals(id)).findFirst();

        if (course.isPresent()) {
            return ResponseEntity.ok(course.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course tidak ditemukan.");
        }
    }

    @PostMapping
    public ResponseEntity<?> createCourse(@RequestBody Course newCourse) {
        if (newCourse.getName() == null || newCourse.getDescription() == null ||
                newCourse.getTargetDate() == null || newCourse.getStatus() == null) {
            return ResponseEntity.badRequest().body("Error: Pastikan field wajib terisi.");
        }
        if (!VALID_STATUSES.contains(newCourse.getStatus())) {
            return ResponseEntity.badRequest().body("Error: Status tidak valid.");
        }

        List<Course> courses = courseService.getAllCourses();
        newCourse.setId(courseService.generateId(courses));
        newCourse.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        courses.add(newCourse);
        courseService.saveAllCourses(courses);

        return new ResponseEntity<>(newCourse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable Integer id, @RequestBody Course updatedData) {
        List<Course> courses = courseService.getAllCourses();
        boolean found = false;

        for (Course c : courses) {
            if (c.getId().equals(id)) {
                found = true;
                if (updatedData.getName() != null) c.setName(updatedData.getName());
                if (updatedData.getDescription() != null) c.setDescription(updatedData.getDescription());
                if (updatedData.getTargetDate() != null) c.setTargetDate(updatedData.getTargetDate());
                if (updatedData.getStatus() != null) {
                    if (VALID_STATUSES.contains(updatedData.getStatus())) {
                        c.setStatus(updatedData.getStatus());
                    } else {
                        return ResponseEntity.badRequest().body("Error: Status tidak valid.");
                    }
                }
                break;
            }
        }

        if (found) {
            courseService.saveAllCourses(courses);
            return ResponseEntity.ok("Course berhasil diupdate.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course tidak ditemukan.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Integer id) {
        List<Course> courses = courseService.getAllCourses();
        boolean removed = courses.removeIf(c -> c.getId().equals(id));

        if (removed) {
            courseService.saveAllCourses(courses);
            return ResponseEntity.ok("Course berhasil dihapus.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course tidak ditemukan.");
        }
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getCourseStats() {
        List<Course> courses = courseService.getAllCourses();

        long totalCourses = courses.size();
        long notStarted = courses.stream().filter(c -> "Not Started".equals(c.getStatus())).count();
        long inProgress = courses.stream().filter(c -> "In Progress".equals(c.getStatus())).count();
        long completed = courses.stream().filter(c -> "Completed".equals(c.getStatus())).count();

        Map<String, Object> stats = new HashMap<>();
        stats.put("total_courses", totalCourses);
        stats.put("not_started", notStarted);
        stats.put("in_progress", inProgress);
        stats.put("completed", completed);

        return ResponseEntity.ok(stats);
    }
}