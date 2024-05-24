package com.likelion.yourside.posting.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@Builder
public class PostingCreateRequestDto {
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("worksheet_id")
    private Long worksheetId;
    private String title;
    private String content;
}
