package com.wangkaiping.eduservice.client;

import com.wangkaiping.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class VdoClientImpl implements VdoClient{
    @Override
    public R deleteVideoByVideoId(String videoId) {
        System.out.println("执行了熔断器这里");
        return R.error().message("根据id删除视频出错了");
    }

    @Override
    public R deleteVideoMore(List<String> videoIds) {
        return R.error().message("删除多个视频出错了");
    }
}
