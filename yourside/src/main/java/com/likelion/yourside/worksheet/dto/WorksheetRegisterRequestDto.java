package com.likelion.yourside.worksheet.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@Builder
public class WorksheetRegisterRequestDto {
    private String title;
    private String content;
    private int total_pay;
    private boolean extra_pay;    // 가산 수당
    private boolean week_pay;     // 주휴 수당
    private boolean night_pay;    // 야간 수당
    private boolean overtime_pay; // 연장 근로 수당
    private boolean holiday_pay;  // 휴일 근로 수당
    private Long userId;
}
