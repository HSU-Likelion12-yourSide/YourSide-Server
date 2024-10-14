package com.likelion.yourside.posting.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


public class PostingListDto {
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class PostingResponse {
        private Long id;
        private String title;
        private String content;
        @JsonProperty("created_at")
        private String createdAt;
        @JsonProperty("bookmark_count")
        private int bookmarkCount;
    }
}
