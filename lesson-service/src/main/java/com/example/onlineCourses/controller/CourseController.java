package com.example.onlineCourses.controller;

import com.example.onlineCourses.model.Course;
import com.example.onlineCourses.repository.CourseRepository;
//import com.example.onlineCourses.model.Provider;
//import com.example.onlineCourses.repository.ProviderRepository;
import com.example.onlineCourses.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000") //cho phép mọi nguồn (tạm thời để test)
@RestController
@RequestMapping("/api/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping("/public/create")
    public Course createCourse( @RequestBody Course course) {
        return courseService.createCourse( course);
    }

//    public Course createCourse(@PathVariable Long providerId, @RequestBody Course course) {
//        return courseService.createCourse( course);
//    }

//    @GetMapping("/provider/{providerId}")
//    public List<Course> getCoursesByProvider(@PathVariable Long providerId) {
//        return courseService.getCoursesByProvider(providerId);
//    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/list")
    public List<Course> getAllCourses() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Auth principal: " + auth);
        return courseService.getAllCourses();

    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{code}")
    public Course getByCode(@PathVariable String code) {
        return courseService.getByCode(code);
    }

//    @PreAuthorize("hasRole('USER')")
    @GetMapping("id/{id}")
    public Course getById(@PathVariable Long id) {
        return courseService.getById(id);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/batch")
    public List<Course> getCoursesByIds(@RequestBody List<Long> ids) {
        return courseService.getCoursesByIds(ids);
    }

}
