package com.wangkaiping.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangkaiping.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-12-23
 */
public interface EduTeacherService extends IService<EduTeacher> {

    HashMap<String, Object> getFrontTeacherByPage(Page<EduTeacher> teacherPage);
}
