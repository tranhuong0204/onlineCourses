package com.example.onlineCourses.controller;

import com.example.onlineCourses.model.Course;
import com.example.onlineCourses.repository.CourseRepository;
import com.example.onlineCourses.model.Provider;
import com.example.onlineCourses.repository.ProviderRepository;
import com.example.onlineCourses.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000") //cho phép mọi nguồn (tạm thời để test)
@RestController
@RequestMapping("/api/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping("/provider/{providerId}")
    public Course createCourse(@PathVariable Long providerId, @RequestBody Course course) {
        return courseService.createCourse(providerId, course);
    }

    @GetMapping("/provider/{providerId}")
    public List<Course> getCoursesByProvider(@PathVariable Long providerId) {
        return courseService.getCoursesByProvider(providerId);
    }

    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{code}")
    public Course getByCode(@PathVariable String code) {
        return courseService.getByCode(code);
    }
}
