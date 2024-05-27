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
    private boolean overFive;
    @JsonProperty("week_work")
    private int weekWork;
    @JsonProperty("night_work")
    private int nightWork;
    @JsonProperty("night_money")
    private int nightMoney;
    @JsonProperty("overtime_work")
    private int overtimeWork;
    @JsonProperty("overtime_money")
    private int overtimeMoney;
    @JsonProperty("holiday_work")
    private int holidayWork;
    @JsonProperty("holiday_money")
    private int holidayMoney;
    @JsonProperty("major_insurance")
    private boolean majorInsurance;
    @JsonProperty("income_tax")
    private boolean incomeTax;
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
}
