package com.likelion.yourside.myPage.service;

import com.likelion.yourside.util.response.CustomAPIResponse;
import org.springframework.http.ResponseEntity;

public interface MypageService {
    ResponseEntity<CustomAPIResponse<?>> getUserInfo(Long userId);
    ResponseEntity<CustomAPIResponse<?>> updateUserIsExpert(Long userId);
    ResponseEntity<CustomAPIResponse<?>> getWorksheetList(Long userId);
}
