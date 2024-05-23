package com.likelion.yourside.user.service;

import com.likelion.yourside.user.dto.UserLoginRequestDto;
import com.likelion.yourside.user.dto.UserSignUpRequestDto;
import com.likelion.yourside.util.response.CustomAPIResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<CustomAPIResponse<?>> signUp(UserSignUpRequestDto signUpDto); // 회원가입
    ResponseEntity<CustomAPIResponse<?>> login(UserLoginRequestDto loginDto); // 로그인

}
