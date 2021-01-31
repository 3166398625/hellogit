package com.bjlemon.eduservice.client;

import com.bjlemon.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderDegradeFeignClient implements OrderClient {
    @Override
    public boolean isBuyCourse(String courseId, String memberId) {
        log.info("熔断器触发，获取课程是否购买---失败");
        return false;
    }
}
