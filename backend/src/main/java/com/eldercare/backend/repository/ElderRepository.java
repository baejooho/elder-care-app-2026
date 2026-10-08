package com.eldercare.backend.repository;

import com.eldercare.backend.entity.Elder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ElderRepository extends JpaRepository<Elder, Long> {

    // 이름으로 검색
    List<Elder> findByNameContaining(String name);

    // 나이 범위로 검색
    List<Elder> findByAgeBetween(Integer minAge, Integer maxAge);

    // 건강 상태로 검색
    List<Elder> findByHealthStatus(Elder.HealthStatus healthStatus);

    // 전화번호로 검색
    Optional<Elder> findByPhoneNumber(String phoneNumber);

    // 주소로 검색
    List<Elder> findByAddressContaining(String address);

    // 커스텀 쿼리: 건강 상태가 주의 이상인 어르신
    @Query("SELECT e FROM Elder e WHERE e.healthStatus IN ('POOR', 'CRITICAL')")
    List<Elder> findEldersWithAttentionRisk();

    // 커스텀 쿼리: 특정 나이 이상
    @Query("SELECT e FROM Elder e WHERE e.age >= :age ORDER BY e.age DESC")
    List<Elder> findEldersOlderThan(@Param("age") Integer age);
}