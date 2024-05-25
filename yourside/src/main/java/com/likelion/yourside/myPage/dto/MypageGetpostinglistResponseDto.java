package com.likelion.yourside.myPage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@Builder
public class MypageGetpostinglistResponseDto {
    @JsonProperty("posting_id")
    private Long postingId;
    private String title;
    private String content;
    @JsonProperty("created_at")
    private String createdAt;
}
