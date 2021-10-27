package com.nunar.nunar.service;


import com.nunar.nunar.exception.CustomException;
import com.nunar.nunar.model.User;
import com.nunar.nunar.repository.UserRepository;
import com.nunar.nunar.request.ModifiedUserRequest;
import com.nunar.nunar.response.UserResponse;
import com.nunar.nunar.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    final JwtUtil jwtUtil;
    final UserRepository userRepository;

    public UserService(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    public UserResponse getUser(String token) {
        return userRepository.findById(jwtUtil.extractUsername(token)).toUserResponse();
    }

    public UserResponse getUser(int userIdx) throws CustomException {
        return userRepository.findById(userIdx).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 유저가 존재하지 않습니다")).toUserResponse();
    }

    public void modifyUserInfo(String token, ModifiedUserRequest userRequest) throws CustomException {
        if(userRequest.getName().isBlank() || userRequest.getProfileUrl().isBlank()) throw new CustomException(HttpStatus.BAD_REQUEST, "공백 없이 입력해 주세요");

        User user = userRepository.findById(jwtUtil.extractUsername(token));
        user.setName(userRequest.getName());
        user.setProfileUrl(userRequest.getProfileUrl());

        userRepository.save(user);
    }

}
