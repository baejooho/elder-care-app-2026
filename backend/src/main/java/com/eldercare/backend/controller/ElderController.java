package com.eldercare.backend.controller;

import com.eldercare.backend.dto.ElderRequestDto;
import com.eldercare.backend.dto.ElderResponseDto;
import com.eldercare.backend.entity.Elder;
import com.eldercare.backend.service.ElderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/elders")
public class ElderController {

    private final ElderService elderService;

    @Autowired
    public ElderController(ElderService elderService) {
        this.elderService = elderService;
    }

    // 전체 조회
    @GetMapping
    public ResponseEntity<List<ElderResponseDto>> getAllElders() {
        List<ElderResponseDto> elders = elderService.getAllElders();
        return ResponseEntity.ok(elders);
    }

    // ID로 조회
    @GetMapping("/{id}")
    public ResponseEntity<ElderResponseDto> getElderById(@PathVariable Long id) {
        ElderResponseDto elder = elderService.getElderById(id);
        return ResponseEntity.ok(elder);
    }

    // 생성
    @PostMapping
    public ResponseEntity<ElderResponseDto> createElder(@Valid @RequestBody ElderRequestDto requestDto) {
        ElderResponseDto createdElder = elderService.createElder(requestDto);
        return new ResponseEntity<>(createdElder, HttpStatus.CREATED);
    }

    // 수정
    @PutMapping("/{id}")
    public ResponseEntity<ElderResponseDto> updateElder(
            @PathVariable Long id,
            @Valid @RequestBody ElderRequestDto requestDto) {
        ElderResponseDto updatedElder = elderService.updateElder(id, requestDto);
        return ResponseEntity.ok(updatedElder);
    }

    // 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteElder(@PathVariable Long id) {
        elderService.deleteElder(id);
        return ResponseEntity.noContent().build();
    }

    // 이름으로 검색
    @GetMapping("/search")
    public ResponseEntity<List<ElderResponseDto>> searchByName(@RequestParam String name) {
        List<ElderResponseDto> elders = elderService.searchByName(name);
        return ResponseEntity.ok(elders);
    }

    // 건강 상태로 검색
    @GetMapping("/health-status/{status}")
    public ResponseEntity<List<ElderResponseDto>> findByHealthStatus(@PathVariable Elder.HealthStatus status) {
        List<ElderResponseDto> elders = elderService.findByHealthStatus(status);
        return ResponseEntity.ok(elders);
    }

    // 주의 필요 어르신 조회
    @GetMapping("/attention-needed")
    public ResponseEntity<List<ElderResponseDto>> getEldersNeedingAttention() {
        List<ElderResponseDto> elders = elderService.findEldersNeedingAttention();
        return ResponseEntity.ok(elders);
    }
}