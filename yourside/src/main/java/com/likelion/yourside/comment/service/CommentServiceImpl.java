package com.likelion.yourside.comment.service;
import com.likelion.yourside.comment.repository.CommentRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Builder
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;

    //댓글 작성


    //댓글 전체 조회
}
