package com.wangkaiping.eduservice.service;

import com.wangkaiping.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wangkaiping.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-01-07
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file,EduSubjectService eduSubjectService);
    List<OneSubject> findAllSubject();
}
