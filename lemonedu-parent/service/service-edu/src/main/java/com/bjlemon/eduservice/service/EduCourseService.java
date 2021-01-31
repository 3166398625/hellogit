package com.bjlemon.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjlemon.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bjlemon.eduservice.entity.frontvo.CourseFrontVo;
import com.bjlemon.eduservice.entity.frontvo.CourseWebVo;
import com.bjlemon.eduservice.entity.vo.CourseInfoVo;
import com.bjlemon.eduservice.entity.vo.CoursePublishVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author blackcat
 * @since 2021-01-21
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo publishCourseInfo(String id);

    void removeCourse(String courseId);

    Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);
}
