package com.likelion.yourside.posting.service;

import com.likelion.yourside.posting.dto.PostingBookmarkRequestDto;
import com.likelion.yourside.posting.dto.PostingCreateResponseDto;
import com.likelion.yourside.util.response.CustomAPIResponse;
import org.springframework.http.ResponseEntity;

public interface PostingService {
    ResponseEntity<CustomAPIResponse<?>> createPosting(PostingCreateResponseDto postingCreateResponseDto);
    ResponseEntity<CustomAPIResponse<?>> createOrDeleteBookmark(PostingBookmarkRequestDto postingBookmarkRequestDto);
    ResponseEntity<CustomAPIResponse<?>> getAllPosting();

    ResponseEntity<CustomAPIResponse<?>> getPopularPosting(int postingType);
}
