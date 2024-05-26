package com.likelion.yourside.posting.controller;

import com.likelion.yourside.posting.dto.PostingBookmarkRequestDto;
import com.likelion.yourside.posting.dto.PostingCreateResponseDto;
import com.likelion.yourside.posting.service.PostingService;
import com.likelion.yourside.util.response.CustomAPIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/posting")
@RequiredArgsConstructor
public class PostingController {
    private final PostingService postingService;

    //게시글 전체 조회
    @GetMapping("/list")
    public ResponseEntity<CustomAPIResponse<?>> getAllPosting() {
        return postingService.getAllPosting();
    }

    //인기 게시글 조회 (상위 3개)
    @GetMapping("/list/popular/{type}")
    public ResponseEntity<CustomAPIResponse<?>> getPopularPosting(
            @PathVariable int type) {
        return postingService.getPopularPosting(type);
    }

    //게시글 세부 조회
    @GetMapping("/{posting_id}")
    public ResponseEntity<CustomAPIResponse<?>> getPosting(
            @PathVariable Long posting_id) {
        return postingService.getOnePosting(posting_id);
    }

    //게시글 등록
    @PostMapping
    public ResponseEntity<CustomAPIResponse<?>> createPosting(@RequestBody PostingCreateResponseDto postingCreateResponseDto) {
        return postingService.createPosting(postingCreateResponseDto);
    }

    //게시글 책갈피 등록/해제
    @PostMapping("/bookmarks")
    public ResponseEntity<CustomAPIResponse<?>> createOrDeleteBookmark(@RequestBody PostingBookmarkRequestDto postingBookmarkRequestDto) {
        return postingService.createOrDeleteBookmark(postingBookmarkRequestDto);
    }

}
