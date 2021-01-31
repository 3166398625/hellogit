package com.bjlemon.eduservice.client;

import com.bjlemon.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class VodFileDegradeFeignClient implements VodClient {

    @Override
    public R removeAlyVideo(String id) {
        log.info("熔断器触发");
        return R.error().message("删除视频失败");
    }

    @Override
    public R deleteBatch(List<String> videoIdList) {
        log.info("熔断器触发");
        return R.error().message("批量删除视频失败");
    }
}
