package com.wangkaiping.eduservice.test;

import com.alibaba.excel.EasyExcel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ExcelTestRead {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("C:\\1awangkaipingoffile\\test\\testone.xlsx");
        InputStream in = new FileInputStream(file);
        EasyExcel.read(in,Student.class,new ExcelListener()).sheet().doRead();
    }
}
