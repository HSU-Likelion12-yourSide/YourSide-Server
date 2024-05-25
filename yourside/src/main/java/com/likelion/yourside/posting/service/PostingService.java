package com.likelion.yourside.posting.service;

import com.likelion.yourside.posting.dto.PostingBookmarkRequestDto;
import com.likelion.yourside.posting.dto.PostingCreateRequestDto;
import com.likelion.yourside.util.response.CustomAPIResponse;
import org.springframework.http.ResponseEntity;

public interface PostingService {
    ResponseEntity<CustomAPIResponse<?>> createPosting(PostingCreateRequestDto postingCreateRequestDto);
    ResponseEntity<CustomAPIResponse<?>> createOrDeleteBookmark(PostingBookmarkRequestDto postingBookmarkRequestDto);
}
