package com.likelion.yourside.worksheet.controller;

import com.likelion.yourside.util.response.CustomAPIResponse;
import com.likelion.yourside.worksheet.dto.WorksheetRegisterRequestDto;
import com.likelion.yourside.worksheet.service.WorksheetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/worksheet")
public class WorksheetController {
    private final WorksheetService worksheetService;

    @PostMapping
    public ResponseEntity<CustomAPIResponse<?>> register(@RequestBody WorksheetRegisterRequestDto worksheetRegisterRequestDto) {
        return worksheetService.register(worksheetRegisterRequestDto);
    }
}
