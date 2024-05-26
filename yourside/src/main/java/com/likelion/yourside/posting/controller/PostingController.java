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

    @GetMapping("/list")
    public ResponseEntity<CustomAPIResponse<?>> getAllPosting() {
        return postingService.getAllPosting();
    }

    @GetMapping("/list/popular/{type}")
    public ResponseEntity<CustomAPIResponse<?>> getPopularPosting(
            @PathVariable int type) {
        return postingService.getPopularPosting(type);
    }

    @PostMapping
    public ResponseEntity<CustomAPIResponse<?>> createPosting(@RequestBody PostingCreateResponseDto postingCreateResponseDto) {
        return postingService.createPosting(postingCreateResponseDto);
    }
    @PostMapping("/bookmarks")
    public ResponseEntity<CustomAPIResponse<?>> createOrDeleteBookmark(@RequestBody PostingBookmarkRequestDto postingBookmarkRequestDto) {
        return postingService.createOrDeleteBookmark(postingBookmarkRequestDto);
    }

}
