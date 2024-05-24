package com.likelion.yourside.myPage.controller;

import com.likelion.yourside.myPage.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/mypage")
@RequiredArgsConstructor
public class MypageController {
    private final MypageService mypageService;
}
