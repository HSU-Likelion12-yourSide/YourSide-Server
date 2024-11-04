package com.likelion.yourside.comment.controller;

import com.likelion.yourside.comment.dto.CommentCreateDto;
import com.likelion.yourside.comment.dto.CommentDislikeDto;
import com.likelion.yourside.comment.dto.CommentLikeDto;
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
    @PostMapping
    public ResponseEntity<CustomAPIResponse<?>> createComment(
            @RequestBody CommentCreateDto.Req req) {
        ResponseEntity<CustomAPIResponse<?>> result = commentService.createComment(req);
        return result;
    }

    //댓글 전체 조회
    @GetMapping("/list")
    public ResponseEntity<CustomAPIResponse<?>> getAllComment(
            @RequestParam("user_id") Long userId,
            @RequestParam("posting_id") Long postingId)  {
        ResponseEntity<CustomAPIResponse<?>> result = commentService.getAllComment(userId, postingId);
        return result;
    }

    //댓글에 좋아요 추가 / 해제
    @PostMapping("/likes")
    public ResponseEntity<CustomAPIResponse<?>> addOrDeleteLikes(
            @RequestBody CommentLikeDto.Req req) {
        ResponseEntity<CustomAPIResponse<?>> result = commentService.addLikeOrDelete(req);
        return result;
    }

    //댓글에 싫어요 추가 / 해제
    @PostMapping("/dislikes")
    public ResponseEntity<CustomAPIResponse<?>> addOrDeleteDislikes(
            @RequestBody CommentDislikeDto.Req req) {
        ResponseEntity<CustomAPIResponse<?>> result = commentService.addDislikeOrDelete(req);
        return result;
    }
}
