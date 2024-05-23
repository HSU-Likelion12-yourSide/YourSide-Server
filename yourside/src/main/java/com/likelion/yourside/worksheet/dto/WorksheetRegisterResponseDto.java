package com.likelion.yourside.worksheet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@Builder
public class WorksheetRegisterResponseDto {
    private Long worksheet_id;
}
