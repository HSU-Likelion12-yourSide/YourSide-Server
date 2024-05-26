package com.likelion.yourside.posting.dto;

import lombok.*;

public class PostingPopularListDto {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class PostingResponse {
        private String title;
        private String content;
    }
}
