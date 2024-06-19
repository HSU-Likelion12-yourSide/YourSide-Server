package com.likelion.yourside.comment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.likelion.yourside.domain.Comment;
import com.likelion.yourside.domain.Posting;
import com.likelion.yourside.domain.User;
import com.likelion.yourside.user.repository.UserRepository;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

public class CommentCreateDto {
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Req {

        private Long id; //기본키

        private Long user_id;

        private Long posting_id;

        @NotBlank(message = "댓글의 내용을 입력해주세요.")
        private String content;

        @JsonProperty("like_count")
        @Builder.Default //기본값 지정해주기
        private int likeCount = 0; // 좋아요 수

        @JsonProperty("dislike_count")
        @Builder.Default //기본값 지정해주기
        private int dislikeCount = 0; // 싫어요 수

        public Comment toEntity(Posting posting, Req req ,User user){

            return Comment.builder()
                    .user(user)
                    .posting(posting)
                    .content(req.content)
                    //null이 아니어야 하는 likeCount, dislikeCount가 포함되어 있지 않음. 추가해주어야 함
                    // -> 그러나 댓글 생성 시 초기 좋아요/싫어요 개수는 항상 0 이므로 엔티티에서 기본값으로 설정해주기
                    .build();
        }

    }
}
