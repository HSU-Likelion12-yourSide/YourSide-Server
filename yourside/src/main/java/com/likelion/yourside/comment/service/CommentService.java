package com.likelion.yourside.comment.service;

import com.likelion.yourside.comment.dto.CommentCreateDto;
import com.likelion.yourside.comment.dto.CommentLikeDto;
import com.likelion.yourside.util.response.CustomAPIResponse;
import org.springframework.http.ResponseEntity;

public interface CommentService {
    //댓글 데이터베이스에 추가하기
    ResponseEntity<CustomAPIResponse<?>> createComment(Long postingId, CommentCreateDto.Req req);

    //데이터베이스에 저장되어 있는 해당 게시글의 댓글 정보 얻어오기
    ResponseEntity<CustomAPIResponse<?>> getAllComment(Long postingId);

    //1. likes 스키마에 user_id, comment_id를 가지는 레코드 추가
    //2. Comment 스키마에 likes_count +1
    ResponseEntity<CustomAPIResponse<?>> addLikeToComment(CommentLikeDto.Req req);

    //1. likes 스키마에 user_id, comment_id를 가지는 레코드 삭제
    //2. Comment 스키마에 likes_count -1
    ResponseEntity<CustomAPIResponse<?>> removeLikeFromComment(CommentLikeDto.Req req);
}
