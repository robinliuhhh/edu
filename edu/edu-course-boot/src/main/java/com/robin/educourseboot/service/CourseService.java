package com.robin.educourseboot.service;

import com.robin.educourseboot.entity.Course;
import com.robin.educourseboot.entity.CourseDTO;

import java.util.List;

public interface CourseService {
    /**
     * 查询全部课程信息
     *
     * @return
     */
    List<CourseDTO> getAllCourse();

    /**
     * 查询某门课程的详细信息
     *
     * @param courseid 课程编号
     * @return
     */
    CourseDTO getCourseById(Integer courseid);

    /**
     * 查询已登录用户购买的全部课程信息
     * @return
     */
    List<CourseDTO> getCourseByUserId(Integer userid);

}