package com.likelion.yourside.comment.controller;

import com.likelion.yourside.comment.dto.CommentCreateDto;
import com.likelion.yourside.comment.service.CommentService;
import com.likelion.yourside.util.response.CustomAPIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<CustomAPIResponse<?>> createComment(
            @PathVariable("posting_id") Long postingId,
            @RequestBody CommentCreateDto.Req req
    ){
        ResponseEntity<CustomAPIResponse<?>> result = commentService.createComment(postingId, req);
        return result;
    }

    //댓글 전체 조회
    @GetMapping("/{posting_id}/list")
    public ResponseEntity<CustomAPIResponse<?>> getAllComment(
            @PathVariable("posting_id") Long postingId
    ){
        ResponseEntity<CustomAPIResponse<?>> result = commentService.getAllComment(postingId);
        return result;
    }
}
