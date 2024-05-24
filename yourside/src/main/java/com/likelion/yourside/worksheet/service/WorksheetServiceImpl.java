package com.likelion.yourside.worksheet.service;

import com.likelion.yourside.domain.User;
import com.likelion.yourside.domain.Worksheet;
import com.likelion.yourside.user.repository.UserRepository;
import com.likelion.yourside.util.response.CustomAPIResponse;
import com.likelion.yourside.worksheet.dto.WorksheetGetAllListResponseDto;
import com.likelion.yourside.worksheet.dto.WorksheetGetOneResponseDto;
import com.likelion.yourside.worksheet.dto.WorksheetRegisterRequestDto;
import com.likelion.yourside.worksheet.dto.WorksheetRegisterResponseDto;
import com.likelion.yourside.worksheet.repository.WorksheetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorksheetServiceImpl implements WorksheetService{
    private final WorksheetRepository worksheetRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<CustomAPIResponse<?>> register(WorksheetRegisterRequestDto worksheetRegisterRequestDto) {
        // 1. userId에 해당하는 유저 있는지 확인
        Optional<User> foundUser = userRepository.findById(worksheetRegisterRequestDto.getUser_id());
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
                .totalPay(worksheetRegisterRequestDto.getTotal_pay())
                .extraPay(worksheetRegisterRequestDto.isExtra_pay())
                .weekPay(worksheetRegisterRequestDto.isWeek_pay())
                .nightPay(worksheetRegisterRequestDto.isNight_pay())
                .overtimePay(worksheetRegisterRequestDto.isOvertime_pay())
                .holidayPay(worksheetRegisterRequestDto.isHoliday_pay())
                .isOpen(false)
                .user(user)
                .build();
        worksheetRepository.save(worksheet);
        // 2-1. data
        Long id = worksheet.getId();
        WorksheetRegisterResponseDto data = WorksheetRegisterResponseDto.builder()
                .worksheet_id(id)
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
            CustomAPIResponse<Object> responseBody = CustomAPIResponse.createSuccessWithoutData(HttpStatus.NO_CONTENT.value(), "조회된 근로 결과지가 없습니다.");
            // 1-3. ResponseEntity
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
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
}
