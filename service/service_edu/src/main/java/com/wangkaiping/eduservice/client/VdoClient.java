package com.wangkaiping.eduservice.client;


import com.wangkaiping.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "service-vdo",fallback = VdoClientImpl.class)
@Component
public interface VdoClient {
    @DeleteMapping("/vdoservice/video/deleteVideoByVideoId/{videoId}")
    public R deleteVideoByVideoId(@PathVariable("videoId") String videoId);
    @DeleteMapping("/vdoservice/video/deleteVideoMore")
    public R deleteVideoMore(@RequestParam("videoIds")List<String> videoIds);
}
