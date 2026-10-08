package com.eldercare.backend.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class FCMService {

    private static final Logger logger = LoggerFactory.getLogger(FCMService.class);

    public void sendNotification(String token, String title, String body) {
        try {
            Message message = Message.builder()
                .setToken(token)
                .setNotification(Notification.builder()
                    .setTitle(title)
                    .setBody(body)
                    .build())
                .build();
            String response = FirebaseMessaging.getInstance().send(message);
            logger.info("FCM 전송 성공: {}", response);
        } catch (Exception e) {
            logger.error("FCM 전송 실패: {}", e.getMessage());
        }
    }

    // 매일 9시 안부 전화 알림
    @Scheduled(cron = "0 0 9 * * *")
    public void sendMorningCall() {
        logger.info("매일 9시 안부 전화 알림 전송!");
        // TODO: DB에서 모든 어르신의 FCM 토큰 조회 후 순회
        // for (Elder elder : elders) {
        //     sendNotification(elder.getFcmToken(), 
        //         "📞 안부 전화가 왔어요!", 
        //         "가족 분께서 안부를 전하셨습니다.");
        // }
    }

    // 약물 복용 알림 (매일 오전 8시)
    @Scheduled(cron = "0 0 8 * * *")
    public void sendMedicineReminder() {
        logger.info("약물 복용 알림 전송!");
    }

    // 건강 데이터 이상 알림 (5분마다 체크)
    @Scheduled(cron = "0 */5 * * * *")
    public void checkHealthAnomalies() {
        logger.debug("건강 데이터 이상 감지 체크 중...");
    }
}
