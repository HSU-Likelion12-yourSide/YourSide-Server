package com.likelion.yourside.worksheet.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@Builder
public class WorksheetRegisterResponseDto {
    @JsonProperty("worksheet_id")
    private Long worksheetId;
}
