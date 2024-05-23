package com.likelion.yourside.user.controller;

import com.likelion.yourside.user.service.UserService;
import com.likelion.yourside.util.response.CustomAPIResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<CustomAPIResponse<?>> signUp(@RequestBody @Valid UserSignUpDto userSignUpDto) {
        return userService.signUp(userSignUpDto);
    }

}
