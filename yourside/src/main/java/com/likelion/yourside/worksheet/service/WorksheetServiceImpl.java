package com.likelion.yourside.worksheet.service;

import com.likelion.yourside.util.response.CustomAPIResponse;
import com.likelion.yourside.worksheet.repository.WorksheetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorksheetServiceImpl implements WorksheetService{
    private final WorksheetRepository worksheetRepository;

    @Override
    public ResponseEntity<CustomAPIResponse<?>> register(WorksheetRegisterRequestDto worksheetRegisterRequestDto) {
        return null;
    }
}
