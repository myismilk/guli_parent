package com.wangkaiping.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangkaiping.eduservice.controller.front.CourseFrontController;
import com.wangkaiping.eduservice.entity.EduChapter;
import com.wangkaiping.eduservice.entity.EduCourse;
import com.wangkaiping.eduservice.entity.EduCourseDescription;
import com.wangkaiping.eduservice.entity.EduTeacher;
import com.wangkaiping.eduservice.entity.vo.CourseInfoVo;
import com.wangkaiping.eduservice.entity.vo.CourseQueryVo;
import com.wangkaiping.eduservice.entity.vo.FrontCourseInfoVo;
import com.wangkaiping.eduservice.entity.vo.PublicshCourseInfo;
import com.wangkaiping.eduservice.mapper.EduCourseMapper;
import com.wangkaiping.eduservice.service.EduChapterService;
import com.wangkaiping.eduservice.service.EduCourseDescriptionService;
import com.wangkaiping.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangkaiping.eduservice.service.EduVideoService;
import com.wangkaiping.servicebase.exception.MyExceptionOne;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-01-08
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    //操作课程简介的信息表的对象
    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;
    //操作小节表的对象
    @Autowired
    private EduVideoService eduVideoService;

    //删除章节表的对象
    @Autowired
    private EduChapterService eduChapterService;
    /**
     * 这里进行课程相关信息的添加
     * @param courseInfoVo
     */
    @Override
    public String addCourse(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();

        //将都有的属性   将courseInfoVo对象的赋值给eduCourse对象
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if(insert ==0){
            //添加失败
            throw new MyExceptionOne(20001,"课程信息添加失败");
        }

        //如果添加课程添加成功，就下来加简介信息
        String id = eduCourse.getId();
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescription.setId(id);
        eduCourseDescriptionService.save(eduCourseDescription);

        return eduCourse.getId();
    }

    //根据courseId查询课程相关信息
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        CourseInfoVo courseInfoVo = new CourseInfoVo();
         //查询course表中的信息
        EduCourse eduCourse = baseMapper.selectById(courseId);
        //填充
        BeanUtils.copyProperties(eduCourse,courseInfoVo);

        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getById(courseId);
        BeanUtils.copyProperties(eduCourseDescription,courseInfoVo);
        return courseInfoVo;
    }

    //更改课程相关
    @Override
    public String updateCourse(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if(update==0){
            throw new MyExceptionOne(20001,"course表更改失败");
        }

        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(courseInfoVo.getId());
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        boolean b = eduCourseDescriptionService.updateById(eduCourseDescription);
        if (!b){
            throw new MyExceptionOne(20001,"courseDescription表更改失败");
        }
        return eduCourse.getId();
    }

    //获得完成了课程添加的全部信息的课程
    @Override
    public PublicshCourseInfo getPublishCourseInfo(String courseId) {
        PublicshCourseInfo publicshCourseInfo = baseMapper.getPublishCourseInfo(courseId);
        return publicshCourseInfo;
    }


    //根据课程id删除课程
    @Override
    public void deleteCourseById(String courseId) {
        //删除课程之前要删除对应的其他信息
        //删除小节
        eduVideoService.removeByCourseId(courseId);
        //删除章节
        eduChapterService.removeByCourseId(courseId);
        //删除课程简介
        eduCourseDescriptionService.removeByCourseId(courseId);
        //删除课程
        baseMapper.deleteById(courseId);
    }

    @Override
    public List<EduCourse> getCourseInfoByTeacherId(String id) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id",id);
        List<EduCourse> courseList = baseMapper.selectList(queryWrapper);
        return courseList;
    }

    //条件查询课程
    @Override
    public Map<String, Object> getCourseListByPageAndWhere(Page<EduCourse> coursePage, CourseQueryVo courseQueryVo) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(courseQueryVo.getSubjectParentId())){
            queryWrapper.eq("subject_parent_id",courseQueryVo.getSubjectParentId());
        }
        if(!StringUtils.isEmpty(courseQueryVo.getSubjectId())){
            queryWrapper.eq("subject_id",courseQueryVo.getSubjectId());
        }
        if(!StringUtils.isEmpty(courseQueryVo.getBuyCountSort())){
            queryWrapper.orderByDesc("buy_count");
        }
        if(!StringUtils.isEmpty(courseQueryVo.getGmtCreateSort())){
            queryWrapper.orderByDesc("gmt_create");
        }
        if(!StringUtils.isEmpty(courseQueryVo.getPriceSort())){
            queryWrapper.orderByDesc("price");
        }
        baseMapper.selectPage(coursePage,queryWrapper);
        long total = coursePage.getTotal();
        List<EduCourse> records = coursePage.getRecords();
        long size = coursePage.getSize();
        boolean hasNext = coursePage.hasNext();
        boolean hasPrevious = coursePage.hasPrevious();
        long current = coursePage.getCurrent();
        long pages = coursePage.getPages();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("total",total);
        map.put("records",records);
        map.put("size",size);
        map.put("hasNext",hasNext);
        map.put("hasPrevious",hasPrevious);
        map.put("current",current);
        map.put("pages",pages);
        return map;
    }

    //根据id获取课程的混合信息
    @Override
    public FrontCourseInfoVo getFrontCourseInfo(String courseId) {
        FrontCourseInfoVo frontCourseInfo = baseMapper.getFrontCourseInfo(courseId);
        return frontCourseInfo;
    }


}
