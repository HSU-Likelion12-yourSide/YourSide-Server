package com.likelion.yourside.worksheet.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@Builder
public class WorksheetGetAllListResponseDto {
    @JsonProperty("worksheet_id")
    private Long worksheetId;
    private String title;
    private String content;
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
