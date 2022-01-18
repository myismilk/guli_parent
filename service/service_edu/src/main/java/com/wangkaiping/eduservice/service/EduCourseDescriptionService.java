package com.wangkaiping.eduservice.service;

import com.wangkaiping.eduservice.entity.EduCourseDescription;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程简介 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-01-08
 */
public interface EduCourseDescriptionService extends IService<EduCourseDescription> {

    void removeByCourseId(String courseId);
}
