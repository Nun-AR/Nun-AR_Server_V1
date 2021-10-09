package com.nunar.nunar.service;

import com.nunar.nunar.FileHandler;
import com.nunar.nunar.exception.CustomException;
import com.nunar.nunar.model.Image;
import com.nunar.nunar.model.Model;
import com.nunar.nunar.repository.ImageRepository;
import com.nunar.nunar.repository.ModelRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadService {

    ImageRepository imageRepository;
    ModelRepository modelRepository;
    FileHandler fileHandler;

    public UploadService(ImageRepository imageRepository, ModelRepository modelRepository, FileHandler fileHandler) {
        this.imageRepository = imageRepository;
        this.modelRepository = modelRepository;
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

    public Model uploadModel(MultipartFile model) throws CustomException {
        try {
            String url = fileHandler.getModelUrl(model);
            Model newModel = new Model(url);
            return modelRepository.save(newModel);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
