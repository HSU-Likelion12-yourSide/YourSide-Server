package com.likelion.yourside.comment.service;
import com.likelion.yourside.comment.dto.CommentCreateDto;
import com.likelion.yourside.comment.repository.CommentRepository;
import com.likelion.yourside.domain.Comment;
import com.likelion.yourside.domain.User;
import com.likelion.yourside.user.repository.UserRepository;
import com.likelion.yourside.util.response.CustomAPIResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Builder
public class CommentServiceImpl implements CommentService{


}
