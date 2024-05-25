package com.likelion.yourside.posting.controller;

import com.likelion.yourside.posting.dto.PostingCreateBookmarkRequestDto;
import com.likelion.yourside.posting.dto.PostingCreateRequestDto;
import com.likelion.yourside.posting.service.PostingService;
import com.likelion.yourside.util.response.CustomAPIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/posting")
@RequiredArgsConstructor
public class PostingController {
    private final PostingService postingService;

    @PostMapping
    public ResponseEntity<CustomAPIResponse<?>> createPosting(@RequestBody PostingCreateRequestDto postingCreateRequestDto) {
        return postingService.createPosting(postingCreateRequestDto);
    }

    @PostMapping("/bookmarks")
    public ResponseEntity<CustomAPIResponse<?>> createBookmark(@RequestBody PostingCreateBookmarkRequestDto postingCreateBookmarkRequestDto) {
        return postingService.createBookmark(postingCreateBookmarkRequestDto);
    }

}
