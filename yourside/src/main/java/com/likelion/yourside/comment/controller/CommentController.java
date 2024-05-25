package com.likelion.yourside.comment.controller;

import com.likelion.yourside.comment.dto.CommentCreateDto;
import com.likelion.yourside.comment.service.CommentService;
import com.likelion.yourside.util.response.CustomAPIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;


}
