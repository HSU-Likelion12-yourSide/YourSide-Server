package com.likelion.yourside.worksheet.controller;

import com.likelion.yourside.util.response.CustomAPIResponse;
import com.likelion.yourside.worksheet.dto.WorksheetRegisterRequestDto;
import com.likelion.yourside.worksheet.service.WorksheetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/worksheet")
public class WorksheetController {
    private final WorksheetService worksheetService;

    @PostMapping
    public ResponseEntity<CustomAPIResponse<?>> register(@RequestBody WorksheetRegisterRequestDto worksheetRegisterRequestDto) {
        return worksheetService.register(worksheetRegisterRequestDto);
    }
    @PutMapping("/{worksheet_id}")
    public ResponseEntity<CustomAPIResponse<?>> share(@PathVariable("worksheet_id") Long worksheetId) {
        return worksheetService.share(worksheetId);
    }
    @GetMapping("/list")
    public ResponseEntity<CustomAPIResponse<?>> getAllList() {
        return worksheetService.getAllList();
    }

}
