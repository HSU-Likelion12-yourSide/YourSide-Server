package com.likelion.yourside.worksheet.service;

import com.likelion.yourside.worksheet.repository.WorksheetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorksheetServiceImpl implements WorksheetService{
    private final WorksheetRepository worksheetRepository;
}
