package com.likelion.yourside.comment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.likelion.yourside.domain.Comment;
import com.likelion.yourside.domain.Likes;
import com.likelion.yourside.domain.User;
import lombok.*;

public class CommentLikeDto {
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Req {
        @JsonProperty("user_id")
        private Long userId; //사용자 ID
        @JsonProperty("comment_id")
        private Long commentId; //댓글 ID
        @JsonProperty("is_liked")
        private boolean isLiked; //좋아요 여부

        public Likes toEntity(User user, Comment comment){
            return Likes.builder()
                    .user(user)
                    .comment(comment)
                    .build();

        }
    }
}
