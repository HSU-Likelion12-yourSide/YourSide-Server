package com.likelion.yourside.worksheet.dto;

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
    private int totalPay;
    private boolean extraPay;    // 가산 수당
    private boolean weekPay;     // 주휴 수당
    private boolean nightPay;    // 야간 수당
    private boolean overtimePay; // 연장 근로 수당
    private boolean holidayPay;  // 휴일 근로 수당
    private Long userId;
}
