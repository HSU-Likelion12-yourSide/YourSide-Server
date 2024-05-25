package com.likelion.yourside.notation.dto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

//특정 게시글 조회 응답 DTO
public class NotationDto {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class NotationResponse{
        private String title;
        private String content;
        private String createdAt;
        private boolean isPinned;
    }

    //공지사항 조회
    @Getter @Setter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SearchNotations{
        private List<NotationResponse> notations;

        public SearchNotations(List<NotationResponse> notations){
            this.notations = notations;
        }

    }
}

