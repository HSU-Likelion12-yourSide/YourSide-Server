package com.likelion.yourside.myPage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@Builder
public class MypageGetuserinfoResponseDto {
    @JsonProperty("posting_count")
    private Long postingCount;
    @JsonProperty("comment_count")
    private Long commentCount;
    @JsonProperty("like_count")
    private Long likeCount;
    private String nickname;
}
