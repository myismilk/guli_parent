package com.wangkaiping.vdo.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoUploadService {
    String videoUpload(MultipartFile file);

    void deleteVideoMore(List<String> videoIds);
}
