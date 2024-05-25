package com.likelion.yourside.comment.service;
import com.likelion.yourside.comment.dto.CommentCreateDto;
import com.likelion.yourside.comment.dto.CommentListDto;
import com.likelion.yourside.comment.repository.CommentRepository;
import com.likelion.yourside.domain.Comment;
import com.likelion.yourside.domain.Notation;
import com.likelion.yourside.domain.Posting;
import com.likelion.yourside.domain.User;
import com.likelion.yourside.likes.repository.LikesRepository;
import com.likelion.yourside.notation.dto.NotationDto;
import com.likelion.yourside.posting.repository.PostingRepository;
import com.likelion.yourside.user.repository.UserRepository;
import com.likelion.yourside.util.response.CustomAPIResponse;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Builder
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostingRepository postingRepository;
    private final LikesRepository likesRepository;

    //댓글 작성 -----------------------------------------------------------------------------------------------
    @Override
    public ResponseEntity<CustomAPIResponse<?>> createComment(Long postingId, @Valid CommentCreateDto.Req req) {
        User user = userRepository.findById(req.getUser_id()).orElseThrow();

        Optional<Posting> optionalPosting = postingRepository.findById(postingId);
        //해당 게시글이 없는 경우 : 404
        if (optionalPosting.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(CustomAPIResponse.createFailWithoutData(HttpStatus.NOT_FOUND.value(), "게시글이 삭제되었거나 존재하지 않는 게시글입니다."));
        }
        Posting posting = optionalPosting.get();

        //Comment 엔티티 객체 생성 후 저장
        Comment comment = req.toEntity(posting,req, user);
        Comment savedComment = commentRepository.save(comment);

        //댓글 작성 성공 : 201
        CustomAPIResponse<?> res = CustomAPIResponse.createSuccess(HttpStatus.CREATED.value(), null, "댓글이 작성되었습니다.");
        return ResponseEntity.ok(res);
    }


    //댓글 전체 조회 -------------------------------------------------------------------------------------------
    @Override
    public ResponseEntity<CustomAPIResponse<?>> getAllComment(Long postingId) {
        List<Comment> comments = commentRepository.findAll();

        //댓글 전체 조회 성공(댓글 존재하지 않음) : 200
        if (comments.isEmpty()) {
            CustomAPIResponse<?> res = CustomAPIResponse.createFailWithoutData(HttpStatus.OK.value(), "작성한 댓글이 없습니다.");
            return ResponseEntity.ok(res);
        }

        //해당 게시글이 없는 경우 : 404
        Optional<Posting> optionalPosting = postingRepository.findById(postingId);
        if (optionalPosting.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(CustomAPIResponse.createFailWithoutData(HttpStatus.NOT_FOUND.value(), "게시글이 존재하지 않습니다."));
        }

        List<CommentListDto.CommentResponse> commentResponses = new ArrayList<>();
        //List<Comment> -> List<CommentListDto.CommentResponse>작업
        for (Comment comment : comments) {
            Long userId = comment.getUser().getId();
            Long commentId = comment.getId();
            boolean isLiked = likesRepository.existsByUserIdAndCommentId(userId, commentId);

            commentResponses.add(CommentListDto.CommentResponse.builder()
                    .nickname(comment.getUser().getNickname())
                    .createdAt(comment.getCreatedAt().toLocalDate())
                    .content(comment.getContent())
                    .isLiked(isLiked)
                    .likes(comment.getLikes())
                    .build());
        }

        //사용자에게 반환하기 위한 최종 데이터
        CommentListDto.SearchCommentRes searchCommentRes = new CommentListDto.SearchCommentRes(commentResponses);
        CustomAPIResponse<CommentListDto.SearchCommentRes> res = CustomAPIResponse.createSuccess(HttpStatus.OK.value(), searchCommentRes, "댓글 조회가 완료되었습니다.");
        //댓글 전체 조회 성공(댓글 존재) : 200
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }
}
