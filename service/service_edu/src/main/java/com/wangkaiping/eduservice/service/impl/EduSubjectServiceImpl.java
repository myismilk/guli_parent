package com.wangkaiping.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wangkaiping.eduservice.entity.EduSubject;
import com.wangkaiping.eduservice.entity.excel.SubjectData;
import com.wangkaiping.eduservice.entity.subject.OneSubject;
import com.wangkaiping.eduservice.entity.subject.TwoSubject;
import com.wangkaiping.eduservice.listener.SubjectExcelListener;
import com.wangkaiping.eduservice.mapper.EduSubjectMapper;
import com.wangkaiping.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-01-07
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file,EduSubjectService eduSubjectService) {
        try{
        InputStream in = file.getInputStream();
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> findAllSubject() {
        //创建一级课程分类集合
        List<OneSubject> oneSubjectList = new ArrayList<>();
        //查到一级分类
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id","0");
        //得到一级分类
        List<EduSubject> eduSubjectList = baseMapper.selectList(queryWrapper);
        //遍历一级分类得到OneSubject集合
        for (EduSubject eduSubject:eduSubjectList){
            OneSubject oneSubject = new OneSubject();
            oneSubject.setId(eduSubject.getId());
            oneSubject.setTitle(eduSubject.getTitle());
            //根据一级分类的id去查询对应的二级分类
            QueryWrapper<EduSubject> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("parent_id",eduSubject.getId());
            //得到二级分类
            List<EduSubject> eduSubjectList2 = baseMapper.selectList(queryWrapper2);
            //遍历二级分类List得到TwoSubjectList
            List<TwoSubject> twoSubjectList = new ArrayList<>();
            for(EduSubject eduSubject2:eduSubjectList2){
                TwoSubject twoSubject = new TwoSubject();
                twoSubject.setId(eduSubject2.getId());
                twoSubject.setTitle(eduSubject2.getTitle());
                twoSubjectList.add(twoSubject);
            }
            //将得到的twoSubjectList集合附给oneSubject的children属性
            oneSubject.setChildren(twoSubjectList);
            //将oneSubject加入到oneSubjectList中
            oneSubjectList.add(oneSubject);
        }


        return oneSubjectList;
    }
}
