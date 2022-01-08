package com.wangkaiping.eduservice.service;

import com.wangkaiping.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wangkaiping.eduservice.entity.vo.CourseInfoVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-01-08
 */
public interface EduCourseService extends IService<EduCourse> {

    void addCourse(CourseInfoVo courseInfoVo);
}
