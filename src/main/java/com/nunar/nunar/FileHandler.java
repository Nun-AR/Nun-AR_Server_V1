package com.nunar.nunar;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Component
public class FileHandler {
    public String getImageUrl(MultipartFile image) throws Exception {
        String absolutePath = new File("").getAbsolutePath() + "\\";
        String type = "images/";
        File file = new File(type);
        if (!file.exists()) {
            file.mkdirs();
        }
        if (image.isEmpty()) {
            throw new Exception("파일을 확인해 주세요");
        }
        String contentType = image.getContentType();
        String originalFileExtension;
        if (ObjectUtils.isEmpty(contentType)) {
            throw new Exception("파일을 확인해 주세요");
        }
        if (contentType.contains("image/jpeg")) {
            originalFileExtension = ".jpg";
        } else if (contentType.contains("image/png")) {
            originalFileExtension = ".png";
        } else if (contentType.contains("image/gif")) {
            originalFileExtension = ".gif";
        } else {
            throw new Exception("이미지가 아닙니다");
        }
        String newFile = UUID.randomUUID() + originalFileExtension;
        file = new File(absolutePath + type + "/" + newFile);
        try {
            image.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("파일 변환 불가");
        }
        return type + "/" + newFile;
    }
}
