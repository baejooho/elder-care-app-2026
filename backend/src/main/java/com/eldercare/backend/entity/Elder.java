package com.eldercare.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "elders")
public class Elder extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "이름은 필수입니다")
    @Column(nullable = false, length = 50)
    private String name;

    @NotNull(message = "나이는 필수입니다")
    @Min(value = 0, message = "나이는 0 이상이어야 합니다")
    @Max(value = 150, message = "나이는 150 이하여야 합니다")
    @Column(nullable = false)
    private Integer age;

    @Column(length = 20)
    private String phoneNumber;

    @Column(length = 200)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private HealthStatus healthStatus;

    @Column(length = 500)
    private String notes;

    // 기본 생성자
    public Elder() {
    }

    // 생성자
    public Elder(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    // Full 생성자
    public Elder(String name, Integer age, String phoneNumber, String address, HealthStatus healthStatus, String notes) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.healthStatus = healthStatus;
        this.notes = notes;
    }

    // Getters and Setters
    public Long getId() {
        return id;
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

    public HealthStatus getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(HealthStatus healthStatus) {
        this.healthStatus = healthStatus;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    // 건강 상태 Enum
    public enum HealthStatus {
        GOOD("양호"),
        FAIR("보통"),
        POOR("주의"),
        CRITICAL("위험");

        private final String description;

        HealthStatus(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}