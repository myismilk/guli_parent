package com.wangkaiping.eduservice.controller;


import com.wangkaiping.commonutils.R;
import com.wangkaiping.eduservice.entity.EduChapter;
import com.wangkaiping.eduservice.entity.chapter.ChapterVo;
import com.wangkaiping.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-01-08
 */
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {
    @Autowired
    private EduChapterService eduChapterService;

    //获得所有的章节和小节
    @GetMapping("/getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable("courseId") String course){
        List<ChapterVo> chapterVoList = eduChapterService.getChapterVideo(course);
        return R.ok().data("allChapterVideo",chapterVoList);
    }

    //添加章节
    @PostMapping("/addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.save(eduChapter);
        return R.ok();
    }

    //修改章节
    @PostMapping("/updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.updateById(eduChapter);
        return R.ok();
    }

    //根据章节id查章节
    @GetMapping("/getChapterById/{chapterId}")
    public R getChapterById(@PathVariable String chapterId){
        EduChapter eduChapter = eduChapterService.getById(chapterId);
        return R.ok().data("chapter",eduChapter);
    }
    //根据章节id删除章节
    @DeleteMapping("/deleteChapterById/{chapterId}")
    public R deleteChapterById(@PathVariable String chapterId){
        eduChapterService.removeById(chapterId);
        return R.ok();
    }

    //根据章节id删除章节,如果章节内有小节就不能删除
    @DeleteMapping("/deleteChapterByIdW/{chapterId}")
    public R deleteChapterByIdW(@PathVariable String chapterId){
        boolean flag = eduChapterService.deleteChapterByIdW(chapterId);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }

    }
}

