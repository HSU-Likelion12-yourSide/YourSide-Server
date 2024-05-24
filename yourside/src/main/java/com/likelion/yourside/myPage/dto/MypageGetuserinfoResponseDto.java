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
    @JsonProperty("worksheet_count")
    private int worksheetCount;
    @JsonProperty("posting_count")
    private int postingCount;
    @JsonProperty("comment_count")
    private int commentCount;
    private String nickname;
}
