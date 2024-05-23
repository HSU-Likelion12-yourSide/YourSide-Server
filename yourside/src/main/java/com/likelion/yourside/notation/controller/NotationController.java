package com.likelion.yourside.notation.controller;

import com.likelion.yourside.notation.service.NotationService;
import com.likelion.yourside.util.response.CustomAPIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/notation")
@RequiredArgsConstructor
public class NotationController {

    private final NotationService notationService;

    //공지사항 전체 조회
    @GetMapping("/list")
    public ResponseEntity<CustomAPIResponse<?>> getAllNotation(){
            ResponseEntity<CustomAPIResponse<?>> result = notationService.getAllNotation();
        return result;
    }

    //공지사항 한 개 조회
    @GetMapping("/{notation_id}")
    public ResponseEntity<CustomAPIResponse<?>> getOneNotation(
            @PathVariable("notation_id") Long notationId){
        ResponseEntity<CustomAPIResponse<?>> result = notationService.getOneNotation(notationId);
        return result;
    }

}
