package com.likelion.yourside.comment.service;

import com.likelion.yourside.comment.dto.CommentCreateDto;
import com.likelion.yourside.comment.dto.CommentDislikeDto;
import com.likelion.yourside.comment.dto.CommentLikeDto;
import com.likelion.yourside.util.response.CustomAPIResponse;
import org.springframework.http.ResponseEntity;

public interface CommentService {
    ResponseEntity<CustomAPIResponse<?>> createComment(CommentCreateDto.Req req);
    ResponseEntity<CustomAPIResponse<?>> getAllComment(Long postingId);
    ResponseEntity<CustomAPIResponse<?>> addLikeToComment(CommentLikeDto.Req req);
    ResponseEntity<CustomAPIResponse<?>> removeLikeFromComment(CommentLikeDto.Req req);
    ResponseEntity<CustomAPIResponse<?>> addDislikeToComment(CommentDislikeDto.Req req);
}
