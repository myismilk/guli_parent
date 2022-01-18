package com.wangkaiping.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangkaiping.commonutils.R;
import com.wangkaiping.eduservice.entity.EduTeacher;
import com.wangkaiping.eduservice.entity.vo.TeacherQuery;
import com.wangkaiping.eduservice.mapper.EduTeacherMapper;
import com.wangkaiping.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-12-23
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/edu-teacher")
@CrossOrigin
public class EduTeacherController {
    @Autowired
    private EduTeacherService eduTeacherService;

    @ApiOperation("查找所有讲师")
    @GetMapping("/findAllTeacher")
    public R findAllTeacher(){
        List<EduTeacher> eduTeacherList = eduTeacherService.list(null);
        return R.ok().data("teachers", eduTeacherList);
    }


    @ApiOperation("逻辑删除讲师")
    @DeleteMapping("/removeTeacher/{id}")
    public R removeTeacher(@ApiParam("讲师的id") @PathVariable("id") String id){
        boolean flag = eduTeacherService.removeById(id);
        if(flag){
            return R.ok();
        }else{
            return R.error();
        }
    }


    @ApiOperation("根据分页查询讲师")
    @GetMapping("/findTeacherPage/{current}/{limit}")
    public R findTeacherPage(@ApiParam("当前页数")@PathVariable("current") long current,@ApiParam("每页的记录数")@PathVariable("limit") long limit){
        Page<EduTeacher> eduTeacherPage = new Page<>(current,limit);
        eduTeacherService.page(eduTeacherPage,null);
        long total = eduTeacherPage.getTotal();
        List<EduTeacher> teacherPageRecords = eduTeacherPage.getRecords();
        System.out.println(total);
        for (EduTeacher eduTeacher:teacherPageRecords){
            System.out.println(eduTeacher);
        }
        return R.ok().data("total",total).data("teacherPageRecords",teacherPageRecords);
    }


    @ApiOperation("条件查询分页")
    @PostMapping("/findTeacherPageByWhere/{current}/{limit}")
    public R findTeacherPageByWhere(@PathVariable("current") long current, @PathVariable("limit") long limit,
                                    @RequestBody(required = false) TeacherQuery teacherQuery){
        Page<EduTeacher> eduTeacherPage = new Page<>(current,limit);
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        String name = teacherQuery.getName();
        String level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if(!StringUtils.isEmpty(name)){
            queryWrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)){
            queryWrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)){
            queryWrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            queryWrapper.le("gmt_create",end);
        }
        queryWrapper.orderByDesc("gmt_create");
        eduTeacherService.page(eduTeacherPage,queryWrapper);
        long total = eduTeacherPage.getTotal();
        List<EduTeacher> teacherPageRecords = eduTeacherPage.getRecords();
        return R.ok().data("total",total).data("teacherPageRecords",teacherPageRecords);
    }

    /*
    添加讲师的接口方法
    * */
    @ApiOperation("添加讲师的方法")
    @PostMapping("/addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);
        return save?R.ok():R.error();
    }

    /*根据id查讲师*/
    @ApiOperation("根据id查讲师")
    @PostMapping("/getTeacherById/{id}")
    public R getTeacherById(@PathVariable("id") String id){
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok().data("teacher",eduTeacher);
    }

    @ApiOperation("更改讲师")
    @PostMapping("/updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean flag = eduTeacherService.updateById(eduTeacher);
        if(flag){
            return R.ok();
        }else{
            return R.error();
        }
    }


}

