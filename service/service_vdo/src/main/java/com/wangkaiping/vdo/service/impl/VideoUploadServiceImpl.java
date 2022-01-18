package com.wangkaiping.vdo.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.wangkaiping.vdo.service.VideoUploadService;
import com.wangkaiping.vdo.utils.InitClientObejct;
import com.wangkaiping.vdo.utils.VideoPropertiesUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

import static com.wangkaiping.vdo.utils.InitClientObejct.deleteVideo;

@Service
public class VideoUploadServiceImpl implements VideoUploadService {
    @Override
    public String videoUpload(MultipartFile file) {
        try {
            String accessKeyId = VideoPropertiesUtils.KEYID;
            String accessKeySecret = VideoPropertiesUtils.KEYSECRET;
            String fileName = file.getOriginalFilename();
            String title = file.getOriginalFilename().substring(0, fileName.lastIndexOf("."));
            InputStream inputStream = file.getInputStream();
            String videoId = "";
            UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
                System.out.print("ErrorCode=" + response.getCode() + "\n");
                System.out.print("ErrorMessage=" + response.getMessage() + "\n");
            }
            return videoId;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //同事删除多个视频的方法
    @Override
    public void deleteVideoMore(List<String> videoIds) {
        System.out.println("进入到删除多个视频");
        DeleteVideoRequest request = new DeleteVideoRequest();
        DeleteVideoResponse response = null;
        //支持传入多个视频ID，多个用逗号分隔
        String str = StringUtils.join(videoIds,",");
        System.out.println(str);
        request.setVideoIds(str);
        try {
            DefaultAcsClient client = InitClientObejct.initVodClient(VideoPropertiesUtils.KEYID, VideoPropertiesUtils.KEYSECRET);
            client.getAcsResponse(request);
            response = client.getAcsResponse(request);
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }
}
