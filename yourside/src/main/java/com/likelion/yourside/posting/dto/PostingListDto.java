package com.likelion.yourside.posting.dto;

import lombok.*;

import java.util.List;


public class PostingListDto {
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class PostingResponse {
        private String title;
        private String content;
        private String created_at;
        private int bookmark_count;
    }

/*    //게시글 조회
    @Getter @Setter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SearchPostings{
        private List<PostingResponse> postings;
        public SearchPostings(List<PostingResponse> postings){
            this.postings = postings;
        }
    }*/
}
