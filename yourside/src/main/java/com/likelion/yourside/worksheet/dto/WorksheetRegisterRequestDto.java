package com.likelion.yourside.worksheet.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("total_pay")
    private int totalPay;
    @JsonProperty("extra_pay")
    private boolean extraPay;    // 가산 수당
    @JsonProperty("week_pay")
    private boolean weekPay;     // 주휴 수당
    @JsonProperty("night_pay")
    private boolean nightPay;    // 야간 수당
    @JsonProperty("overtime_pay")
    private boolean overtimePay; // 연장 근로 수당
    @JsonProperty("holiday_pay")
    private boolean holidayPay;  // 휴일 근로 수당
    @JsonProperty("user_id")
    private Long userId;
}
