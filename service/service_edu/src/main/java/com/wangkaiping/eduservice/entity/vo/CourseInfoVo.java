package com.wangkaiping.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CourseInfoVo {
    @ApiModelProperty("课程Id")
    private String id;
    @ApiModelProperty("课程讲师Id")
    private String teacherId;
    @ApiModelProperty("课程专业Id")
    private String subject;
    @ApiModelProperty("课程标题")
    private String title;
    @ApiModelProperty("课程销售价格")
    private BigDecimal price;
    @ApiModelProperty("总课时")
    private Integer lessonNum;
    @ApiModelProperty("课程封面图片路径")
    private String cover;
    @ApiModelProperty("课程简介")
    private String description;

}
