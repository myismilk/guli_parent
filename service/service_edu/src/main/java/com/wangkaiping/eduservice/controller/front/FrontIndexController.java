package com.wangkaiping.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wangkaiping.commonutils.R;
import com.wangkaiping.eduservice.entity.EduCourse;
import com.wangkaiping.eduservice.entity.EduTeacher;
import com.wangkaiping.eduservice.service.EduCourseService;
import com.wangkaiping.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/eduservice/index")
public class FrontIndexController {
    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduTeacherService eduTeacherService;

    //查询8条课程，4名名师的请求
    @Cacheable(value = "courseAndTeacher",key = "'courseAndTeacherList'")
    @GetMapping("/getCourseAndTeacher")
    public R getCourseAndTeacher(){
        System.out.println("进入到了getCourseAndTeacher");
        //查询8门热门课程
        QueryWrapper<EduCourse> eduCourseQueryWrapper = new QueryWrapper<>();
        eduCourseQueryWrapper.orderByDesc("view_count");
        eduCourseQueryWrapper.last("limit 8");
        List<EduCourse> eduCourseList = eduCourseService.list(eduCourseQueryWrapper);

        //查询4个名师
        QueryWrapper<EduTeacher> eduTeacherQueryWrapper = new QueryWrapper<>();
        eduTeacherQueryWrapper.orderByDesc("id");
        eduTeacherQueryWrapper.last("limit 4");
        List<EduTeacher> eduTeacherList = eduTeacherService.list(eduTeacherQueryWrapper);
        return R.ok().data("eduCourseList",eduCourseList).data("eduTeacherList",eduTeacherList);
    }

}
