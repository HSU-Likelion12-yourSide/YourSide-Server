package com.likelion.yourside.user.service;

import com.likelion.yourside.user.dto.UserLoginRequestDto;
import com.likelion.yourside.user.dto.UserSignUpRequestDto;
import com.likelion.yourside.util.response.CustomAPIResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<CustomAPIResponse<?>> signUp(UserSignUpRequestDto signUpDto); // 회원가입
    ResponseEntity<CustomAPIResponse<?>> login(UserLoginRequestDto loginDto); // 로그인
    ResponseEntity<CustomAPIResponse<?>> checkDuplicationUsername(String username); // username 중복 확인
    ResponseEntity<CustomAPIResponse<?>> checkUsername(String name, String email); // username 찾기
    ResponseEntity<CustomAPIResponse<?>> checkPassword(String name, String email, String username); // password 찾기
}
