package com.nunar.nunar.controller;

import com.nunar.nunar.response.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {


    @GetMapping("/")
    public BaseResponse<String> getTest() {
        return new BaseResponse<>(200, "Successful Test", "Test");
    }

}
