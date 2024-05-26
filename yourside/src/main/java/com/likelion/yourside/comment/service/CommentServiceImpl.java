package com.likelion.yourside.comment.service;
import com.likelion.yourside.comment.dto.CommentCreateDto;
import com.likelion.yourside.comment.dto.CommentDislikeDto;
import com.likelion.yourside.comment.dto.CommentLikeDto;
import com.likelion.yourside.comment.dto.CommentListDto;
import com.likelion.yourside.comment.repository.CommentRepository;
import com.likelion.yourside.dislikes.DislikesRepository;
import com.likelion.yourside.domain.*;
import com.likelion.yourside.likes.repository.LikesRepository;
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
    private final DislikesRepository dislikesRepository;

    //댓글 작성 -------------------------------------------------------------------------------------------------------------------------------------
    @Override
    public ResponseEntity<CustomAPIResponse<?>> createComment(CommentCreateDto.Req req) {
        User user = userRepository.findById(req.getUser_id()).orElseThrow();

        Optional<Posting> optionalPosting = postingRepository.findById(req.getPosting_id());
        //해당 게시글이 없는 경우 : 404
        if (optionalPosting.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(CustomAPIResponse.createFailWithoutData(HttpStatus.NOT_FOUND.value(), "게시글이 삭제되었거나 존재하지 않는 게시글입니다."));
        }
        Posting posting = optionalPosting.get();

        //Comment 엔티티 객체 생성 후 저장
        Comment comment = req.toEntity(posting,req, user);
        commentRepository.save(comment);

        //댓글 작성 성공 : 201
        CustomAPIResponse<?> res = CustomAPIResponse.createSuccessWithoutData(HttpStatus.CREATED.value(),  "댓글이 작성되었습니다.");
        return ResponseEntity.ok(res);
    }


    //댓글 전체 조회 ---------------------------------------------------------------------------------------------------------------------------------
    @Override
    public ResponseEntity<CustomAPIResponse<?>> getAllComment(Long postingId) {
        Optional<Posting> optionalPosting = postingRepository.findById(postingId);
        Posting posting = optionalPosting.get();
        List<Comment> comments = commentRepository.findAllbyPosting(posting);

        //성공1. 댓글 존재하지 않는 경우 : 200
        if (comments.isEmpty()) {
            CustomAPIResponse<?> res = CustomAPIResponse.createFailWithoutData(HttpStatus.OK.value(), "작성한 댓글이 없습니다.");
            return ResponseEntity.ok(res);
        }

        //해당 게시글이 없는 경우 : 404
        if (optionalPosting.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(CustomAPIResponse.createFailWithoutData(HttpStatus.NOT_FOUND.value(), "게시글이 존재하지 않습니다."));
        }

        //List<Comment> -> List<CommentListDto.CommentResponse>작업
        List<CommentListDto.CommentResponse> commentResponses = new ArrayList<>();
        for (Comment comment : comments) {
            User user = comment.getUser();
            Optional<Likes> foundLikes = likesRepository.findByUserAndComment(user, comment);

            commentResponses.add(CommentListDto.CommentResponse.builder()
                    .nickname(user.getNickname())
                    .createdAt(comment.localDateTimeToString())
                    .content(comment.getContent())
                    .liked(foundLikes.isEmpty()? true : false)
                    .likeCount(comment.getLikeCount())
                    .build());
        }

        //사용자에게 반환하기 위한 최종 데이터
        CommentListDto.SearchCommentRes searchCommentRes = new CommentListDto.SearchCommentRes(commentResponses);
        CustomAPIResponse<CommentListDto.SearchCommentRes> res = CustomAPIResponse.createSuccess(HttpStatus.OK.value(), searchCommentRes, "댓글 조회가 완료되었습니다.");

        //성공2. 댓글이 존재하는 경우 : 200
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }


    //좋아요 추가------------------------------------------------------------------------------------------------------------------------------------
    @Override
    public ResponseEntity<CustomAPIResponse<?>> addLikeToComment(CommentLikeDto.Req req) {
        // DTO 검증 : API에는 없음(프론트 측에서 해서 줄 것. 그러나 이미 만들었으니 통신할 때 편하라고 남겨둠)
        if (req.getUserId() == null || req.getCommentId() == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(CustomAPIResponse.createFailWithoutData(HttpStatus.BAD_REQUEST.value(), "user_id와 comment_id는 필수 값입니다."));
        }

        //DB에 해당 댓글이 없는 경우 : 404
        Optional<Comment> optionalComment = commentRepository.findById(req.getCommentId());
        if (optionalComment.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(CustomAPIResponse.createFailWithoutData(HttpStatus.NOT_FOUND.value(), "해당하는 댓글이 존재하지 않습니다."));
        }

        User user = userRepository.findById(req.getUserId()).orElseThrow();
        Comment comment = optionalComment.get();

        //likes 스키마에 user_id, comment_id를 가지는 레코드 추가
        Likes likes = req.toEntity(user, comment);
        likesRepository.save(likes);

        //Comment 스키마에 likes_count +1
        int likesCount = comment.getLikeCount() + 1;
        comment.changeLikeCount(likesCount);
        commentRepository.save(comment); // 변경 사항 저장

        //좋아요 추가 성공 : 200
        CustomAPIResponse<?> res = CustomAPIResponse.createSuccessWithoutData(HttpStatus.OK.value(), "해당 댓글을 좋아요 하셨습니다.");
        return ResponseEntity.ok(res);
    }


    //좋아요 취소--------------------------------------------------------------------------------------------------------------------------------------
    @Override
    public ResponseEntity<CustomAPIResponse<?>> removeLikeFromComment(CommentLikeDto.Req req) {

        // DTO 검증 : API에는 없음(프론트 측에서 해서 줄 것. 그러나 이미 만들었으니 통신할 때 편하라고 남겨둠)
        if (req.getUserId() == null || req.getCommentId() == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(CustomAPIResponse.createFailWithoutData(HttpStatus.BAD_REQUEST.value(), "user_id와 comment_id는 필수 값입니다."));
        }

        //DB에 해당 댓글이 없는 경우 : 404
        Optional<Comment> optionalComment = commentRepository.findById(req.getCommentId());
        if (optionalComment.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(CustomAPIResponse.createFailWithoutData(HttpStatus.NOT_FOUND.value(), "해당하는 댓글이 존재하지 않습니다."));
        }

        User user = userRepository.findById(req.getUserId()).orElseThrow();
        Comment comment = optionalComment.get();

        //likes 스키마에서 user_id, comment_id를 가지는 레코드 삭제
        Optional<Likes> optionalLikes = likesRepository.findByUserAndComment(user, comment);//user, comment 필드를 가지는 likes 찾기

        if (optionalLikes.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(CustomAPIResponse.createFailWithoutData(HttpStatus.NOT_FOUND.value(), "좋아요를 하신 적이 없습니다."));
        }

        Likes likes = optionalLikes.get();
        likesRepository.delete(likes);

        //Comment 스키마에 likes_count +1
        int likesCount = comment.getLikeCount() - 1;
        comment.changeLikeCount(likesCount);
        commentRepository.save(comment); // 변경 사항 저장

        //좋아요 삭제 성공 : 200
        CustomAPIResponse<?> res = CustomAPIResponse.createSuccessWithoutData(HttpStatus.OK.value(), "해당 댓글에 좋아요를 취소하셨습니다.");
        return ResponseEntity.ok(res);
    }


    //싫어요 추가 ------------------------------------------------------------------------------------------------------------------------------------
    @Override
    public ResponseEntity<CustomAPIResponse<?>> addDislikeToComment(CommentDislikeDto.Req req) {
        // DTO 검증 : API에는 없음(프론트 측에서 해서 줄 것. 그러나 이미 만들었으니 통신할 때 편하라고 남겨둠)
        if (req.getUserId() == null || req.getCommentId() == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(CustomAPIResponse.createFailWithoutData(HttpStatus.BAD_REQUEST.value(), "user_id와 comment_id는 필수 값입니다."));
        }

        //DB에 해당 댓글이 없는 경우 : 404
        Optional<Comment> optionalComment = commentRepository.findById(req.getCommentId());
        if (optionalComment.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(CustomAPIResponse.createFailWithoutData(HttpStatus.NOT_FOUND.value(), "해당하는 댓글이 존재하지 않습니다."));
        }

        User user = userRepository.findById(req.getUserId()).orElseThrow();
        Comment comment = optionalComment.get();

        //dislikes 스키마에 user_id, comment_id를 가지는 레코드 추가
        Dislikes dislikes = req.toEntity(user, comment);
        dislikesRepository.save(dislikes);

        //Comment 스키마에 dislikes_count + 1
        int dislikesCount = comment.getDislikeCount() + 1;
        comment.changeDislikeCount(dislikesCount);
        commentRepository.save(comment); // 변경 사항 저장

        //싫어요 추가 성공 : 200
        CustomAPIResponse<?> res = CustomAPIResponse.createSuccessWithoutData(HttpStatus.OK.value(), "해당 댓글을 싫어요 하셨습니다.");
        return ResponseEntity.ok(res);
    }
}
