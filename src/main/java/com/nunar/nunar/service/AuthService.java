package com.nunar.nunar.service;

import com.nunar.nunar.exception.CustomException;
import com.nunar.nunar.model.User;
import com.nunar.nunar.repository.UserRepository;
import com.nunar.nunar.request.LoginRequest;
import com.nunar.nunar.request.RegisterRequest;
import com.nunar.nunar.security.JwtUtil;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public String login(LoginRequest loginRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getId(), loginRequest.getPassword())
            );
        } catch (Exception e) {
            throw new CustomException(HttpStatus.NOT_FOUND, "아이디 또는 패스워드를 확인해 주세요");
        }
        return jwtUtil.generateToken(loginRequest.getId());
    }

    public void register(RegisterRequest registerRequest) throws CustomException {
        User user = new User(0, registerRequest.getId(), passwordEncoder.encode(registerRequest.getPassword()), registerRequest.getName(), registerRequest.getProfileUrl());
        try {
            userRepository.save(user);
        } catch (Exception exception) {
            throw new CustomException(HttpStatus.CONFLICT, "아이디가 중복 되었습니다");
        }
    }


}
