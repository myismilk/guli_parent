package com.wangkaiping.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangkaiping.eduservice.entity.EduTeacher;
import com.wangkaiping.eduservice.mapper.EduTeacherMapper;
import com.wangkaiping.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-12-23
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public HashMap<String, Object> getFrontTeacherByPage(Page<EduTeacher> teacherPage) {
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        baseMapper.selectPage(teacherPage,queryWrapper);
        long total = teacherPage.getTotal();
        List<EduTeacher> records = teacherPage.getRecords();
        long size = teacherPage.getSize();
        boolean hasNext = teacherPage.hasNext();
        boolean hasPrevious = teacherPage.hasPrevious();
        long current = teacherPage.getCurrent();
        long pages = teacherPage.getPages();
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
}
