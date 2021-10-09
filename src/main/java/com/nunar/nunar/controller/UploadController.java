package com.nunar.nunar.controller;

import com.nunar.nunar.exception.CustomException;
import com.nunar.nunar.model.Image;
import com.nunar.nunar.response.BaseResponse;
import com.nunar.nunar.service.UploadService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/upload")
public class UploadController {

    final UploadService uploadService;

    public UploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @PostMapping("/image")
    public BaseResponse<String> uploadImage(@RequestParam MultipartFile file) throws CustomException {
        Image image = uploadService.uploadImage(file);
        return new BaseResponse<>(200, "성공적으로 업로드했습니다", image.getImageUrl());
    }

}
