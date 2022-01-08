package com.wangkaiping.eduservice.controller;


import com.wangkaiping.commonutils.R;
import com.wangkaiping.eduservice.entity.vo.CourseInfoVo;
import com.wangkaiping.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        eduCourseService.addCourse(courseInfoVo);
        return R.ok();
    }


}

