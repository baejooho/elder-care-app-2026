package com.eldercare.backend.dto;

import com.eldercare.backend.entity.Elder;

import java.time.LocalDateTime;

public class ElderResponseDto {

    private Long id;
    private String name;
    private Integer age;
    private String phoneNumber;
    private String address;
    private Elder.HealthStatus healthStatus;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 기본 생성자
    public ElderResponseDto() {
    }

    // Entity로부터 생성
    public ElderResponseDto(Elder elder) {
        this.id = elder.getId();
        this.name = elder.getName();
        this.age = elder.getAge();
        this.phoneNumber = elder.getPhoneNumber();
        this.address = elder.getAddress();
        this.healthStatus = elder.getHealthStatus();
        this.notes = elder.getNotes();
        this.createdAt = elder.getCreatedAt();
        this.updatedAt = elder.getUpdatedAt();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Elder.HealthStatus getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(Elder.HealthStatus healthStatus) {
        this.healthStatus = healthStatus;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}