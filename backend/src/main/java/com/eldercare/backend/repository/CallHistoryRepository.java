package com.eldercare.backend.repository;

import com.eldercare.backend.entity.CallHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CallHistoryRepository extends JpaRepository<CallHistory, Long> {

    // 최근 통화 내역 (최신순)
    List<CallHistory> findTop10ByOrderByCallTimeDesc();

    // 특정 기간 통화 내역
    List<CallHistory> findByCallTimeBetweenOrderByCallTimeDesc(LocalDateTime start, LocalDateTime end);

    // 특정 타입 통화 내역
    List<CallHistory> findByCallerTypeOrderByCallTimeDesc(String callerType);

    // 오늘 통화 내역
    @Query("SELECT c FROM CallHistory c WHERE CAST(c.callTime AS DATE) = CURRENT_DATE ORDER BY c.callTime DESC")
    List<CallHistory> findTodayCalls();
}