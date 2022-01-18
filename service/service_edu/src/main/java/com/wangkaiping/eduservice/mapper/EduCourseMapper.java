package com.wangkaiping.eduservice.mapper;

import com.wangkaiping.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wangkaiping.eduservice.entity.vo.FrontCourseInfoVo;
import com.wangkaiping.eduservice.entity.vo.PublicshCourseInfo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2022-01-08
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    PublicshCourseInfo getPublishCourseInfo(String courseId);

    FrontCourseInfoVo getFrontCourseInfo(String courseId);
}
