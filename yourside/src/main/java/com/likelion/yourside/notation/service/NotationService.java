package com.likelion.yourside.notation.service;

import com.likelion.yourside.notation.dto.NotationDto;
import com.likelion.yourside.util.response.CustomAPIResponse;
import org.springframework.http.ResponseEntity;

public interface NotationService {

    //데이터베이스에 저장되어 있는 해당 공지사항의 정보 얻어오기
    ResponseEntity<CustomAPIResponse<?>> getOneNotation(Long notationId);

    //데이터베이스에 저장되어 있는 모든 공지사항 가져오기
    ResponseEntity<CustomAPIResponse<?>> getAllNotation();

    ResponseEntity<CustomAPIResponse<?>> create(NotationDto.NotationResponse notationDto);
}
