package com.nunar.nunar.service;

import com.nunar.nunar.FileHandler;
import com.nunar.nunar.exception.CustomException;
import com.nunar.nunar.model.Image;
import com.nunar.nunar.repository.ImageRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadService {

    ImageRepository imageRepository;
    FileHandler fileHandler;

    public UploadService(ImageRepository imageRepository, FileHandler fileHandler) {
        this.imageRepository = imageRepository;
        this.fileHandler = fileHandler;
    }

    public Image uploadImage(MultipartFile image) throws CustomException {
        try {
            String url = fileHandler.getImageUrl(image);
            Image newImage = new Image(url);
            return imageRepository.save(newImage);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
