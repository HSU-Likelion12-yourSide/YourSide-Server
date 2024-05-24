package com.likelion.yourside.comment.controller;

import com.likelion.yourside.comment.service.CommentService;
import com.likelion.yourside.util.response.CustomAPIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    //댓글 작성
    @PostMapping("/{posting_id}")
    public ResponseEntity<CustomAPIResponse<?>> postComment(){

    }

    //댓글 전체 조회
    @GetMapping("/{posting_id}/list")
    public ResponseEntity<CustomAPIResponse<?>> getAllComment(){

    }
}
