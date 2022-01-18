package com.wangkaiping.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangkaiping.commonutils.R;
import com.wangkaiping.eduservice.entity.EduCourse;
import com.wangkaiping.eduservice.entity.EduTeacher;
import com.wangkaiping.eduservice.service.EduCourseService;
import com.wangkaiping.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/eduservice/teacherfront")
@CrossOrigin
public class TeacherFrontController {
    @Autowired
    private EduTeacherService eduTeacherService;
    @Autowired
    private EduCourseService eduCourseService;

    //分页查询讲师列表
    @GetMapping("/getFrontTeacherByPage/{page}/{limit}")
    public R getFrontTeacherByPage(@PathVariable("page") long page, @PathVariable("limit") long limit){
        Page<EduTeacher> teacherPage = new Page<>(page,limit);
        HashMap<String,Object> map = eduTeacherService.getFrontTeacherByPage(teacherPage);
        return R.ok().data(map);
    }

    //根据讲师id查询讲师的基本信息和课程
    @GetMapping("/getTeacherInfoById/{id}")
    public R getTeacherInfoById(@PathVariable("id") String id){
        EduTeacher teacher = eduTeacherService.getById(id);
        List<EduCourse> eduCourseList = eduCourseService.getCourseInfoByTeacherId(id);
        return R.ok().data("teacher",teacher).data("courseList",eduCourseList);
    }
}
