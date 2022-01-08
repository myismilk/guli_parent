package com.wangkaiping.eduservice.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wangkaiping.eduservice.entity.EduCourse;
import com.wangkaiping.eduservice.entity.EduCourseDescription;
import com.wangkaiping.eduservice.entity.vo.CourseInfoVo;
import com.wangkaiping.eduservice.mapper.EduCourseMapper;
import com.wangkaiping.eduservice.service.EduCourseDescriptionService;
import com.wangkaiping.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangkaiping.servicebase.exception.MyExceptionOne;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    /**
     * 这里进行课程相关信息的添加
     * @param courseInfoVo
     */
    @Override
    public void addCourse(CourseInfoVo courseInfoVo) {
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


    }
}
