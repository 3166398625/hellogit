package com.bjlemon.eduservice.service;

import com.bjlemon.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bjlemon.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author blackcat
 * @since 2021-01-21
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    boolean deleteChapter(String chapterId);

    void removeChapterByCourseId(String courseId);
}
