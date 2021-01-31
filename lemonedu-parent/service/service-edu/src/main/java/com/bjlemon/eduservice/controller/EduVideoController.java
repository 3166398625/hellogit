package com.bjlemon.eduservice.controller;


import com.bjlemon.commonutils.R;
import com.bjlemon.eduservice.client.VodClient;
import com.bjlemon.eduservice.entity.EduVideo;
import com.bjlemon.eduservice.service.EduVideoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author blackcat
 * @since 2021-01-21
 */
@RestController
@RequestMapping("/eduservice/video")
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;
    @Autowired
    private VodClient vodClient;

    //添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        videoService.save(eduVideo);
        return R.ok();
    }

    //删除小节
    // TODO 后面这个方法需要完善：删除小节时候，同时把里面视频删除
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id) {
        //根据小节id得到视频ID
        EduVideo eduVideo = videoService.getById(id);
        String videoSourceId=eduVideo.getVideoSourceId();
        if(!StringUtils.isEmpty(videoSourceId)){
            //远程调用
            vodClient.removeAlyVideo(eduVideo.getVideoSourceId());
        }

        videoService.removeById(id);
        return R.ok();
    }
}

