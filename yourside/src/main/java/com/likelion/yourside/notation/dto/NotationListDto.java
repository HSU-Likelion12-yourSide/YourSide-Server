package com.likelion.yourside.notation.dto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

//게시글 리스트 조회 응답 DTO
public class NotationListDto {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class NotationResponse{
        private Long id;
        private String title;
        private boolean isPinned;
        private LocalDateTime createdAt;
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

