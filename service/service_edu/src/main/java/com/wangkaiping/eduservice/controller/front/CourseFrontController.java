package com.wangkaiping.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangkaiping.commonutils.R;
import com.wangkaiping.eduservice.entity.EduChapter;
import com.wangkaiping.eduservice.entity.EduCourse;
import com.wangkaiping.eduservice.entity.chapter.ChapterVo;
import com.wangkaiping.eduservice.entity.vo.CourseQueryVo;
import com.wangkaiping.eduservice.entity.vo.FrontCourseInfoVo;
import com.wangkaiping.eduservice.service.EduChapterService;
import com.wangkaiping.eduservice.service.EduCourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin
public class CourseFrontController {
    @Autowired
    private EduCourseService eduCourseService;
    @Autowired
    private EduChapterService eduChapterService;

    //条件查询加分页获取课程列表
    @PostMapping("/getCourseListByPageAndWhere/{page}/{limit}")
    public R getCourseListByPageAndWhere(@PathVariable("page") long page,@PathVariable("limit") long limit,
                                         @RequestBody(required = false) CourseQueryVo courseQueryVo){
        Page<EduCourse> coursePage = new Page<>(page,limit);
        Map<String,Object> map = eduCourseService.getCourseListByPageAndWhere(coursePage,courseQueryVo);
        return R.ok().data(map);
    }

    //根据课程id获取课程的混合详细信息
    @GetMapping("/getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable("courseId") String courseId){
        FrontCourseInfoVo frontCourseInfoVo = eduCourseService.getFrontCourseInfo(courseId);
        List<ChapterVo> chapterVoList = eduChapterService.getChapterVideo(courseId);
        return R.ok().data("courseInfo",frontCourseInfoVo).data("chapterList",chapterVoList);
    }

    //远程调用根据课程id获取课程的信息
    @PostMapping("/getCourseInfoY/{courseId}")
    public com.wangkaiping.commonutils.EntityVo.FrontCourseInfoVo getCourseInfoY(@PathVariable("courseId") String courseId){
        FrontCourseInfoVo frontCourseInfoVo = eduCourseService.getFrontCourseInfo(courseId);
        com.wangkaiping.commonutils.EntityVo.FrontCourseInfoVo frontCourseInfoVo1 = new com.wangkaiping.commonutils.EntityVo.FrontCourseInfoVo();
        BeanUtils.copyProperties(frontCourseInfoVo,frontCourseInfoVo1);
        return frontCourseInfoVo1;
    }

}
