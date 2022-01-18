package com.wangkaiping.eduservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChapterVo {
    //章节id
    private String id;
    //章节名称
    private String title;
    //小节集合
    private List<VideoVo> children = new ArrayList<>();
}
