package com.wangkaiping.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wangkaiping.eduservice.client.VdoClient;
import com.wangkaiping.eduservice.entity.EduVideo;
import com.wangkaiping.eduservice.mapper.EduVideoMapper;
import com.wangkaiping.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-01-08
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VdoClient vdoClient;
    @Override
    public void removeByCourseId(String courseId) {
        System.out.println("进入到删除小节处");
        //删除小节，也要删除对应的视频
        //根据课程id查出所有的小节
        QueryWrapper<EduVideo> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("course_id",courseId);
        queryWrapper2.select("video_source_id");
        List<EduVideo> eduVideos = baseMapper.selectList(queryWrapper2);
        List<String> videoIds = new ArrayList<>();
        for (EduVideo eduVideo:eduVideos){
            if(!StringUtils.isEmpty(eduVideo.getVideoSourceId())){
                videoIds.add(eduVideo.getVideoSourceId());
            }
        }
        if(videoIds.size()>0){
            System.out.println("String数组："+videoIds.toString());
            vdoClient.deleteVideoMore(videoIds);
        }


        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("course_id",courseId);
        baseMapper.delete(queryWrapper);
    }
}
