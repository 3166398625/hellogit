package com.bjlemon.eduservice.service;

import com.bjlemon.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bjlemon.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author blackcat
 * @since 2021-01-20
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file,EduSubjectService subjectService);

    List<OneSubject> getAllOneTwoSubject();
}
