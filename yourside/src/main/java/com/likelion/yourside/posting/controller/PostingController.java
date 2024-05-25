package com.likelion.yourside.posting.controller;

import com.likelion.yourside.posting.dto.PostingBookmarkRequestDto;
import com.likelion.yourside.posting.dto.PostingCreateRequestDto;
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

    @PostMapping
    public ResponseEntity<CustomAPIResponse<?>> createPosting(@RequestBody PostingCreateRequestDto postingCreateRequestDto) {
        return postingService.createPosting(postingCreateRequestDto);
    }
    @PostMapping("/bookmarks")
    public ResponseEntity<CustomAPIResponse<?>> createOrDeleteBookmark(@RequestBody PostingBookmarkRequestDto postingBookmarkRequestDto) {
        return postingService.createOrDeleteBookmark(postingBookmarkRequestDto);
    }

}
