package com.bjlemon.eduservice.controller.front;

import com.bjlemon.commonutils.CourseWebVoOrder;
import com.bjlemon.commonutils.JwtUtils;
import com.bjlemon.commonutils.R;
import com.bjlemon.eduservice.client.OrderClient;
import com.bjlemon.eduservice.entity.EduCourse;
import com.bjlemon.eduservice.entity.EduTeacher;
import com.bjlemon.eduservice.entity.chapter.ChapterVo;
import com.bjlemon.eduservice.entity.frontvo.CourseFrontVo;
import com.bjlemon.eduservice.entity.frontvo.CourseWebVo;
import com.bjlemon.eduservice.service.EduChapterService;
import com.bjlemon.eduservice.service.EduCourseService;
import com.bjlemon.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjlemon.eduservice.service.EduChapterService;
import com.bjlemon.eduservice.service.EduCourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/coursefront")
@Slf4j
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private OrderClient orderClient;

    //1 条件查询带分页查询课程
    @PostMapping("/getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable long page, @PathVariable long limit,
                                @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        Page<EduCourse> pageCourse = new Page<>(page,limit);
        Map<String,Object> map = courseService.getCourseFrontList(pageCourse,courseFrontVo);
        //返回分页所有数据
        return R.ok().data(map);
    }

    //2 课程详情的方法
    @GetMapping("/getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request) {
        //根据课程id，编写sql语句查询课程信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);

        //根据课程id查询章节和小节
        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);

        //查询该课程是否购买
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        log.info("memberId:{},courseId:{}",memberId,courseId);
        boolean isBuy=orderClient.isBuyCourse(courseId,memberId);
        courseWebVo.setBuy(isBuy);

        return R.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList);
    }

    //根据课程id查询课程信息
    @PostMapping("/getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable String id) {
        CourseWebVo courseInfo = courseService.getBaseCourseInfo(id);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(courseInfo,courseWebVoOrder);
        return courseWebVoOrder;
    }
}












