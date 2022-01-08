package com.wangkaiping.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wangkaiping.eduservice.entity.EduSubject;
import com.wangkaiping.eduservice.entity.excel.SubjectData;
import com.wangkaiping.eduservice.service.EduSubjectService;
import com.wangkaiping.servicebase.exception.MyExceptionOne;

import java.util.Map;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {
    public EduSubjectService eduSubjectService;
    public SubjectExcelListener(){

    }
    public SubjectExcelListener(EduSubjectService eduSubjectService){
        this.eduSubjectService = eduSubjectService;
    }


    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        //如果subject是空，抛出异常
        if(subjectData==null){
            throw new MyExceptionOne(20001,"文件是空的");
        }


        //获得一级目录的名称
        String oneTitle = subjectData.getOneSubjectName();
        //通过方法判断一级目录是否存在
        EduSubject eduSubject = existsOneSubject(oneTitle);
        if(eduSubject==null){
            //一级目录不存在，添加一级目录
            eduSubject = new EduSubject();
            eduSubject.setTitle(oneTitle);
            eduSubject.setParentId("0");
            eduSubjectService.save(eduSubject);
        }

        //获取二级目录
        String twoTitle = subjectData.getTwoSubjectName();
        //判断二级目录是否存在,参数包括二级目录的标题，和他的一级标题的id。
        //如果一级目录本身存在的话，就会返回给eduSubject，如果不存在，在执行数据添加的会填充上去
        EduSubject eduSubject2 = existsTwoSubject(subjectData.getTwoSubjectName(),eduSubject.getId());
        if(eduSubject2==null){
            //二级目录不存在，添加二级目录
            eduSubject2 = new EduSubject();
            eduSubject2.setTitle(twoTitle);
            eduSubject2.setParentId(eduSubject.getId());
            eduSubjectService.save(eduSubject2);
        }
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头："+headMap);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("完成了");
    }

    //判断这个一级目录是否存在的方法
    private EduSubject existsOneSubject(String oneTitle){
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title",oneTitle);
        queryWrapper.eq("parent_id","0");
        EduSubject eduSubject = eduSubjectService.getOne(queryWrapper);
        return eduSubject;
    }
    //判断这个二级目录是否存在的方法
    private EduSubject existsTwoSubject(String twoTitle,String parentId){
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title",twoTitle);
        queryWrapper.eq("parent_id",parentId);
        EduSubject eduSubject = eduSubjectService.getOne(queryWrapper);
        return eduSubject;
    }
}
