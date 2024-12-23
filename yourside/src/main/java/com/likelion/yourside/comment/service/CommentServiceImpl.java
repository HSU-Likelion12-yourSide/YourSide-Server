package com.likelion.yourside.comment.service;
import com.likelion.yourside.comment.dto.*;
import com.likelion.yourside.comment.repository.CommentRepository;
import com.likelion.yourside.dislikes.DislikesRepository;
import com.likelion.yourside.domain.*;
import com.likelion.yourside.likes.repository.LikesRepository;
import com.likelion.yourside.posting.repository.PostingRepository;
import com.likelion.yourside.user.repository.UserRepository;
import com.likelion.yourside.util.response.CustomAPIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostingRepository postingRepository;
    private final LikesRepository likesRepository;
    private final DislikesRepository dislikesRepository;

    //댓글 작성 -------------------------------------------------------------------------------------------------------------------------------------
    @Override
    public ResponseEntity<CustomAPIResponse<?>> createComment(CommentCreateDto.Req req) {

        //해당 회원이 없는 경우 : 404
        Optional<User> optionalUser = userRepository.findById(req.getUserId());
        if (optionalUser.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(CustomAPIResponse.createFailWithoutData(HttpStatus.NOT_FOUND.value(), "해당 회원을 찾을 수 없습니다."));
        }
        User user = optionalUser.get();

        Optional<Posting> optionalPosting = postingRepository.findById(req.getPostingId());
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

        // User 답변 총 개수 증가
        user.increaseTotalComments();
        userRepository.save(user);

        //댓글 작성 성공 : 201
        CustomAPIResponse<?> res = CustomAPIResponse.createSuccessWithoutData(HttpStatus.CREATED.value(),  "댓글이 작성되었습니다.");
        return ResponseEntity.ok(res);
    }

    //댓글 전체 조회 ---------------------------------------------------------------------------------------------------------------------------------
    @Override
    public ResponseEntity<CustomAPIResponse<?>> getAllComment(Long userId, Long postingId) {
        Optional<Posting> optionalPosting = postingRepository.findById(postingId);

        //해당 게시글이 없는 경우 : 404
        if (optionalPosting.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(CustomAPIResponse.createFailWithoutData(HttpStatus.NOT_FOUND.value(), "게시글이 존재하지 않습니다."));
        }

        Posting posting = optionalPosting.get();
        List<Comment> comments = commentRepository.findAllByPosting(posting);

        //댓글이 존재하지 않는 경우 : 200
        if (comments.isEmpty()) {
            CustomAPIResponse<?> res = CustomAPIResponse.createSuccessWithoutData(HttpStatus.OK.value(), "작성한 댓글이 없습니다.");
            return ResponseEntity.ok(res);
        }

        //(댓글을 조회한)해당 회원이 없는 경우 : 404
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(CustomAPIResponse.createFailWithoutData(HttpStatus.NOT_FOUND.value(), "해당 회원을 찾을 수 없습니다."));
        }
        User user = optionalUser.get();

        //List<Comment> -> List<CommentListDto.CommentResponse>작업
        List<CommentListResponseDto.CommentResponse> commentResponses = new ArrayList<>();
        for (Comment comment : comments) {

            Optional<Likes> foundLikes = likesRepository.findByUserAndComment(user, comment);
            Optional<Dislikes> foundDislikes = dislikesRepository.findByUserAndComment(user, comment);

            commentResponses.add(CommentListResponseDto.CommentResponse.builder()
                    .id(comment.getId())
                    .nickname(user.getNickname())
                    .createdAt(comment.localDateTimeToString())
                    .content(comment.getContent())
                    .liked(foundLikes.isPresent())
                    .disliked(foundDislikes.isPresent())
                    .likeCount(comment.getLikeCount())
                    .dislikeCount(comment.getDislikeCount())
                    .build());
        }

        //사용자에게 반환하기 위한 최종 데이터
        CommentListResponseDto.SearchCommentRes searchCommentRes = new CommentListResponseDto.SearchCommentRes(commentResponses);
        CustomAPIResponse<CommentListResponseDto.SearchCommentRes> res = CustomAPIResponse.createSuccess(HttpStatus.OK.value(), searchCommentRes, "댓글 조회가 완료되었습니다.");

        //성공2. 댓글이 존재하는 경우 : 200
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }


    //좋아요 추가 / 해제 ------------------------------------------------------------------------------------------------------------------------------
    @Override
    public ResponseEntity<CustomAPIResponse<?>> addLikeOrDelete(CommentLikeDto.Req req) {
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

        //DB에 해당 게시글이 없는 경우 : 404
        Optional<Posting> optionalPosting = postingRepository.findById(comment.getPosting().getId());
        if (optionalPosting.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(CustomAPIResponse.createFailWithoutData(HttpStatus.NOT_FOUND.value(), "해당하는 게시글이 존재하지 않습니다."));
        }

        //좋아요가 이미 눌려있으면 -> 좋아요 해제
        if (req.isLiked()) {
            //likes 스키마에서 user_id, comment_id를 가지는 레코드 삭제
            Optional<Likes> optionalLikes = likesRepository.findByUserAndComment(user, comment);//user, comment 필드를 가지는 likes 찾기

            //400
            if (optionalLikes.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(CustomAPIResponse.createFailWithoutData(HttpStatus.BAD_REQUEST.value(), "좋아요 하신 적이 없습니다."));
            }

            Likes likes = optionalLikes.get();
            likesRepository.delete(likes);

            //Comment 스키마에 likes_count -1
            comment.subtractLikeCount();
            commentRepository.save(comment); // 변경 사항 저장

            // User 좋아요 개수 감소
            user.decreaseTotalLikes();
            userRepository.save(user);

            //좋아요 삭제 성공 : 200
            CustomAPIResponse<?> res = CustomAPIResponse.createSuccessWithoutData(HttpStatus.OK.value(), "해당 댓글에 좋아요를 취소하셨습니다.");
            return ResponseEntity.ok(res);

        }else{ //좋아요가 눌려있지 않으면 -> 좋아요 추가
            Optional<Likes> optionalLikes = likesRepository.findByUserAndComment(user, comment);//user, comment 필드를 가지는 likes 찾기

            //이미 좋아요를 누른 경우
            if(optionalLikes.isPresent()) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(CustomAPIResponse.createFailWithoutData(HttpStatus.BAD_REQUEST.value(), "이미 좋아요 하셨습니다."));
            }

            //likes 스키마에 user_id, comment_id를 가지는 레코드 추가
            Likes likes = req.toEntity(user, comment);
            likesRepository.save(likes);

            //Comment 스키마에 likes_count +1
            comment.addLikeCount();
            commentRepository.save(comment); // 변경 사항 저장

            // User 좋아요 개수 증가
            user.increaseTotalLikes();
            userRepository.save(user);

            //좋아요 추가 성공 : 200
            CustomAPIResponse<?> res = CustomAPIResponse.createSuccessWithoutData(HttpStatus.OK.value(), "해당 댓글을 좋아요 하셨습니다.");
            return ResponseEntity.ok(res);
        }
    }

    //싫어요 추가 / 해제 ------------------------------------------------------------------------------------------------------------------------------
    @Override
    public ResponseEntity<CustomAPIResponse<?>> addDislikeOrDelete(CommentDislikeDto.Req req) {
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

        //DB에 해당 게시글이 없는 경우 : 404
        Optional<Posting> optionalPosting = postingRepository.findById(comment.getPosting().getId());
        if (optionalPosting.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(CustomAPIResponse.createFailWithoutData(HttpStatus.NOT_FOUND.value(), "해당하는 게시글이 존재하지 않습니다."));
        }

        //싫어요가 이미 눌려있으면 -> 싫어요 해제
        if (req.isDisliked()) {
            //dislikes 스키마에서 user_id, comment_id를 가지는 레코드 삭제
            Optional<Dislikes> optionalDislikes = dislikesRepository.findByUserAndComment(user, comment);//user, comment 필드를 가지는 dislikes 찾기

            //400
            if (optionalDislikes.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(CustomAPIResponse.createFailWithoutData(HttpStatus.BAD_REQUEST.value(), "싫어요 하신 적이 없습니다."));
            }

            Dislikes dislikes = optionalDislikes.get();
            dislikesRepository.delete(dislikes);

            //Comment 스키마에 dislikes_count -1
            comment.subtractDisLikeCount();
            commentRepository.save(comment); // 변경 사항 저장

            //싫어요 삭제 성공 : 200
            CustomAPIResponse<?> res = CustomAPIResponse.createSuccessWithoutData(HttpStatus.OK.value(), "해당 댓글에 싫어요를 취소하셨습니다.");
            return ResponseEntity.ok(res);

        }else{ //싫어요가 눌려있지 않으면 -> 싫어요 추가
            Optional<Dislikes> optionalDislikes = dislikesRepository.findByUserAndComment(user, comment);//user, comment 필드를 가지는 dislikes 찾기

            //400 : 이미 싫어요를 누른 경우
            if(optionalDislikes.isPresent()) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(CustomAPIResponse.createFailWithoutData(HttpStatus.BAD_REQUEST.value(), "이미 싫어요 하셨습니다."));
            }

            //Comment 스키마에 dislikes_count + 1
            comment.addDislikeCount();

            //dislikes_count가 50 이상일 시 해당 댓글 삭제
            if ( comment.getDislikeCount() >= 50) {
                User commentWriter = comment.getUser();
                commentWriter.addDeleteComments();
                commentRepository.delete(comment);

                //delete_comments가 10 이상일 시 User의 Tier을 일반인으로 변경
                if ( commentWriter.getDeleteComments()>= 10) {
                    commentWriter.demoteToOrdinaryPerson(); //일반인으로 강등
                    commentWriter.resetDeleteComments(); //삭제된 댓글 개수 초기화
                    userRepository.save(commentWriter); // User 변경 사항 저장

                    //200 : 일반인으로 강등
                    CustomAPIResponse<?> res = CustomAPIResponse.createSuccessWithoutData(HttpStatus.OK.value(), "싫어요가 일정 수준을 넘어, 댓글이 삭제되었습니다. (댓글 작성자의) 삭제된 댓글이 10개가 되어 일반인으로 강등되었습니다.");
                    return ResponseEntity.ok(res);
                }

                userRepository.save(commentWriter); // User 변경 사항 저장
                CustomAPIResponse<?> res = CustomAPIResponse.createSuccessWithoutData(HttpStatus.OK.value(), "싫어요가 일정 수준을 넘어, 댓글이 삭제되었습니다.");
                return ResponseEntity.ok(res);
            }

            //Dislikes 스키마에 user_id, comment_id를 가지는 레코드 추가
            Dislikes dislikes = req.toEntity(user, comment);
            dislikesRepository.save(dislikes);

            commentRepository.save(comment); // 변경 사항 저장

            //싫어요 추가 성공 : 200
            CustomAPIResponse<?> res = CustomAPIResponse.createSuccessWithoutData(HttpStatus.OK.value(), "해당 댓글을 싫어요 하셨습니다.");
            return ResponseEntity.ok(res);
        }
    }
}
