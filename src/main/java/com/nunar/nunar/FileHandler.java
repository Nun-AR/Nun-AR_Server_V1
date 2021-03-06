package com.nunar.nunar;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileHandler {
    private final PathComponent pathComponent;

    public FileHandler(PathComponent pathComponent) {
        this.pathComponent = pathComponent;
    }

    public String getImageUrl(MultipartFile image) throws Exception {
        System.out.println(pathComponent.getPath());
        String absolutePath = new File(pathComponent.getPath()).getAbsolutePath() + "/";
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
        return newFile;
    }

    public String getModelUrl(MultipartFile model) throws Exception {
        String absolutePath = new File(pathComponent.getPath()).getAbsolutePath() + "/";
        String type = "models/";
        File file = new File(type);
        if (!file.exists()) {
            file.mkdirs();
        }
        if (model.isEmpty()) {
            throw new Exception("파일을 확인해 주세요");
        }
        String contentType = model.getContentType();
        String originalFileExtension = ".gltf";
        if (ObjectUtils.isEmpty(contentType)) {
            throw new Exception("파일을 확인해 주세요");
        }
        if(!contentType.contains("model/gltf+json")) {
            throw new Exception("glTF 파일을 선택해 주세요 Not " + contentType);
        }
        System.out.println(contentType);
        String newFile = UUID.randomUUID() + originalFileExtension;
        file = new File(absolutePath + type + "/" + newFile);
        model.transferTo(file);
        return newFile;
    }

}
