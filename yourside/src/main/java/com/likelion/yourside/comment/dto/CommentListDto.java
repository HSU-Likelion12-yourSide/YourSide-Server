package com.likelion.yourside.comment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class CommentListDto {

    @Getter @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class CommentResponse {
        private String nickname;
        @JsonProperty("created_at")
        private String createdAt;
        private String content;
        @JsonProperty("is_liked")
        private boolean isLiked;
        @JsonProperty("like_count")
        private Integer likeCount;
    }

    @Getter @Setter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SearchCommentRes{
        private List<CommentResponse> comments;

        public SearchCommentRes(List<CommentResponse> comments){
            this.comments = comments;}
    }
}
