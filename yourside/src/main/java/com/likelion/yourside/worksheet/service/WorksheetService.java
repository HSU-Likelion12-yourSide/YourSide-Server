package com.likelion.yourside.worksheet.service;

import com.likelion.yourside.util.response.CustomAPIResponse;
import com.likelion.yourside.worksheet.dto.WorksheetRegisterRequestDto;
import org.springframework.http.ResponseEntity;

public interface WorksheetService {
    ResponseEntity<CustomAPIResponse<?>> register(WorksheetRegisterRequestDto worksheetRegisterRequestDto);
    ResponseEntity<CustomAPIResponse<?>> share(Long worksheetId);
    ResponseEntity<CustomAPIResponse<?>> getAllList();
    ResponseEntity<CustomAPIResponse<?>> getOne(Long worksheetId);
}
