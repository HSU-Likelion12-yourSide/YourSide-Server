package com.likelion.yourside.worksheet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorksheetServiceImpl {
    private final WorksheetRepository worksheetRepository;
}
