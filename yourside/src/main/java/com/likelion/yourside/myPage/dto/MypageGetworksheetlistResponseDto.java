package com.likelion.yourside.myPage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@Builder
public class MypageGetworksheetlistResponseDto {
    @JsonProperty("worksheet_id")
    private Long worksheetId;
    @JsonProperty("total_pay")
    private int totalPay;
    @JsonProperty("extra_pay")
    private boolean extraPay;
    @JsonProperty("week_pay")
    private boolean weekPay;
    @JsonProperty("night_pay")
    private boolean nightPay;
    @JsonProperty("overtime_pay")
    private boolean overtimePay;
    @JsonProperty("holiday_pay")
    private boolean holidayPay;
    @JsonProperty("created_at")
    private String createdAt;
}
