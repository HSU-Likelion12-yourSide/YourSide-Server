package com.likelion.yourside.comment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

public class CommentListDto {

    @Getter @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class CommentResponse {
        private String nickname;
        @JsonProperty("created_at")
        private LocalDate createdAt;
        private String content;
        @JsonProperty("is_liked")
        private boolean isLiked;
        private Integer likes;
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
