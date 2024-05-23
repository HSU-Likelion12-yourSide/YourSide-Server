package com.likelion.yourside.user.service;

import com.likelion.yourside.user.dto.UserSignUpDto;
import com.likelion.yourside.util.response.CustomAPIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

public interface UserService {
    ResponseEntity<CustomAPIResponse<?>> signUp(UserSignUpDto signUpDto);
}
