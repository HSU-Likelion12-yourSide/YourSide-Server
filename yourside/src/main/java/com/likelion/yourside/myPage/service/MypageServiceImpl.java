package com.likelion.yourside.myPage.service;

import com.likelion.yourside.domain.Posting;
import com.likelion.yourside.domain.User;
import com.likelion.yourside.domain.Worksheet;
import com.likelion.yourside.myPage.dto.MypageGetuserinfoResponseDto;
import com.likelion.yourside.posting.repository.PostingRepository;
import com.likelion.yourside.user.repository.UserRepository;
import com.likelion.yourside.util.response.CustomAPIResponse;
import com.likelion.yourside.worksheet.repository.WorksheetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MypageServiceImpl implements MypageService{
    private final UserRepository userRepository;
    private final WorksheetRepository worksheetRepository;
    private final PostingRepository postingRepository;
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
        int commentCount = 0;
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
        for (Worksheet worksheet : worksheetList) {

        }

        // 2-2. responseBody
        // 2-3. ResponseEntity
        return null;
    }
}
