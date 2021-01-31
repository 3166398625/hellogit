package com.bjlemon.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjlemon.commonutils.R;
import com.bjlemon.commonutils.ResultCode;
import com.bjlemon.eduservice.entity.EduTeacher;
import com.bjlemon.eduservice.entity.vo.TeacherQuery;
import com.bjlemon.eduservice.service.EduTeacherService;
import com.bjlemon.servicebase.exceptionhandler.LemonException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author blackcat
 * @since 2021-01-16
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
@Slf4j
public class EduTeacherController {

    @Resource
    private EduTeacherService eduTeacherService;

    //查询讲师表所有数据 get post put delete
    @ApiOperation(value = "查询所有讲师")
    @GetMapping("/findAll")
    public R findAllTeacher(){

        List<EduTeacher> teachers = eduTeacherService.list(null);
        return R.ok().data("teachers",teachers);
    }

    //逻辑删除讲师数据
    @ApiOperation(value = "根据ID逻辑删除讲师")
    @DeleteMapping("/remove/{id}")
    public R removeTeacher(
            @ApiParam(name = "id",value = "讲师ID",required = true)
            @PathVariable("id") String id){

        log.info("id:{}",id);
        boolean result = eduTeacherService.removeById(id);
        return result==true?R.ok():R.error();
    }

    //分页查询讲师
    @GetMapping("/pageTeacher/{current}/{size}")
    public R findTeacherPage(@PathVariable("current") long current,
                             @PathVariable("size") long size){
        //创建page
        Page<EduTeacher> page=new Page<>(current,size);
        eduTeacherService.page(page, null);

        long total=page.getTotal();//总记录数
        List<EduTeacher> records=page.getRecords();//数据List集合

        Map map=new HashMap();
        map.put("total",total);
        map.put("records",records);

        return R.ok().data(map);
    }

    //条件查询 分页 RequestBody提交方式必须为PostMapping，否则无法取值
    @PostMapping("/pageTeacherCondition/{current}/{size}")
    public R pageTeacherCondition(@PathVariable("current") long current,
                                  @PathVariable("size") long size,
                                  @RequestBody(required = false) TeacherQuery teacherQuery){

        //创建page
        Page<EduTeacher> page=new Page<>(current,size);
        //构建条件对象
        QueryWrapper<EduTeacher> wrapper=new QueryWrapper<>();
        String name=teacherQuery.getName();
        Integer level=teacherQuery.getLevel();
        String begin=teacherQuery.getBegin();
        String end=teacherQuery.getEnd();

        if(!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if(level!=null){
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);
        }

        wrapper.orderByDesc("gmt_create");
        //调用方法实现条件查询分页
        eduTeacherService.page(page,wrapper);

        long total=page.getTotal();//总记录数
        List<EduTeacher> records=page.getRecords();//数据List集合

        Map map=new HashMap();
        map.put("total",total);
        map.put("records",records);
        return R.ok().data(map);
    }

    //添加讲师
    @PostMapping("/addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);
        return save==true?R.ok():R.error();
    }

    //根据ID查询讲师
    @GetMapping("/getTeacher/{id}")
    public R getTeacher(@PathVariable("id") Long id){
        EduTeacher teacher = eduTeacherService.getById(id);
        return R.ok().data("teacher",teacher);
    }

    //讲师修改
    @PostMapping("/updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean b = eduTeacherService.updateById(eduTeacher);
        return b==true?R.ok():R.error();
    }
}

