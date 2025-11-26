package com.example.onlineCourses.service;

import com.example.onlineCourses.model.Course;
import com.example.onlineCourses.model.Provider;
import com.example.onlineCourses.repository.CourseRepository;
import com.example.onlineCourses.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepo;

    @Autowired
    private ProviderRepository providerRepo;

    public Course createCourse(Course course) {
        // Nếu không có provider thì chỉ lưu trực tiếp
        return courseRepo.save(course);
    }

//    public Course createCourse(Long providerId, Course course) {
//        Provider provider = providerRepo.findById(providerId)
//                .orElseThrow(() -> new RuntimeException("Provider not found"));
//        course.setProvider(provider);
//        return courseRepo.save(course);
//    }

//    public List<Course> getCoursesByProvider(Long providerId) {
//        Provider provider = providerRepo.findById(providerId)
//                .orElseThrow(() -> new RuntimeException("Provider not found"));
//        return courseRepo.findByProvider(provider);
//    }

    public List<Course> getAllCourses() {
        return courseRepo.findAll();
    }

    public Course getByCode(String code) {
        return courseRepo.findByCode(code);
    }

    public Course getById(Long id) {
        return courseRepo.findById(id).orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
    }
    public List<Course> getCoursesByIds(List<Long> ids) {
        return courseRepo.findAllById(ids);
    }

}

