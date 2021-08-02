package com.robin.educourseboot.controller;

import com.robin.educourseboot.entity.CourseDTO;
import com.robin.educourseboot.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("course")
@CrossOrigin //跨域
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("getAllCourse")
    public List<CourseDTO> getAllCourse() {
        return courseService.getAllCourse();
    }

    @GetMapping("getCourseById/{courseid}")
    public CourseDTO getCourseById(@PathVariable("courseid") Integer courseid) {
        return courseService.getCourseById(courseid);
    }

    @GetMapping("getCourseByUserId")
    public List<CourseDTO> getCourseByUserId(Integer userid ) {
        return courseService.getCourseByUserId(userid);
    }
}