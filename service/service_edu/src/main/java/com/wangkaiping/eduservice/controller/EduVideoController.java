package com.wangkaiping.eduservice.controller;


import com.wangkaiping.commonutils.R;
import com.wangkaiping.eduservice.client.VdoClient;
import com.wangkaiping.eduservice.entity.EduVideo;
import com.wangkaiping.eduservice.service.EduVideoService;
import com.wangkaiping.servicebase.exception.MyExceptionOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-01-08
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {
    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private VdoClient vdoClient;

    //添加小节
    @PostMapping("/addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.save(eduVideo);
        return R.ok();
    }

    //删除小节
    @DeleteMapping("/deleteVideoById/{videoId}")
    public R deleteVideoById(@PathVariable String videoId){
        System.out.println("来到了edu删除小节");
        //删除小节之前要去删除阿里云上的视频
        //首先根据小节的id查询到视频的id
        EduVideo video = eduVideoService.getById(videoId);
        //获得该课程所以视频id
        String videoSourceId = video.getVideoSourceId();
        //判断该小节的视频是否有
        if (videoSourceId!=null){
            //删除视频
            R r = vdoClient.deleteVideoByVideoId(videoSourceId);
        }
        //删除小节
        eduVideoService.removeById(videoId);
        return R.ok();
    }

    //修改小节
    @PostMapping("/updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.updateById(eduVideo);
        return R.ok();
    }

    //根据id查找video
    @GetMapping("/getVideoById/{videoId}")
    public R getVideoById(@PathVariable String videoId){
        EduVideo eduVideo= eduVideoService.getById(videoId);
        return R.ok().data("video",eduVideo);
    }
}

