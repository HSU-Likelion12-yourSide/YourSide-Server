package com.likelion.yourside.myPage.controller;

import com.likelion.yourside.myPage.service.MypageService;
import com.likelion.yourside.util.response.CustomAPIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/mypage")
@RequiredArgsConstructor
public class MypageController {
    private final MypageService mypageService;

    @GetMapping("/{user_id}")
    public ResponseEntity<CustomAPIResponse<?>> getUserInfo(@PathVariable("user_id") Long userId) {
        return mypageService.getUserInfo(userId);
    }
}
