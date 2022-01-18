package com.wangkaiping.eduservice.test;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

public class ExcelListener extends AnalysisEventListener<Student> {


    @Override
    public void invoke(Student student, AnalysisContext analysisContext) {
        //每读一行数据，就执行一次这里的代码,,,student就是读到的数据
        System.out.println("---------------");
        System.out.println(student);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("完成读文件");
    }
}
