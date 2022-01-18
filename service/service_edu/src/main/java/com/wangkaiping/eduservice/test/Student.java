package com.wangkaiping.eduservice.test;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class Student {
    @ExcelProperty(value = "学生姓名",index = 0)
    private String name;
    @ExcelProperty(value = "学生性别",index = 1)
    private String sex;
    @ExcelProperty(value = "学生住址",index = 2)
    private String address;
}
