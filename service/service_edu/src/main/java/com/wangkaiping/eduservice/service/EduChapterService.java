package com.wangkaiping.eduservice.service;

import com.wangkaiping.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wangkaiping.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-01-08
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideo(String course);

    boolean deleteChapterByIdW(String chapterId);

    void removeByCourseId(String courseId);
}
