package com.guli.easyExcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

public class ExcelListener extends AnalysisEventListener<ReadData> {

    //一行一行读取excel内容
    @Override
    public void invoke(ReadData readData, AnalysisContext analysisContext) {
    //readData
        System.out.println(readData);
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头"+headMap);
    }

    //读取完成后
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

        System.out.println("完成了");
    }
}
