package com.nunar.nunar.controller;

import com.nunar.nunar.exception.CustomException;
import com.nunar.nunar.request.LoginRequest;
import com.nunar.nunar.request.RegisterRequest;
import com.nunar.nunar.response.BaseResponse;
import com.nunar.nunar.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public BaseResponse<String> login(@RequestBody LoginRequest loginRequest) throws Exception {
        return new BaseResponse<>(HttpStatus.OK.value(), "로그인 성공", authService.login(loginRequest));
    }

    @PostMapping("/register")
    public BaseResponse<Void> register(@RequestBody RegisterRequest registerRequest) throws CustomException {
        authService.register(registerRequest);
        return new BaseResponse<>(200, "회원가입 성공", null);
    }

}
