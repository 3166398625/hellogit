package com.bjlemon.eduservice.controller;


import com.bjlemon.commonutils.R;
import com.bjlemon.eduservice.entity.subject.OneSubject;
import com.bjlemon.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author blackcat
 * @since 2021-01-20
 */
@RestController
@RequestMapping("/eduservice/subject")
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    //添加课程分类
    //获得上传的excel文件，读取内容
    @PostMapping("/addSubject")
    public R addSubject(MultipartFile file){

        subjectService.saveSubject(file,subjectService);
        return R.ok();
    }

    //课程分类列表（树形）
    @GetMapping("/getAllSubject")
    public R getAllSubject() {
        //list集合泛型是一级分类
        List<OneSubject> list = subjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }
}

