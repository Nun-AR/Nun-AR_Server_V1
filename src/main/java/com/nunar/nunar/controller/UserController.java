package com.nunar.nunar.controller;

import com.nunar.nunar.exception.CustomException;
import com.nunar.nunar.request.ModifiedUserRequest;
import com.nunar.nunar.response.BaseResponse;
import com.nunar.nunar.response.UserResponse;
import com.nunar.nunar.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/user")
public class UserController {

    final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/my")
    public BaseResponse<UserResponse> getMyInfo(@RequestHeader("Authorization") String token) {
        return new BaseResponse<>(200, "성공적으로 조회하였습니다", userService.getUser(token.substring(7)));
    }

    @GetMapping("/{userIdx}")
    public BaseResponse<UserResponse> getUserByIdx(@PathVariable("userIdx") int userIdx) throws CustomException {
        return new BaseResponse<>(200, "성공적으로 조회하였습니다", userService.getUser(userIdx));
    }

    @PatchMapping("")
    public BaseResponse<Void> modifyMyInfo(@RequestHeader("Authorization") String token, @RequestBody ModifiedUserRequest userRequest) throws CustomException {
        userService.modifyUserInfo(token.substring(7), userRequest);
        return new BaseResponse<>(200, "유저 정보를 변경했습니다", null);
    }

}
