package com.wangkaiping.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangkaiping.eduservice.controller.front.CourseFrontController;
import com.wangkaiping.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wangkaiping.eduservice.entity.vo.CourseInfoVo;
import com.wangkaiping.eduservice.entity.vo.CourseQueryVo;
import com.wangkaiping.eduservice.entity.vo.FrontCourseInfoVo;
import com.wangkaiping.eduservice.entity.vo.PublicshCourseInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-01-08
 */
public interface EduCourseService extends IService<EduCourse> {

    String addCourse(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    String updateCourse(CourseInfoVo courseInfoVo);

    PublicshCourseInfo getPublishCourseInfo(String courseId);

    void deleteCourseById(String courseId);

    List<EduCourse> getCourseInfoByTeacherId(String id);

    Map<String, Object> getCourseListByPageAndWhere(Page<EduCourse> coursePage, CourseQueryVo courseQueryVo);

    FrontCourseInfoVo getFrontCourseInfo(String courseId);
}
