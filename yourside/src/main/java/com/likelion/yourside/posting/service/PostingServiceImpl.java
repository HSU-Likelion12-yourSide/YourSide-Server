package com.likelion.yourside.posting.service;

import com.likelion.yourside.domain.Posting;
import com.likelion.yourside.domain.User;
import com.likelion.yourside.domain.Worksheet;
import com.likelion.yourside.posting.dto.PostingCreateRequestDto;
import com.likelion.yourside.posting.repository.PostingRepository;
import com.likelion.yourside.user.repository.UserRepository;
import com.likelion.yourside.util.response.CustomAPIResponse;
import com.likelion.yourside.worksheet.repository.WorksheetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostingServiceImpl implements PostingService{
    private final PostingRepository postingRepository;
    private final UserRepository userRepository;
    private final WorksheetRepository worksheetRepository;

    @Override
    public ResponseEntity<CustomAPIResponse<?>> createPosting(PostingCreateRequestDto postingCreateRequestDto) {
        // 1. 사용자 존재 여부 확인
        Optional<User> foundUser = userRepository.findById(postingCreateRequestDto.getUserId());
        if (foundUser.isEmpty()) {
            // 1-1. data
            // 1-2. responseBody
            CustomAPIResponse<Object> responseBody = CustomAPIResponse.createFailWithoutData(HttpStatus.NOT_FOUND.value(), "일치하는 사용자가 존재하지 않습니다.");
            // 1-3. ResponseEntity
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(responseBody);
        }
        User user = foundUser.get();
        // 2. 선택한 근로 결과지 존재 여부 확인
        Optional<Worksheet> foundWorksheet = worksheetRepository.findById(postingCreateRequestDto.getWorksheetId());
        if (foundWorksheet.isEmpty()) {
            // 1-1. data
            // 1-2. responseBody
            CustomAPIResponse<Object> responseBody = CustomAPIResponse.createFailWithoutData(HttpStatus.NOT_FOUND.value(), "일치하는 근로 결과지가 존재하지 않습니다.");
            // 1-3. ResponseEntity
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(responseBody);
        }
        Worksheet worksheet = foundWorksheet.get();
        // 3. 저장
        Posting posting = Posting.builder()
                .title(postingCreateRequestDto.getTitle())
                .content(postingCreateRequestDto.getContent())
                .bookmarkCount(0)
                .user(user)
                .worksheet(worksheet)
                .build();
        postingRepository.save(posting);
        // 4. 응답
        // 4-1. data
        // 4-2. responseBody
        CustomAPIResponse<Object> responseBody = CustomAPIResponse.createSuccessWithoutData(HttpStatus.CREATED.value(), "게시글이 등록되었습니다.");
        // 4-3. ResponseEntity
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseBody);

    }
}
