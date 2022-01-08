package com.guli.easyExcel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ReadData {
    @ExcelProperty(value = "学生姓名",index = 0)
    private Integer no;
    @ExcelProperty(value = "学生姓名",index = 1)
    private String name;
}
