package com.likelion.yourside.worksheet.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@Builder
public class WorksheetCalculateRequestDto {
    @JsonProperty("hour_pay")
    private int hourPay;                // 시급
    @JsonProperty("week_work")
    private int weekWork;               // 주 근로 시간
    @JsonProperty("over_five")
    private boolean overFive;           // 5인 이상의 사업장인지 여부
    @JsonProperty("overtime_work")
    private int overtimeWork;           // 연장 근로 시간
    @JsonProperty("night_work")
    private int nightWork;              // 야간 근로 시간
    @JsonProperty("holiday_work")
    private int holidayWork;            // 약정 휴일 근로 시간
    @JsonProperty("major_insurance")
    private boolean majorInsurance;
    @JsonProperty("income_tax")
    private boolean incomeTax;
}
