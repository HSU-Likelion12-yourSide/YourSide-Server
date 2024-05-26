package com.likelion.yourside.posting.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostingSearchResponseDto {
    private String title;
    private String nickname;
    @JsonProperty("created_at")
    private String createdAt;
    private String content;
    @JsonProperty("is_bookmarked")
    private boolean bookmarked; //is 사용 x
    @JsonProperty("worksheet_id")
    private Long worksheetId;
}
