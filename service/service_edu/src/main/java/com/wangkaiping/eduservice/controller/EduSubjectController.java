package com.wangkaiping.eduservice.controller;


import com.alibaba.excel.EasyExcel;
import com.wangkaiping.commonutils.R;
import com.wangkaiping.eduservice.entity.subject.OneSubject;
import com.wangkaiping.eduservice.listener.SubjectExcelListener;
import com.wangkaiping.eduservice.mapper.EduSubjectMapper;
import com.wangkaiping.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-01-07
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
@Api(description = "课程分类控制器")
public class EduSubjectController {
    @Autowired
    private EduSubjectService eduSubjectService;

    @PostMapping("/saveSubject")
    @ApiOperation("通过excel表格添加分类")
    public R saveSubject(MultipartFile file){
        eduSubjectService.saveSubject(file,eduSubjectService);
        return R.ok();
    }

    /*查询课程分类*/
    @GetMapping("/findAllSubject")
    @ApiOperation("查询课程分类")
    public R findAllSubject(){
        List<OneSubject> oneSubjectList = eduSubjectService.findAllSubject();
        return R.ok().data("list",oneSubjectList);
    }

}

