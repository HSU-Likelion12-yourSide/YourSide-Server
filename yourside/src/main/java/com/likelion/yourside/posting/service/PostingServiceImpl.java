package com.likelion.yourside.posting.service;

import com.likelion.yourside.posting.repository.PostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostingServiceImpl implements PostingService{
    private final PostingRepository postingRepository;
}
