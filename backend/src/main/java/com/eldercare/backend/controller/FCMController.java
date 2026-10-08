package com.eldercare.backend.controller;

import com.eldercare.backend.service.FCMService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fcm")
@CrossOrigin(origins = "*")
public class FCMController {

    private final FCMService fcmService;

    public FCMController(FCMService fcmService) {
        this.fcmService = fcmService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerToken(@RequestBody TokenRequest request) {
        System.out.println("FCM 토큰 등록: " + request.getToken());
        // TODO: 데이터베이스에 토큰 저장
        return ResponseEntity.ok("토큰 등록 완료");
    }

    @PostMapping("/test")
    public ResponseEntity<String> sendTest(@RequestBody TokenRequest request) {
        fcmService.sendNotification(request.getToken(), "안부 전화 📞", "안녕하세요!");
        return ResponseEntity.ok("알림 전송 완료");
    }

    public static class TokenRequest {
        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
