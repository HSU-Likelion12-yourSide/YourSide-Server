package com.likelion.yourside.posting.service;

import com.likelion.yourside.bookmark.repository.BookmarkRepository;
import com.likelion.yourside.domain.Bookmark;
import com.likelion.yourside.domain.Posting;
import com.likelion.yourside.domain.User;
import com.likelion.yourside.domain.Worksheet;
import com.likelion.yourside.posting.dto.*;
import com.likelion.yourside.posting.repository.PostingRepository;
import com.likelion.yourside.user.repository.UserRepository;
import com.likelion.yourside.util.response.CustomAPIResponse;
import com.likelion.yourside.worksheet.repository.WorksheetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PostingServiceImpl implements PostingService{
    private final PostingRepository postingRepository;
    private final UserRepository userRepository;
    private final WorksheetRepository worksheetRepository;
    private final BookmarkRepository bookmarkRepository;

    // 게시글 추가 ------------------------------------------------------------------------------------------------------------------------------------------
    @Override
    public ResponseEntity<CustomAPIResponse<?>> createPosting(PostingCreateResponseDto postingCreateResponseDto) {
        // 1. 사용자 존재 여부 확인
        Optional<User> foundUser = userRepository.findById(postingCreateResponseDto.getUserId());
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
        Optional<Worksheet> foundWorksheet = worksheetRepository.findById(postingCreateResponseDto.getWorksheetId());
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
                .title(postingCreateResponseDto.getTitle())
                .content(postingCreateResponseDto.getContent())
                .bookmarkCount(0)
                .user(user)
                .worksheet(worksheet)
                .type(postingCreateResponseDto.getType())
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

    //북마크 추가/해제 ----------------------------------------------------------------------------------------------------------------------
    @Override
    public ResponseEntity<CustomAPIResponse<?>> createOrDeleteBookmark(PostingBookmarkRequestDto postingBookmarkRequestDto) {
        // 1. 존재하는 User인지 조회
        Optional<User> foundUser = userRepository.findById(postingBookmarkRequestDto.getUserId());
        if (foundUser.isEmpty()) {
            // 1-1. data
            // 1-2. responseBody
            CustomAPIResponse<Object> responseBody = CustomAPIResponse.createFailWithoutData(HttpStatus.NOT_FOUND.value(), "해당하는 사용자가 존재하지 않습니다.");
            // 1-3. responseEntity
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(responseBody);
        }
        User user = foundUser.get();
        // 2. 존재하는 Posting인지 조회
        Optional<Posting> foundPosting = postingRepository.findById(postingBookmarkRequestDto.getPostId());
        if (foundPosting.isEmpty()) {
            // 2-1. data
            // 2-2. responseBody
            CustomAPIResponse<Object> responseBody = CustomAPIResponse.createFailWithoutData(HttpStatus.NOT_FOUND.value(), "해당하는 게시글이 존재하지 않습니다.");
            // 2-3. responseEntity
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(responseBody);
        }
        Posting posting = foundPosting.get();
        // 3. 북마크 해제
        if (postingBookmarkRequestDto.isBookmarked()) {
            Optional<Bookmark> foundBookmark = bookmarkRepository.findByUserAndPosting(user, posting);
            // 3-1. 북마크 존재 여부
            if (foundBookmark.isEmpty()) {
                // 3-1-1. data
                // 3-1-2. responseBody
                CustomAPIResponse<Object> responseBody = CustomAPIResponse.createFailWithoutData(HttpStatus.NOT_FOUND.value(), "해당하는 북마크가 존재하지 않습니다.");
                // 3-1-3. ResponseEntity
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(responseBody);
            }
            // 3-2. 북마크 해제
            Bookmark bookmark = foundBookmark.get();
            bookmarkRepository.delete(bookmark);
            // 3-3. Posting 북마크 수 감소
            posting.changeBookmarkCount(false);
            postingRepository.save(posting);
            // 3-4. 응답
            // 3-4-1. data
            // 3-4-2. responseBody
            CustomAPIResponse<Object> responseBody = CustomAPIResponse.createSuccessWithoutData(HttpStatus.OK.value(), "해당 게시글이 책갈피에서 삭제되었습니다.");
            // 3-4-3. ResponseEntity
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(responseBody);
        }
        // 4. 북마크 등록
        else{
            // 4-1. 북마크 존재하는지 확인
            Optional<Bookmark> foundBookmark = bookmarkRepository.findByUserAndPosting(user, posting);
            if (foundBookmark.isPresent()) {
                // 4-1-1. data
                // 4-1-2. responseBody
                CustomAPIResponse<Object> responseBody = CustomAPIResponse.createFailWithoutData(HttpStatus.CONFLICT.value(), "이미 존재하는 북마크입니다.");
                // 4-1-3. ResponseEntity
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(responseBody);
            }
            // 4-2. 북마크 등록
            Bookmark bookmark = Bookmark.builder()
                    .posting(posting)
                    .user(user)
                    .build();
            bookmarkRepository.save(bookmark);
            // 4-3. Posting 북마크 수 증가
            posting.changeBookmarkCount(true);
            postingRepository.save(posting);
            // 4-4. 응답
            // 4-4-1. data
            // 4-4-2. responseBody
            CustomAPIResponse<Object> responseBody = CustomAPIResponse.createSuccessWithoutData(HttpStatus.CREATED.value(), "해당 게시글이 책갈피에 추가되었습니다.");
            // 4-4-3. ResponseEntity
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(responseBody);
        }

    }

    //모든 게시글 조회 ----------------------------------------------------------------------------------------
    @Override
    public ResponseEntity<CustomAPIResponse<?>> getAllPosting() {

        List<Posting> postings = postingRepository.findAll();

        //게시글이 존재하지 않는 경우
        if (postings.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(CustomAPIResponse.createFailWithoutData(HttpStatus.OK.value(), "게시글이 존재하지 않습니다."));
        }

        //반환
        List<PostingListDto.PostingResponse> postingListResponseDtos = new ArrayList<>();
        for (Posting posting : postings) {
            postingListResponseDtos.add(PostingListDto.PostingResponse.builder()
                            .title(posting.getTitle())
                            .content(posting.getContent())
                            .created_at(posting.localDateTimeToString())
                            .bookmark_count(posting.getBookmarkCount())
                    .build());
        }

        //최종 데이터
        CustomAPIResponse<List<PostingListDto.PostingResponse>> res = CustomAPIResponse.createSuccess(HttpStatus.OK.value(), postingListResponseDtos, null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    //인기 게시글 조회 (상위 3개) ----------------------------------------------------------------------------------
    @Override
    public ResponseEntity<CustomAPIResponse<?>> getPopularPosting(int type) {
        List<Posting> postings = postingRepository.findByType(type);

        //게시글이 존재하지 않는 경우
        if (postings.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(CustomAPIResponse.createFailWithoutData(HttpStatus.OK.value(), "게시글이 존재하지 않습니다."));
        }

        //sorting : bookmarks 기준으로
        List<Posting> top3Postings = new ArrayList<>(postings);
        Collections.sort(top3Postings, new Comparator<Posting>() {
            @Override
            public int compare(Posting o1, Posting o2) {
                return Integer.compare(o2.getBookmarkCount(),o1.getBookmarkCount()); //내림차순으로 정렬
            }
        });
        if (top3Postings.size() > 3) {
            top3Postings = top3Postings.subList(0, 3);
        }

        //반환
        List<PostingPopularListDto.PostingResponse> popularListResponseDto = new ArrayList<>();
        for (Posting posting : top3Postings) {
            popularListResponseDto.add(PostingPopularListDto.PostingResponse.builder()
                    .title(posting.getTitle())
                    .content(posting.getContent())
                    .build());
        }

        //최종 데이터
        CustomAPIResponse<List<PostingPopularListDto.PostingResponse>> res = CustomAPIResponse.createSuccess(HttpStatus.OK.value(), popularListResponseDto, "인기 게시글 조회 완료되었습니다.");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    //게시글 세부 조회 ----------------------------------------------------------------------------------------------
    @Override
    public ResponseEntity<CustomAPIResponse<?>> getOnePosting(Long postingId) {
        Optional<Posting> optionalPosting = postingRepository.findById(postingId);

        //404 : 게시글이 없는 경우
        if (optionalPosting.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(CustomAPIResponse.createFailWithoutData(HttpStatus.NOT_FOUND.value(), "삭제되었거나 존재하지 않는 게시글입니다."));
        }

        //변환
        Posting posting = optionalPosting.get();
        Optional<Bookmark> optionalBookmark = bookmarkRepository.findByUserAndPosting(posting.getUser(), posting);
        boolean isBookmarked = optionalBookmark.isPresent();

        PostingSearchResponseDto postingSearchResponseDto = PostingSearchResponseDto.builder()
                .title(posting.getTitle())
                .nickname(posting.getUser().getNickname())
                .createdAt(posting.localDateTimeToString())
                .content(posting.getContent())
                .bookmarked(isBookmarked)
                .worksheetId(posting.getWorksheet().getId())
                .build();
        //200 : 게시글 조회 성공
        CustomAPIResponse<PostingSearchResponseDto> res = CustomAPIResponse.createSuccess(HttpStatus.OK.value(), postingSearchResponseDto, "게시글 조회에 성공했습니다.");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}
