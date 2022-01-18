package com.wangkaiping.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wangkaiping.eduservice.entity.EduChapter;
import com.wangkaiping.eduservice.entity.EduVideo;
import com.wangkaiping.eduservice.entity.chapter.ChapterVo;
import com.wangkaiping.eduservice.entity.chapter.VideoVo;
import com.wangkaiping.eduservice.mapper.EduChapterMapper;
import com.wangkaiping.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangkaiping.eduservice.service.EduVideoService;
import com.wangkaiping.servicebase.exception.MyExceptionOne;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-01-08
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public List<ChapterVo> getChapterVideo(String course) {
        //创建一个存放章节的集合
        List<ChapterVo> chapterVoList = new ArrayList<>();

        //获得课程所有的章节
        QueryWrapper<EduChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",course);
        queryWrapper.orderByAsc("sort");
        List<EduChapter> eduChapterList = baseMapper.selectList(queryWrapper);

        //获得课程所有的小节
        QueryWrapper<EduVideo> queryWrapper2 = new QueryWrapper<>();
        List<EduVideo> eduVideoList = eduVideoService.list(queryWrapper2);

        //遍历章节集合和小节集合进行整理。
        for (EduChapter eduChapter:eduChapterList) {
            ChapterVo chapterVo = new ChapterVo();
            //将查出来的章节的id，和title给到对应的Vo类
            BeanUtils.copyProperties(eduChapter,chapterVo);

            //创建一个放小节的集合
            List<VideoVo> videoVoList = new ArrayList<>();
            //遍历小节集合
            for (EduVideo eduVideo:eduVideoList) {
                //如果这个小节中的的chapter_id和这个章节的id相等，则，这个小节就属于这个章节，进行整理
                if(eduVideo.getChapterId().equals(eduChapter.getId())){
                    VideoVo videoVo = new VideoVo();
                    //将查出来的小节的id，和title给到对应的Vo类
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    //再将这个小节加入到小节的集合中
                    videoVoList.add(videoVo);
                }
            }
            //遍历完后，将小节的集合放到章节对象中
            chapterVo.setChildren(videoVoList);
            //将这个章节对象放到章节集合中
            chapterVoList.add(chapterVo);
        }
        return chapterVoList;
    }

    //删除章节，如果章节中有小节就不删除
    @Override
    public boolean deleteChapterByIdW(String chapterId) {
        System.out.println("chapter:"+chapterId);
        QueryWrapper<EduVideo> eduVideoQueryWrapper = new QueryWrapper<>();
        eduVideoQueryWrapper.eq("chapter_id",chapterId);
        int count = eduVideoService.count(eduVideoQueryWrapper);
        System.out.println("count"+count);
        //有小节存在，不能删除
        if (count>0){
            throw new MyExceptionOne(20001,"存在有小节，无法删除");
        }
        int result = baseMapper.deleteById(chapterId);
        return result>0;
    }

    @Override
    public void removeByCourseId(String courseId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("course_id",courseId);
        baseMapper.delete(queryWrapper);
    }
}
