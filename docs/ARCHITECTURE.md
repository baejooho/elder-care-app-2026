# 늘봄 — 아키텍처 설계 문서

## 1. 개요

늘봄은 Flutter 클라이언트와 Spring Boot API로 나뉜다.
어르신 데이터와 통화 기록은 PostgreSQL에 두고, 로그인·연결코드·AI 친구
이름은 현재 앱 로컬 저장소에 둔다. 대화 문장은 Gemini API가 만든다.

## 2. 시스템 구성

```
[어르신 앱]                         [보호자 앱]
  가입/로그인 (로컬)                   가입/로그인 (로컬)
  AI 대화, 통화기록, 비상연락           연결코드 입력, 요약, 건강 알림
        |                                      |
        +---------------- Flutter --------------+
                         |
                         | HTTP  /api/...
                         | STOMP WebSocket
                         v
              [Spring Boot :8080]
                ElderController        /api/elders
                CallHistoryController  /api/call-history
                ConversationController /api/conversation
                FCMController          /api/fcm
                ChatController         /app/chat.*
                         |                    |
                         v                    v
                  [PostgreSQL]          [Gemini API]
                  elders, call_history   gemini-2.5-flash
                         |
                         v
                  [Firebase FCM]  토큰 등록은 수신만, 저장은 미구현
```

## 3. 컴포넌트

### 3.1 Flutter 앱 (`flutter_app`)

- `main.dart`: Firebase 초기화 후 로그인 상태와 `user_type`으로
  어르신 홈, 보호자 홈, 역할 선택 화면을 고른다.
- `AuthService`: 가입, 로그인, 로그아웃, 6자리 연결코드를
  `SharedPreferences`에 저장한다. 서버 세션은 없다.
- `ApiService`: 통화 기록, FCM 등록, 대화, 어르신 조회.
  베이스 URL은 `http://10.0.2.2:8080`.
- 어르신 탭: `MainScreen`(홈), `HistoryScreen`(통화기록),
  `EmergencyScreen`(비상연락).
- 보호자: `GuardianScreen`에서 연결 어르신, 주간 요약, 건강 알림으로
  이동한다.

### 3.2 API (`backend`)

- `Elder`는 이름, 나이, 전화, 주소, 건강 상태, 메모를 가진다.
  이름·나이는 필수이고, 나이는 0~150이다.
- `CallHistory`는 발신자, 유형, 통화 시간(초), 시각, 상태,
  녹음 URL, AI 요약을 가진다. 녹음 URL과 AI 요약은 이후 연동용 필드다.
- `GeminiService`는 시스템 안내(따뜻하고 짧은 1~2문장)와 사용자 문장을
  합쳐 `gemini-2.5-flash`에 보낸다.
- `ChatController`는 `/chat.send`, `/chat.hello`를 `/topic/messages`로
  중계한다.
- `FCMService`는 firebase-admin으로 테스트 알림을 보낸다.

### 3.3 데이터

- JPA `ddl-auto: update`. 스키마는 엔티티 기준으로 갱신된다.
- 연결코드와 로그인 여부는 DB 테이블이 아니다.

## 4. 데이터 흐름

1. 어르신이 가입하면 앱이 6자리 코드를 만들어 기기에 저장한다.
2. 보호자가 그 코드를 입력하면 앱이 로컬에 저장된 코드와 비교한다.
3. 어르신이 AI 친구에게 말하면 앱이 `POST /api/conversation`을 호출한다.
4. 서버가 Gemini 응답 텍스트를 `{ "response": "..." }`로 돌려준다.
5. 통화가 끝나면 앱이 `POST /api/call-history`로 기록을 남기고,
   목록 화면이 `GET /api/call-history/recent` 등을 조회한다.
6. 보호자 홈의 주간 감정 차트는 아직 이 통화 API 결과가 아니다.

## 5. 설계상 특징

- 어르신과 보호자 네비게이션을 분리해 글자 크기와 정보 밀도를 다르게 둔다.
- AI 호출을 `GeminiService` 한곳으로 모아 모델 교체 범위를 줄인다.
- 어르신 마스터 데이터는 서버, 세션은 로컬로 나눠 프로토타입 속도를 우선한다.

## 6. 확장 시 바뀔 지점

- 인증을 서버로 옮기면 `AuthService`와 어르신 조회 API가 함께 바뀐다.
- 감정 점수를 서버에 두면 보호자 차트는 `CallHistory` 또는 별도 요약을 읽는다.
- FCM 토큰 테이블이 생기면 `/api/fcm/register`가 그 테이블에 저장한다.
