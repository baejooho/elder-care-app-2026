package com.eldercare.backend.service;

import com.eldercare.backend.dto.ElderRequestDto;
import com.eldercare.backend.dto.ElderResponseDto;
import com.eldercare.backend.entity.Elder;
import com.eldercare.backend.exception.ResourceNotFoundException;
import com.eldercare.backend.repository.ElderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ElderService {

    private final ElderRepository elderRepository;

    @Autowired
    public ElderService(ElderRepository elderRepository) {
        this.elderRepository = elderRepository;
    }

    // 전체 조회
    public List<ElderResponseDto> getAllElders() {
        return elderRepository.findAll()
                .stream()
                .map(ElderResponseDto::new)
                .collect(Collectors.toList());
    }

    // ID로 조회
    public ElderResponseDto getElderById(Long id) {
        Elder elder = elderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("어르신을 찾을 수 없습니다. ID: " + id));
        return new ElderResponseDto(elder);
    }

    // 생성
    @Transactional
    public ElderResponseDto createElder(ElderRequestDto requestDto) {
        Elder elder = requestDto.toEntity();
        Elder savedElder = elderRepository.save(elder);
        return new ElderResponseDto(savedElder);
    }

    // 수정
    @Transactional
    public ElderResponseDto updateElder(Long id, ElderRequestDto requestDto) {
        Elder elder = elderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("어르신을 찾을 수 없습니다. ID: " + id));

        elder.setName(requestDto.getName());
        elder.setAge(requestDto.getAge());
        elder.setPhoneNumber(requestDto.getPhoneNumber());
        elder.setAddress(requestDto.getAddress());
        elder.setHealthStatus(requestDto.getHealthStatus());
        elder.setNotes(requestDto.getNotes());

        Elder updatedElder = elderRepository.save(elder);
        return new ElderResponseDto(updatedElder);
    }

    // 삭제
    @Transactional
    public void deleteElder(Long id) {
        Elder elder = elderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("어르신을 찾을 수 없습니다. ID: " + id));
        elderRepository.delete(elder);
    }

    // 이름으로 검색
    public List<ElderResponseDto> searchByName(String name) {
        return elderRepository.findByNameContaining(name)
                .stream()
                .map(ElderResponseDto::new)
                .collect(Collectors.toList());
    }

    // 건강 상태로 검색
    public List<ElderResponseDto> findByHealthStatus(Elder.HealthStatus healthStatus) {
        return elderRepository.findByHealthStatus(healthStatus)
                .stream()
                .map(ElderResponseDto::new)
                .collect(Collectors.toList());
    }

    // 주의 필요 어르신 조회
    public List<ElderResponseDto> findEldersNeedingAttention() {
        return elderRepository.findEldersWithAttentionRisk()
                .stream()
                .map(ElderResponseDto::new)
                .collect(Collectors.toList());
    }
}