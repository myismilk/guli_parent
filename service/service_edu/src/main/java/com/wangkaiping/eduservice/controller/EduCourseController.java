package com.wangkaiping.eduservice.controller;


import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangkaiping.commonutils.R;
import com.wangkaiping.eduservice.entity.EduCourse;
import com.wangkaiping.eduservice.entity.vo.CourseInfoVo;
import com.wangkaiping.eduservice.entity.vo.CourseQuery;
import com.wangkaiping.eduservice.entity.vo.PublicshCourseInfo;
import com.wangkaiping.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-01-08
 */
@RestController
@CrossOrigin
@RequestMapping("/eduservice/course")
@Api(description = "课程信息控制器")
public class EduCourseController {
    @Autowired
    private EduCourseService eduCourseService;

    @ApiOperation("添加课程信息")
    @PostMapping("/addCourse")
    public R addCourse(@RequestBody CourseInfoVo courseInfoVo){
        String courseId = eduCourseService.addCourse(courseInfoVo);
        return R.ok().data("courseId",courseId);
    }
    @ApiOperation("根据courseId查询课程相关信息")
    @GetMapping("/getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable("courseId") String courseId){
        CourseInfoVo courseInfoVo = eduCourseService.getCourseInfo(courseId);
        return R.ok().data("courseInfo",courseInfoVo);
    }

    @ApiOperation("修改课程")
    @PostMapping("/updateCourse")
    public R updateCourse(@RequestBody CourseInfoVo courseInfoVo){
        String courseId = eduCourseService.updateCourse(courseInfoVo);
        return R.ok().data("courseId",courseId);
    }

    //获得完成了课程添加的全部信息的课程
    @GetMapping("/getPublishCourseInfo/{courseId}")
    public R getPublishCourseInfo(@PathVariable String courseId){
        PublicshCourseInfo publicshCourseInfo = eduCourseService.getPublishCourseInfo(courseId);
        return R.ok().data("coursePublishInfo",publicshCourseInfo);
    }

    //课程发布的功能：
    @PostMapping("/publishCourse/{courseId}")
    public R publishCourse(@PathVariable String courseId){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus("Normal");
        eduCourseService.updateById(eduCourse);
        return R.ok();
    }

    //条件分页查询获得所有课程
    @PostMapping("/getAllCourseByPageWhere/{current}/{limit}")
    public R getAllCourseByPage(@PathVariable long current,@PathVariable long limit,@RequestBody (required = false) CourseQuery courseQuery){
        Page<EduCourse> eduCoursePage = new Page<>(current,limit);
        QueryWrapper<EduCourse> eduCourseQueryWrapper = new QueryWrapper<>();
        String id = courseQuery.getId();
        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();
        if (!StringUtils.isEmpty(id)){
            System.out.println("执行了id");
            eduCourseQueryWrapper.eq("id",id);
        }
        if(!StringUtils.isEmpty(title)){
            System.out.println("执行了title");
            eduCourseQueryWrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(status)){
            System.out.println("执行了status");
            eduCourseQueryWrapper.eq("status",status);
        }
        eduCourseQueryWrapper.orderByDesc("gmt_create");
        eduCourseService.page(eduCoursePage,eduCourseQueryWrapper);
        long total = eduCoursePage.getTotal();
        List<EduCourse> courseList = eduCoursePage.getRecords();
        return R.ok().data("total",total).data("courseList",courseList);
    }

    //根据课程id删除课程
    @DeleteMapping("/deleteCourseById/{courseId}")
    public R deleteCourseById(@PathVariable String courseId){
        eduCourseService.deleteCourseById(courseId);
        return R.ok();
    }



}

