package com.likelion.yourside.myPage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@Builder
public class MypageGetcommentlistResponseDto {
    @JsonProperty("comment_id")
    private Long commentId;
    @JsonProperty("posting_title")
    private String postingTitle;
    private String content;
    @JsonProperty("created_at")
    private String createdAt;
}
