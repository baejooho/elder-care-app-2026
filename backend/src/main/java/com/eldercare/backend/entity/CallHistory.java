package com.eldercare.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "call_history")
public class CallHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String callerName;

    @Column(nullable = false)
    private String callerType; // AI, FAMILY, EMERGENCY

    @Column(nullable = false)
    private Integer duration; // 통화 시간 (초)

    @Column(nullable = false)
    private LocalDateTime callTime;

    @Column(length = 500)
    private String recordingUrl; // 녹음 파일 URL (나중에)

    @Column(length = 2000)
    private String aiSummary; // AI 분석 요약 (나중에)

    @Enumerated(EnumType.STRING)
    private CallStatus status;

    public enum CallStatus {
        COMPLETED, // 정상 종료
        MISSED,    // 부재중
        REJECTED   // 거절
    }

    // Constructors
    public CallHistory() {
    }

    public CallHistory(String callerName, String callerType, Integer duration, LocalDateTime callTime, CallStatus status) {
        this.callerName = callerName;
        this.callerType = callerType;
        this.duration = duration;
        this.callTime = callTime;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCallerName() {
        return callerName;
    }

    public void setCallerName(String callerName) {
        this.callerName = callerName;
    }

    public String getCallerType() {
        return callerType;
    }

    public void setCallerType(String callerType) {
        this.callerType = callerType;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public LocalDateTime getCallTime() {
        return callTime;
    }

    public void setCallTime(LocalDateTime callTime) {
        this.callTime = callTime;
    }

    public String getRecordingUrl() {
        return recordingUrl;
    }

    public void setRecordingUrl(String recordingUrl) {
        this.recordingUrl = recordingUrl;
    }

    public String getAiSummary() {
        return aiSummary;
    }

    public void setAiSummary(String aiSummary) {
        this.aiSummary = aiSummary;
    }

    public CallStatus getStatus() {
        return status;
    }

    public void setStatus(CallStatus status) {
        this.status = status;
    }
}