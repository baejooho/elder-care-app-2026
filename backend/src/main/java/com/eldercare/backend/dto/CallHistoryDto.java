package com.eldercare.backend.dto;

import com.eldercare.backend.entity.CallHistory;
import java.time.LocalDateTime;

public class CallHistoryDto {

    private Long id;
    private String callerName;
    private String callerType;
    private Integer duration;
    private LocalDateTime callTime;
    private String status;
    private String recordingUrl;
    private String aiSummary;

    // Constructor from Entity
    public CallHistoryDto(CallHistory callHistory) {
        this.id = callHistory.getId();
        this.callerName = callHistory.getCallerName();
        this.callerType = callHistory.getCallerType();
        this.duration = callHistory.getDuration();
        this.callTime = callHistory.getCallTime();
        this.status = callHistory.getStatus().name();
        this.recordingUrl = callHistory.getRecordingUrl();
        this.aiSummary = callHistory.getAiSummary();
    }

    // Request DTO
    public static class Request {
        private String callerName;
        private String callerType;
        private Integer duration;
        private String status;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getCallerName() {
        return callerName;
    }

    public String getCallerType() {
        return callerType;
    }

    public Integer getDuration() {
        return duration;
    }

    public LocalDateTime getCallTime() {
        return callTime;
    }

    public String getStatus() {
        return status;
    }

    public String getRecordingUrl() {
        return recordingUrl;
    }

    public String getAiSummary() {
        return aiSummary;
    }
}