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

        private Long id;//기본키
        private Long user_id;
        private Long posting_id;
        @NotBlank(message = "댓글의 내용을 입력해주세요.")
        private String content;
        @JsonProperty("is_liked")
        private boolean isLiked;

        public Comment toEntity(Posting posting, Req req ,User user){

            return Comment.builder()
                    .user(user)
                    .posting(posting)
                    .content(req.content)
                    .build();
        }

    }
}
