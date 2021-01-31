package com.bjlemon.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjlemon.commonutils.ResultCode;
import com.bjlemon.eduservice.entity.EduChapter;
import com.bjlemon.eduservice.entity.EduVideo;
import com.bjlemon.eduservice.entity.chapter.ChapterVo;
import com.bjlemon.eduservice.entity.chapter.VideoVo;
import com.bjlemon.eduservice.mapper.EduChapterMapper;
import com.bjlemon.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjlemon.eduservice.service.EduVideoService;
import com.bjlemon.servicebase.exceptionhandler.LemonException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author blackcat
 * @since 2021-01-21
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //根据课程ID查询对应的所有章节
        QueryWrapper<EduChapter> wrapper=new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        List<EduChapter> chapters = baseMapper.selectList(wrapper);

        //根据课程ID查询对应的所有小节
        QueryWrapper<EduVideo> wrapper1=new QueryWrapper<>();
        wrapper1.eq("course_id",courseId);
        List<EduVideo> videos = videoService.list(wrapper1);

        //创建list集合，用于最终封装数据
        List<ChapterVo> finalList = new ArrayList<>();

        //3 遍历查询章节list集合进行封装
        //遍历查询章节list集合
        for (int i = 0; i < chapters.size(); i++) {
            //每个章节
            EduChapter eduChapter = chapters.get(i);
            //eduChapter对象值复制到ChapterVo里面
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            //把chapterVo放到最终list集合
            finalList.add(chapterVo);

            //创建集合，用于封装章节的小节
            List<VideoVo> videoList = new ArrayList<>();

            //4 遍历查询小节list集合，进行封装
            for (int m = 0; m < videos.size(); m++) {
                //得到每个小节
                EduVideo eduVideo = videos.get(m);
                //判断：小节里面chapterid和章节里面id是否一样
                if(eduVideo.getChapterId().equals(eduChapter.getId())) {
                    //进行封装
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    //放到小节封装集合
                    videoList.add(videoVo);
                }
            }
            //把封装之后小节list集合，放到章节对象里面
            chapterVo.setChildren(videoList);
        }
        return finalList;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        //根据chapterid章节id 查询小节表，如果查询出来数据，不进行删除
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = videoService.count(wrapper);
        //判断
        if(count >0) {//查询出小节，不进行删除
            throw new LemonException(ResultCode.ERROR,"不能删除");
        } else { //不能查询数据，进行删除
            //删除章节
            int result = baseMapper.deleteById(chapterId);
            //成功  1>0   0>0
            return result>0;
        }
    }

    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
