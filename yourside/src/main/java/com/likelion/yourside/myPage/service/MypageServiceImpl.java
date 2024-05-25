package com.likelion.yourside.myPage.service;

import com.likelion.yourside.bookmark.repository.BookmarkRepository;
import com.likelion.yourside.comment.repository.CommentRepository;
import com.likelion.yourside.domain.*;
import com.likelion.yourside.myPage.dto.MypageGetpostinglistResponseDto;
import com.likelion.yourside.myPage.dto.MypageGetuserinfoResponseDto;
import com.likelion.yourside.myPage.dto.MypageGetworksheetlistResponseDto;
import com.likelion.yourside.posting.repository.PostingRepository;
import com.likelion.yourside.user.repository.UserRepository;
import com.likelion.yourside.util.response.CustomAPIResponse;
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
public class MypageServiceImpl implements MypageService{
    private final UserRepository userRepository;
    private final WorksheetRepository worksheetRepository;
    private final PostingRepository postingRepository;
    private final CommentRepository commentRepository;
    private final BookmarkRepository bookmarkRepository;

    @Override
    public ResponseEntity<CustomAPIResponse<?>> getUserInfo(Long userId) {
        // 1. user 존재하는지 조회
        Optional<User> foundUser = userRepository.findById(userId);
        if (foundUser.isEmpty()) {
            // 1-1. data
            // 1-2. responseBody
            CustomAPIResponse<Object> responseBody = CustomAPIResponse.createFailWithoutData(HttpStatus.NOT_FOUND.value(), "사용자가 존재하지 않습니다.");
            // 1-3. ResponseEntity
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(responseBody);
        }
        User user = foundUser.get();
        // 2. 근로 결과지 개수 조회
        List<Worksheet> worksheetList = worksheetRepository.findAllByUser(user);
        int worksheetCount = worksheetList.size();
        // 3. 네편 현답 개수 조회
        List<Posting> postingList = postingRepository.findALlByUser(user);
        int postingCount = postingList.size();
        // 4. 답변 개수 조회
        List<Comment> commentList = commentRepository.findAllByUser(user);
        int commentCount = commentList.size();
        // 5. 응답
        // 5-1. data
        MypageGetuserinfoResponseDto data = MypageGetuserinfoResponseDto.builder()
                .worksheetCount(worksheetCount)
                .postingCount(postingCount)
                .commentCount(commentCount)
                .nickname(user.getNickname())
                .build();
        // 5-2. responseBody
        CustomAPIResponse<MypageGetuserinfoResponseDto> responseBody = CustomAPIResponse.createSuccess(HttpStatus.OK.value(), data, "사용자 정보 조회 완료되었습니다.");
        // 5-3. ResponseEntity
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBody);
    }
    @Override
    public ResponseEntity<CustomAPIResponse<?>> updateUserIsExpert(Long userId) {
        // 1. user 존재하는지 조회
        Optional<User> foundUser = userRepository.findById(userId);
        if (foundUser.isEmpty()) {
            // 1-1. data
            // 1-2. responseBody
            CustomAPIResponse<Object> responseBody = CustomAPIResponse.createFailWithoutData(HttpStatus.NOT_FOUND.value(), "존재하지 않는 사용자입니다.");
            // 1-3. ResponseEntity
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(responseBody);
        }
        // 2. isExpert -> true로 수정
        User user = foundUser.get();
        user.changeIsExpert();
        userRepository.save(user);
        // 3. 응답
        // 3-1. data
        // 3-2. responseBody
        CustomAPIResponse<Object> responseBody = CustomAPIResponse.createSuccessWithoutData(HttpStatus.OK.value(), "노무사 인증이 완료되었습니다.");
        // 3-3. ResponseEntity
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBody);
    }
    @Override
    public ResponseEntity<CustomAPIResponse<?>> getWorksheetList(Long userId) {
        // 1. 존재하는 user인지 조회
        Optional<User> foundUser = userRepository.findById(userId);
        if (foundUser.isEmpty()) {
            // 1-1. data
            // 1-2. responseBody
            CustomAPIResponse<Object> responseBody = CustomAPIResponse.createFailWithoutData(HttpStatus.NOT_FOUND.value(), "사용자가 존재하지 않습니다.");
            // 1-3. ResponseEntity
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(responseBody);
        }
        // 2. 응답
        User user = foundUser.get();
        // 2-1. data
        List<Worksheet> worksheetList = worksheetRepository.findAllByUser(user);
        List<MypageGetworksheetlistResponseDto> data = new ArrayList<>();
        for (Worksheet worksheet : worksheetList) {
            MypageGetworksheetlistResponseDto response = MypageGetworksheetlistResponseDto.builder()
                    .worksheetId(worksheet.getId())
                    .totalPay(worksheet.getTotalPay())
                    .extraPay(worksheet.isExtraPay())
                    .weekPay(worksheet.isWeekPay())
                    .nightPay(worksheet.isNightPay())
                    .overtimePay(worksheet.isOvertimePay())
                    .holidayPay(worksheet.isHolidayPay())
                    .createdAt(worksheet.localDateTimeToString())
                    .build();
            data.add(response);
        }
        // 2-2. responseBody
        CustomAPIResponse<List<MypageGetworksheetlistResponseDto>> responseBody = CustomAPIResponse.createSuccess(HttpStatus.OK.value(), data, "나의 근로 결과지 조회 완료되었습니다.");
        // 2-3. ResponseEntity
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBody);
    }
    @Override
    public ResponseEntity<CustomAPIResponse<?>> getPostingList(Long userId) {
        // 1. User 존재 여부 확인
        Optional<User> foundUser = userRepository.findById(userId);
        if (foundUser.isEmpty()) {
            // 1-1. data
            // 1-2. responseBody
            CustomAPIResponse<Object> responseBody = CustomAPIResponse.createFailWithoutData(HttpStatus.NOT_FOUND.value(), "일치하는 사용자가 없습니다.");
            // 1-3. ResponseEntity
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(responseBody);
        }
        // 2. 응답
        // 2-1. data
        User user = foundUser.get();
        List<Posting> postingList = postingRepository.findALlByUser(user);
        List<MypageGetpostinglistResponseDto> data = new ArrayList<>();
        for (Posting posting : postingList) {
            MypageGetpostinglistResponseDto responseDto = MypageGetpostinglistResponseDto.builder()
                    .postingId(posting.getId())
                    .title(posting.getTitle())
                    .content(posting.getContent())
                    .createdAt(posting.localDateTimeToString())
                    .build();
            data.add(responseDto);
        }
        // 2-2. reponseBody
        CustomAPIResponse<List<MypageGetpostinglistResponseDto>> responseBody = CustomAPIResponse.createSuccess(HttpStatus.OK.value(), data, "내 질문 조회 완료되었습니다.");
        // 2-3. ResponseEntity
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(responseBody);
    }
    @Override
    public ResponseEntity<CustomAPIResponse<?>> getBookmarkList(Long userId) {
        // 1. User 존재하는지 여부 확인
        Optional<User> foundUser = userRepository.findById(userId);
        if (foundUser.isEmpty()) {
            // 1-1. data
            // 1-2. responseBody
            CustomAPIResponse<Object> responseBody = CustomAPIResponse.createFailWithoutData(HttpStatus.NOT_FOUND.value(), "일치하는 사용자가 없습니다.");
            // 1-3. ResponseEntity
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(responseBody);
        }
        User user = foundUser.get();
        // 2. 응답
        // 2-1. data
        List<Bookmark> bookmarkList = bookmarkRepository.findAllByUser(user);
        List<MypageGetpostinglistResponseDto> data = new ArrayList<>();
        for (Bookmark bookmark : bookmarkList) {
            Posting posting = bookmark.getPosting();
            MypageGetpostinglistResponseDto responseDto = MypageGetpostinglistResponseDto.builder()
                    .postingId(posting.getId())
                    .title(posting.getTitle())
                    .content(posting.getContent())
                    .createdAt(posting.localDateTimeToString())
                    .build();
            data.add(responseDto);
        }
        // 2-2. responseBody
        CustomAPIResponse<List<MypageGetpostinglistResponseDto>> responseBody = CustomAPIResponse.createSuccess(HttpStatus.OK.value(), data, "내 책갈피 조회가 완료되었습니다.");
        // 2-3. ResponseEntity
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBody);

    }
}
