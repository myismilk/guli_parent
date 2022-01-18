package com.wangkaiping.eduservice.test;

import com.alibaba.excel.EasyExcel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ExcelTest {
    public static void main(String[] args) {
        File file = new File("C:\\1awangkaipingoffile\\test\\testone.xlsx");
        List<Student> studentList = new ArrayList<>();
        for (int i = 0;i<10;i++){
            Student student = new Student();
            student.setName("laowang1");
            student.setSex("男");
            student.setAddress("广东");
            studentList.add(student);
        }
        for (int i = 0;i<10;i++){
            Student student = new Student();
            student.setName("juju1");
            student.setSex("女");
            student.setAddress("四川");
            studentList.add(student);
        }

        EasyExcel.write(file,Student.class).sheet("学生信息").doWrite(studentList);

    }
}
