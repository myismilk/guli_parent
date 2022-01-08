package com.guli.easyExcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ExcelTest {
    public static void main(String[] args) {
        String fileName = "C:\\1awangkaipingoffile\\asometestproject\\testexcel.xlsx";
        /*List<DemoData> demoDataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData demoData = new DemoData();
            demoData.setNo(i);
            demoData.setName("laowang"+i);
            demoDataList.add(demoData);
        }
        EasyExcel.write(fileName,DemoData.class).sheet("学生信息").doWrite(demoDataList);*/
        EasyExcel.read(fileName,ReadData.class,new ExcelListener()).sheet().doRead();
    }
}
