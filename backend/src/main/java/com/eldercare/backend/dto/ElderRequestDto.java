package com.eldercare.backend.dto;

import com.eldercare.backend.entity.Elder;
import jakarta.validation.constraints.*;

public class ElderRequestDto {

    @NotBlank(message = "이름은 필수입니다")
    private String name;

    @NotNull(message = "나이는 필수입니다")
    @Min(value = 0, message = "나이는 0 이상이어야 합니다")
    @Max(value = 150, message = "나이는 150 이하여야 합니다")
    private Integer age;

    @Pattern(regexp = "^01[0-9]-?[0-9]{3,4}-?[0-9]{4}$", message = "올바른 전화번호 형식이 아닙니다")
    private String phoneNumber;

    private String address;

    private Elder.HealthStatus healthStatus;

    private String notes;

    // 기본 생성자
    public ElderRequestDto() {
    }

    // Getters and Setters
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

    // Entity로 변환
    public Elder toEntity() {
        return new Elder(name, age, phoneNumber, address, healthStatus, notes);
    }
}