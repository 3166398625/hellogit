package com.bjlemon.eduservice.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjlemon.commonutils.R;
import com.bjlemon.eduservice.entity.EduCourse;
import com.bjlemon.eduservice.entity.EduTeacher;
import com.bjlemon.eduservice.entity.vo.CourseInfoVo;
import com.bjlemon.eduservice.entity.vo.CoursePublishVo;
import com.bjlemon.eduservice.service.EduCourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author blackcat
 * @since 2021-01-21
 */
@RestController
@RequestMapping("/eduservice/course")
@Slf4j
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    //课程列表 基本实现
    //TODO  完善条件查询带分页
    @GetMapping("/list/{currentPage}/{pageSize}")
    public R getCourseList(@PathVariable("currentPage") long currentPage,
                           @PathVariable("pageSize") long pageSize) {

        log.info("currentPage:{},pageSize:{}",currentPage,pageSize);
        Page<EduCourse> page=new Page<>(currentPage,pageSize);
        courseService.page(page,null);
        long total=page.getTotal();//总记录数
        List<EduCourse> list = page.getRecords();
        return R.ok().data("total",total).data("courseList",list);
    }

    //添加课程基本信息的方法
    @PostMapping("/addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        //返回添加之后课程id，为了后面添加大纲使用
        String id = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId",id);
    }

    //根据课程ID查询课程基本信息
    @GetMapping("/getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId){

        CourseInfoVo courseInfoVo=courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfo",courseInfoVo);
    }

    //修改课程信息
    @PostMapping("/updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){

        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    //根据课程id查询课程确认信息
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id) {
        CoursePublishVo coursePublishVo = courseService.publishCourseInfo(id);
        return R.ok().data("publishCourse",coursePublishVo);
    }

    //课程最终发布
    //修改课程状态
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");//设置课程发布状态
        courseService.updateById(eduCourse);
        return R.ok();
    }

    //删除课程
    @DeleteMapping("{courseId}")
    public R deleteCourse(@PathVariable String courseId) {
        courseService.removeCourse(courseId);
        return R.ok();
    }
}

