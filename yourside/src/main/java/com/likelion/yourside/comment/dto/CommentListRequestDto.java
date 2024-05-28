package com.likelion.yourside.comment.dto;

import lombok.*;


public class CommentListRequestDto {
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Req {
        private Long user_id;
        private Long posting_id;
    }

}

