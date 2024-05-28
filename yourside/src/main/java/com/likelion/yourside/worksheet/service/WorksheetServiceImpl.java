package com.likelion.yourside.worksheet.service;

import com.likelion.yourside.domain.User;
import com.likelion.yourside.domain.Worksheet;
import com.likelion.yourside.user.repository.UserRepository;
import com.likelion.yourside.util.response.CustomAPIResponse;
import com.likelion.yourside.worksheet.dto.*;
import com.likelion.yourside.worksheet.repository.WorksheetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class WorksheetServiceImpl implements WorksheetService {
    private final WorksheetRepository worksheetRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<CustomAPIResponse<?>> register(WorksheetRegisterRequestDto worksheetRegisterRequestDto) {
        // 1. userId에 해당하는 유저 있는지 확인
        Optional<User> foundUser = userRepository.findById(worksheetRegisterRequestDto.getUserId());
        if (foundUser.isEmpty()) {
            // 1-1. data
            // 1-2. responseBody
            CustomAPIResponse<Object> responseBody = CustomAPIResponse.createFailWithoutData(HttpStatus.NOT_FOUND.value(), "존재하지 않는 사용자입니다.");
            // 1-3. ResponseEntity
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(responseBody);
        }
        // 2. 내 근로 결과지 저장
        User user = foundUser.get();
        Worksheet worksheet = Worksheet.builder()
                .title(worksheetRegisterRequestDto.getTitle())
                .content(worksheetRegisterRequestDto.getContent())
                .totalPay(worksheetRegisterRequestDto.getTotalPay())
                .extraPay(worksheetRegisterRequestDto.isExtraPay())
                .weekPay(worksheetRegisterRequestDto.isWeekPay())
                .nightPay(worksheetRegisterRequestDto.isNightPay())
                .overtimePay(worksheetRegisterRequestDto.isOvertimePay())
                .holidayPay(worksheetRegisterRequestDto.isHolidayPay())
                .isOpen(false)
                .user(user)
                .build();
        worksheetRepository.save(worksheet);
        // 2-1. data
        WorksheetRegisterResponseDto data = WorksheetRegisterResponseDto.builder()
                .worksheetId(worksheet.getId())
                .build();
        // 2-2. responseBody
        CustomAPIResponse<WorksheetRegisterResponseDto> responseBody = CustomAPIResponse.createSuccess(HttpStatus.CREATED.value(), data, "근로지 생성 완료되었습니다.");
        // 2-3. ResponseEntity
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseBody);
    }

    @Override
    public ResponseEntity<CustomAPIResponse<?>> share(Long worksheetId) {
        // 1. worksheet 있는지 조회
        Optional<Worksheet> foundWorksheet = worksheetRepository.findById(worksheetId);
        if (foundWorksheet.isEmpty()) {
            // 1-1. data
            // 1-2. response body
            CustomAPIResponse<Object> responseBody = CustomAPIResponse.createFailWithoutData(HttpStatus.NOT_FOUND.value(), "일치하는 결과 근로지가 없습니다.");
            // 1-3. Response Entity
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(responseBody);
        }
        // 2. isOpen 변경
        Worksheet worksheet = foundWorksheet.get();
        worksheet.changeIsOpen();
        worksheetRepository.save(worksheet);
        // 2-1. data
        // 2-2. response Body
        CustomAPIResponse<Object> responseBody = CustomAPIResponse.createSuccessWithoutData(HttpStatus.OK.value(), "공유되었습니다.");
        // 2-3. ResponseEntity
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBody);
    }

    @Override
    public ResponseEntity<CustomAPIResponse<?>> getAllList() {
        // 1. null 일 경우 처리
        List<Worksheet> worksheetALlList = worksheetRepository.findAllbyIsOpen();
        if (worksheetALlList.isEmpty()) {
            // 1-1. data
            // 1-2. responseBody
            CustomAPIResponse<?> responseBody = CustomAPIResponse.createSuccessWithoutData(HttpStatus.OK.value(), "조회된 근로 결과지가 없습니다.");
            // 1-3. ResponseEntity
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(responseBody);
        }
        // 2. 성공
        // 2-1. Data
        List<WorksheetGetAllListResponseDto> worksheets = new ArrayList<>();
        for (Worksheet worksheet : worksheetALlList) {
            WorksheetGetAllListResponseDto customWorksheet = WorksheetGetAllListResponseDto.builder()
                    .worksheetId(worksheet.getId())
                    .title(worksheet.getTitle())
                    .content(worksheet.getContent())
                    .extraPay(worksheet.isExtraPay())
                    .weekPay(worksheet.isWeekPay())
                    .nightPay(worksheet.isNightPay())
                    .overtimePay(worksheet.isOvertimePay())
                    .holidayPay(worksheet.isHolidayPay())
                    .build();
            worksheets.add(customWorksheet);
        }
        // 2-2. responseBody
        CustomAPIResponse<List<WorksheetGetAllListResponseDto>> responseBody = CustomAPIResponse.createSuccess(HttpStatus.OK.value(), worksheets, "조회되었습니다.");
        // 2-3. ResponseEntity
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBody);
    }

    @Override
    public ResponseEntity<CustomAPIResponse<?>> getOne(Long worksheetId) {
        // 1. worsheetId로 조회 실패
        Optional<Worksheet> foundWorksheet = worksheetRepository.findById(worksheetId);
        if (foundWorksheet.isEmpty()) {
            // 1-1. data
            // 1-2. responseBody
            CustomAPIResponse<Object> responseBody = CustomAPIResponse.createFailWithoutData(HttpStatus.NOT_FOUND.value(), "해당하는 근로 결과지가 없습니다.");
            // 1-3. ResponseEntity
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(responseBody);
        }
        // 2. 결과 리턴
        // 2-1. data
        Worksheet worksheet = foundWorksheet.get();
        WorksheetGetOneResponseDto data = WorksheetGetOneResponseDto.builder()
                .nickname(worksheet.getUser().getNickname())
                .title(worksheet.getTitle())
                .content(worksheet.getContent())
                .extraPay(worksheet.isExtraPay())
                .weekPay(worksheet.isWeekPay())
                .nightPay(worksheet.isNightPay())
                .overtimePay(worksheet.isOvertimePay())
                .holidayPay(worksheet.isHolidayPay())
                .build();
        // 2-2. responseBody
        CustomAPIResponse<WorksheetGetOneResponseDto> responseBody = CustomAPIResponse.createSuccess(HttpStatus.OK.value(), data, "근로 결과지 세부 조회 완료되었습니다.");
        // 2-3. ResponseEntity
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBody);
    }

    @Override
    public ResponseEntity<CustomAPIResponse<?>> calculate(WorksheetCalculateRequestDto worksheetCalculateRequestDto) {
        // 수당 발생 여부
        Map<String, Boolean> payMap = new HashMap<>();
        // 수당
        Map<String, Integer> moneyMap = new HashMap<>();
        int hourPay = worksheetCalculateRequestDto.getHourPay();
        int totalPay;
        if (worksheetCalculateRequestDto.isOverFive()) {
            // 1. 주휴 수당
            int weekWork = worksheetCalculateRequestDto.getWeekWork(); // 주 근로 시간
            int weekMoney; // 주휴 수당
            if (weekWork >= 15) { // 주 근로 시간 15시간 이상 -> 주휴 수당 발생
                weekMoney = (int) ((weekWork * 0.025) * 8 * 4 * hourPay);
                moneyMap.put("weekMoney", weekMoney);
                payMap.put("weekPay", true);
            } else { // 주휴 수당 X
                weekMoney = 0;
                moneyMap.put("weekMoney", weekMoney);
                payMap.put("weekPay", false);
            }
            // 2. 연장 근로 수당
            int overtimeWork = worksheetCalculateRequestDto.getOvertimeWork();
            int overtimeMoney;
            if (overtimeWork != 0) { // 연장 근로 수당 발생 O
                overtimeMoney = (int) (overtimeWork * 0.5 * 4 * hourPay);
                moneyMap.put("overtimeMoney", overtimeMoney * 3);
                payMap.put("overtimePay", true);
            } else { // 연장 근로 수당 발생 X
                overtimeMoney = 0;
                moneyMap.put("overtimeMoney", overtimeMoney);
                payMap.put("overtimePay", false);
            }
            // 3. 야간 근로 수당
            int nightWork = worksheetCalculateRequestDto.getNightWork();
            int nightMoney;
            if (nightWork != 0) { // 야간 근로 수당 발생 O
                nightMoney = (int) (nightWork * 0.5 * 4 * hourPay);
                moneyMap.put("nightMoney", nightMoney * 3);
                payMap.put("nightPay", true);
            } else { // 야간 근로 수당 발생 X
                nightMoney = 0;
                moneyMap.put("nightMoney", nightMoney);
                payMap.put("nightPay", false);
            }
            // 4. 휴일 근로 수당
            int holidayWork = worksheetCalculateRequestDto.getHolidayWork();
            int holidayMoney;
            if (holidayWork > 8) {
                holidayMoney = holidayWork * hourPay;
                moneyMap.put("holidayMoney", holidayMoney * 2);
                payMap.put("holidayPay", true);
            } else if (holidayWork > 0) {
                holidayMoney = (int) (holidayWork * hourPay * 0.5);
                moneyMap.put("holidayMoney", holidayMoney * 3);
                payMap.put("holidayPay", true);
            } else {
                holidayMoney = 0;
                moneyMap.put("holidayMoney", holidayMoney);
                payMap.put("holidayPay", false);
            }
            totalPay = (int) (((hourPay * weekWork) * 4.34) + weekMoney + nightMoney + overtimeMoney + holidayMoney);
        } else {
            // 1. 주휴 수당
            int weekWork = worksheetCalculateRequestDto.getWeekWork(); // 주 근로 시간
            int weekMoney; // 주휴 수당
            if (weekWork >= 15) { // 주 근로 시간 15시간 이상 -> 주휴 수당 발생
                weekMoney = (int) ((weekWork * 0.025) * 8 * hourPay);
                moneyMap.put("weekMoney", weekMoney);
                payMap.put("weekPay", true);
            } else { // 주휴 수당 X
                weekMoney = 0;
                moneyMap.put("weekMoney", weekMoney);
                payMap.put("weekPay", false);
            }
            // 2. 연장 근로 수당 - 발생 X
            int overtimeMoney = 0;
            moneyMap.put("overtimeMoney", overtimeMoney);
            payMap.put("overtimePay", false);
            // 3. 야간 근로 수당 발생 X
            int nightMoney = 0;
            moneyMap.put("nightMoney", nightMoney);
            payMap.put("nightPay", false);
            // 4. 휴일 근로 수당 발생 X
            int holidayMoney = 0;
            moneyMap.put("holidayMoney", holidayMoney);
            payMap.put("holidayPay", false);
            totalPay = (int) (((hourPay * weekWork) * 4.34) + weekMoney + nightMoney + overtimeMoney + holidayMoney);
        }
        if (worksheetCalculateRequestDto.isMajorInsurance()) totalPay = (int) (totalPay * 0.9068);
        else if (worksheetCalculateRequestDto.isIncomeTax()) totalPay = (int) (totalPay * 0.967);

        // data
        WorksheetCalculateResponseDto data = WorksheetCalculateResponseDto.builder()
                .overFive(worksheetCalculateRequestDto.isOverFive())
                .weekWork(worksheetCalculateRequestDto.getWeekWork())
                .weekMoney(moneyMap.get("weekMoney"))
                .nightWork(worksheetCalculateRequestDto.getNightWork())
                .nightMoney(moneyMap.get("nightMoney"))
                .overtimeWork(worksheetCalculateRequestDto.getOvertimeWork())
                .overtimeMoney(moneyMap.get("overtimeMoney"))
                .holidayWork(worksheetCalculateRequestDto.getHolidayWork())
                .holidayMoney(moneyMap.get("holidayMoney"))
                .majorInsurance(worksheetCalculateRequestDto.isMajorInsurance())
                .incomeTax(worksheetCalculateRequestDto.isIncomeTax())
                .totalPay(totalPay)
                .extraPay(worksheetCalculateRequestDto.isOverFive())
                .weekPay(payMap.get("weekPay"))
                .nightPay(payMap.get("nightPay"))
                .overtimePay(payMap.get("overtimePay"))
                .holidayPay(payMap.get("holidayPay"))
                .build();
        // responseBody
        CustomAPIResponse<WorksheetCalculateResponseDto> responseBody = CustomAPIResponse.createSuccess(HttpStatus.OK.value(), data, "근로 결과지 계산이 완료되었습니다.");
        // ResponseEntity
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBody);
    }
}
