package com.wangkaiping.vdo.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.wangkaiping.commonutils.R;
import com.wangkaiping.servicebase.exception.MyExceptionOne;
import com.wangkaiping.vdo.service.VideoUploadService;
import com.wangkaiping.vdo.utils.InitClientObejct;
import com.wangkaiping.vdo.utils.VideoPropertiesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.wangkaiping.vdo.utils.InitClientObejct.deleteVideo;

@RestController
@CrossOrigin
@RequestMapping("/vdoservice/video")
public class VideoUploadController {
    @Autowired
    private VideoUploadService videoUploadService;

    //上传视频的控制器
    @PostMapping("/videoUpload")
    public R videoUpload(MultipartFile file){
        String videoId = videoUploadService.videoUpload(file);
        return R.ok().data("videoId",videoId);
    }
    //删除阿里云视频的控制器
    @DeleteMapping("/deleteVideoByVideoId/{videoId}")
    public R deleteVideoByVideoId(@PathVariable("videoId") String videoId){
        DefaultAcsClient client = null;
        DeleteVideoResponse response = null;
        try {
            client = InitClientObejct.initVodClient(VideoPropertiesUtils.KEYID, VideoPropertiesUtils.KEYSECRET);
            response = new DeleteVideoResponse();
            response = deleteVideo(client,videoId);
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");

        return R.ok();
    }


    //删除多个视频的方法
    @DeleteMapping("/deleteVideoMore")
    public R deleteVideoMore(@RequestParam("videoIds") List<String> videoIds){
        System.out.println("执行了deleteVideoMore");
        videoUploadService.deleteVideoMore(videoIds);
        return R.ok();
    }

    //根据视频id获取视频凭证的方法
    @GetMapping("/getVideoPlayAuthById/{videoId}")
    public R getVideoPlayAuthById(@PathVariable("videoId") String videoId){
        try {
            //初始化
            DefaultAcsClient client = InitClientObejct.initVodClient(VideoPropertiesUtils.KEYID, VideoPropertiesUtils.KEYSECRET);

            //请求
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(videoId);

            //响应
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);

            //得到播放凭证
            String playAuth = response.getPlayAuth();

            //返回结果
            return R.ok().message("获取凭证成功").data("playAuth", playAuth);
        }catch (Exception e){
            throw new MyExceptionOne(20001,"凭证获取失败");
        }

    }
}
