package com.wangkaiping.oss.controller;

import com.wangkaiping.commonutils.R;
import com.wangkaiping.oss.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/file/updateLoad")
@CrossOrigin
public class FileController {
    @Autowired
    private FileService fileService;

    @PostMapping("/updateLoadTest")
    public R updateLoadTest(MultipartFile file){
        String url = fileService.updateLoadTest(file);
        return R.ok().data("url",url);
    }
}
