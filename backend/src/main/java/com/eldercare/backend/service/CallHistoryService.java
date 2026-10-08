package com.eldercare.backend.service;

import com.eldercare.backend.dto.CallHistoryDto;
import com.eldercare.backend.entity.CallHistory;
import com.eldercare.backend.repository.CallHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CallHistoryService {

    private final CallHistoryRepository callHistoryRepository;

    public CallHistoryService(CallHistoryRepository callHistoryRepository) {
        this.callHistoryRepository = callHistoryRepository;
    }

    // 최근 통화 내역 조회
    public List<CallHistoryDto> getRecentCalls() {
        return callHistoryRepository.findTop10ByOrderByCallTimeDesc()
                .stream()
                .map(CallHistoryDto::new)
                .collect(Collectors.toList());
    }

    // 전체 통화 내역 조회
    public List<CallHistoryDto> getAllCalls() {
        return callHistoryRepository.findAll()
                .stream()
                .map(CallHistoryDto::new)
                .collect(Collectors.toList());
    }

    // 오늘 통화 내역
    public List<CallHistoryDto> getTodayCalls() {
        return callHistoryRepository.findTodayCalls()
                .stream()
                .map(CallHistoryDto::new)
                .collect(Collectors.toList());
    }

    // 통화 기록 저장
    @Transactional
    public CallHistoryDto saveCallHistory(CallHistoryDto.Request request) {
        CallHistory callHistory = new CallHistory(
                request.getCallerName(),
                request.getCallerType(),
                request.getDuration(),
                LocalDateTime.now(),
                CallHistory.CallStatus.valueOf(request.getStatus())
        );

        CallHistory saved = callHistoryRepository.save(callHistory);
        return new CallHistoryDto(saved);
    }
}