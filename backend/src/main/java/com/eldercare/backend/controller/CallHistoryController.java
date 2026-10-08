package com.eldercare.backend.controller;

import com.eldercare.backend.dto.CallHistoryDto;
import com.eldercare.backend.service.CallHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/call-history")
@CrossOrigin(origins = "*")
public class CallHistoryController {

    private final CallHistoryService callHistoryService;

    public CallHistoryController(CallHistoryService callHistoryService) {
        this.callHistoryService = callHistoryService;
    }

    // 최근 통화 내역
    @GetMapping("/recent")
    public ResponseEntity<List<CallHistoryDto>> getRecentCalls() {
        return ResponseEntity.ok(callHistoryService.getRecentCalls());
    }

    // 전체 통화 내역
    @GetMapping
    public ResponseEntity<List<CallHistoryDto>> getAllCalls() {
        return ResponseEntity.ok(callHistoryService.getAllCalls());
    }

    // 오늘 통화 내역
    @GetMapping("/today")
    public ResponseEntity<List<CallHistoryDto>> getTodayCalls() {
        return ResponseEntity.ok(callHistoryService.getTodayCalls());
    }

    // 통화 기록 저장
    @PostMapping
    public ResponseEntity<CallHistoryDto> saveCallHistory(@RequestBody CallHistoryDto.Request request) {
        return ResponseEntity.ok(callHistoryService.saveCallHistory(request));
    }
}