package com.wangkaiping.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wangkaiping.eduservice.entity.EduCourseDescription;
import com.wangkaiping.eduservice.mapper.EduCourseDescriptionMapper;
import com.wangkaiping.eduservice.service.EduCourseDescriptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程简介 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-01-08
 */
@Service
public class EduCourseDescriptionServiceImpl extends ServiceImpl<EduCourseDescriptionMapper, EduCourseDescription> implements EduCourseDescriptionService {

    //根据课程id删除简介
    @Override
    public void removeByCourseId(String courseId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id",courseId);
        baseMapper.delete(queryWrapper);
    }
}
