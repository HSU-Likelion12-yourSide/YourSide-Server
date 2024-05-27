package com.likelion.yourside.worksheet.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@Builder
public class WorksheetCalculateResponseDto {
    @JsonProperty("over_five")
    private boolean overFive;       // 5인 이상 사업장 여부
    @JsonProperty("week_work")
    private int weekWork;           // 주 근로 시간
    @JsonProperty("week_money")
    private int weekMoney;          // 주휴 수당
    @JsonProperty("night_work")
    private int nightWork;          // 야간 근로 시간
    @JsonProperty("night_money")
    private int nightMoney;         // 야간 근로 수당
    @JsonProperty("overtime_work")
    private int overtimeWork;       // 연장 근로 시간
    @JsonProperty("overtime_money")
    private int overtimeMoney;      // 연장 근로 수당
    @JsonProperty("holiday_work")
    private int holidayWork;        // 휴일 근로 시간
    @JsonProperty("holiday_money")
    private int holidayMoney;       // 휴일 근로 수당
    @JsonProperty("major_insurance")
    private boolean majorInsurance; // 4대 보험 발생 여부
    @JsonProperty("income_tax")
    private boolean incomeTax;      // 소득세 발생 여부
    @JsonProperty("total_pay")
    private int totalPay;           // 월급
    @JsonProperty("extra_pay")
    private boolean extraPay;       // 가산 수당 발생 여부 == 5인 이상 사업장 여부
    @JsonProperty("week_pay")
    private boolean weekPay;        // 주휴 수당 발생 여부
    @JsonProperty("night_pay")
    private boolean nightPay;       // 야간 수당 발생 여부
    @JsonProperty("overtime_pay")
    private boolean overtimePay;    // 연장 근로 수당 발생 여부
    @JsonProperty("holiday_pay")
    private boolean holidayPay;     // 휴일 근로 수당 발생 여부
}
