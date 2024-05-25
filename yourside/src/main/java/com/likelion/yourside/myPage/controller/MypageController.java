package com.likelion.yourside.myPage.controller;

import com.likelion.yourside.myPage.service.MypageService;
import com.likelion.yourside.util.response.CustomAPIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/mypage")
@RequiredArgsConstructor
public class MypageController {
    private final MypageService mypageService;

    @GetMapping("/{user_id}")
    public ResponseEntity<CustomAPIResponse<?>> getUserInfo(@PathVariable("user_id") Long userId) {
        return mypageService.getUserInfo(userId);
    }
    @PutMapping("/{user_id}")
    public ResponseEntity<CustomAPIResponse<?>> updateUserIsExpert(@PathVariable("user_id") Long userId) {
        return mypageService.updateUserIsExpert(userId);
    }
    @GetMapping("/list/worksheet/{user_id}")
    public ResponseEntity<CustomAPIResponse<?>> getWorksheetList(@PathVariable("user_id") Long userId) {
        return mypageService.getWorksheetList(userId);
    }
    @GetMapping("/list/posting/{user_id}")
    public ResponseEntity<CustomAPIResponse<?>> getPostingList(@PathVariable("user_id") Long userId) {
        return mypageService.getPostingList(userId);
    }
    @GetMapping("/list/bookmarks/{user_id")
    public ResponseEntity<CustomAPIResponse<?>> getBookmarkList(@PathVariable("user_id") Long userId) {
        return mypageService.getBookmarkList(userId);
    }

}
