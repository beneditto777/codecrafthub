package com.codecrafthub.service;

import com.codecrafthub.model.Course;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {

    private static final String FILE_PATH = "courses.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Course> getAllCourses() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try {
            return objectMapper.readValue(file, new TypeReference<List<Course>>() {});
        } catch (IOException e) {
            System.err.println("Gagal membaca file: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void saveAllCourses(List<Course> courses) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), courses);
        } catch (IOException e) {
            System.err.println("Gagal menyimpan file: " + e.getMessage());
            throw new RuntimeException("Gagal menyimpan data", e);
        }
    }

    public Integer generateId(List<Course> courses) {
        if (courses.isEmpty()) {
            return 1;
        }
        int maxId = 0;
        for (Course c : courses) {
            if (c.getId() > maxId) {
                maxId = c.getId();
            }
        }
        return maxId + 1;
    }
}