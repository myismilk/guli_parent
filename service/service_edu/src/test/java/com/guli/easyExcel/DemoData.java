package com.guli.easyExcel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class DemoData {
    @ExcelProperty("学生编号")
    private Integer no;
    @ExcelProperty("学生姓名")
    private String name;
}
