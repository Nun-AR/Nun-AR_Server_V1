package com.nunar.nunar.controller;

import com.nunar.nunar.PathComponent;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

@RestController
public class FileController {

    private final PathComponent pathComponent;

    public FileController(PathComponent pathComponent) {
        this.pathComponent = pathComponent;
    }

    @GetMapping(value = "/image/{url}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE})
    public @ResponseBody
    byte[] getImage(@PathVariable String url) throws IOException {
        String absolutePath = new File(pathComponent.getPath()).getAbsolutePath() + "/" + "images//";
        File file = new File(absolutePath + url);
        InputStream in = new FileInputStream(file);
        return IOUtils.toByteArray(in);
    }

    @GetMapping(value = "/model/{url}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody byte[] getModel(@PathVariable String url) throws IOException {
        String absolutePath = new File(pathComponent.getPath()).getAbsolutePath() + "/" + "models//";
        File file = new File(absolutePath + url);
        InputStream in = new FileInputStream(file);
        return IOUtils.toByteArray(in);
    }

}

