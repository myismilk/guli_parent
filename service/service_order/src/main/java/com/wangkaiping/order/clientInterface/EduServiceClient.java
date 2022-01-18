package com.wangkaiping.order.clientInterface;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


//远程调用eduService的接口
@FeignClient("service-edu")
@Component
public interface EduServiceClient {
    @PostMapping("/eduservice/coursefront/getCourseInfoY/{courseId}")
    public com.wangkaiping.commonutils.EntityVo.FrontCourseInfoVo getCourseInfoY(@PathVariable("courseId") String courseId);
}
