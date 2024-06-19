package com.likelion.yourside.comment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


public class CommentListRequestDto {
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Req {
        @JsonProperty("user_id")
        private Long userId;
        @JsonProperty("posting_id")
        private Long postingId;
    }

}

