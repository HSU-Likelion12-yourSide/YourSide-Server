package com.likelion.yourside.posting.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostingCreateResponseDto {
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("worksheet_id")
    private Long worksheetId;
    private String title;
    private String content;
    private int type;
}
