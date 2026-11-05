# 늘봄 (Elder Care)

독거 어르신을 위한 AI 안부 대화와, 보호자가 건강·통화 상태를 확인하는
모바일 돌봄 애플리케이션.

## 프로젝트 개요

늘봄은 어르신과 보호자가 각자 쓰는 화면을 나눈 Flutter 앱과,
Spring Boot 백엔드로 구성된다.

어르신은 이름을 붙인 AI 친구와 대화하고, 통화 기록과 비상연락을
확인한다. 가입 시 발급되는 6자리 연결코드를 보호자에게 알려 주면
보호자 앱에서 해당 어르신을 연결할 수 있다.

보호자는 연결된 어르신의 주간 통화·감정 요약과 건강 알림을 본다.

자세한 기획 배경은 [docs/PLANNING.md](docs/PLANNING.md)를 참고한다.

## 주요 기능

### 어르신

- 이름·전화번호로 가입, 6자리 연결코드 발급
- 전화번호 로그인, 역할(어르신/보호자) 선택
- AI 친구 이름 설정 후 대화 (`POST /api/conversation`, Gemini)
- 홈 / 통화기록 / 비상연락 탭
- 내 연결코드 확인, 로그아웃

### 보호자

- 이메일·비밀번호 가입 및 로그인
- 6자리 연결코드로 어르신 연결
- 주간 통화 횟수, 평균 감정, 감정 추이
- 건강 알림 목록
- 계정 메뉴와 알림 설정 화면

### 백엔드

- 어르신 CRUD, 이름 검색, 건강 상태(양호/보통/주의/위험) 조회
- 통화 기록 저장 및 최근·오늘·전체 조회
- Gemini 기반 대화 API
- STOMP WebSocket 메시지 중계
- FCM 토큰 등록 요청 및 테스트 알림

## 기술 스택

- **App**: Flutter, Dart, Provider, SharedPreferences
- **Backend**: Java 21, Spring Boot 4, Spring Data JPA, WebSocket
- **AI**: Google Gemini API (`gemini-2.5-flash`)
- **DB**: PostgreSQL
- **Push**: Firebase Cloud Messaging, firebase-admin

## 저장소 구성

| 경로 | 설명 |
|------|------|
| `flutter_app/` | 어르신·보호자 Flutter 앱 (주 클라이언트) |
| `backend/` | Spring Boot API |
| `mobile/` | Cordova Android 실험용 프로젝트 |
| `docs/` | 기획, 아키텍처, ADR, WBS, 실행 가이드 |

## 설치 및 실행 방법

백엔드는 PostgreSQL `eldercare` 데이터베이스와 환경변수
`DB_PASSWORD`, `GEMINI_API_KEY`가 필요하다.

```bash
git clone https://github.com/baejooho/elder-care-app-2026.git
cd elder-care-app-2026/backend
./gradlew bootRun
```

앱은 `flutter_app`에서 실행한다. Android 에뮬레이터는
`http://10.0.2.2:8080`으로 백엔드에 접속한다.

```bash
cd flutter_app
flutter pub get
flutter run
```

환경변수, DB, Firebase 설정은 [docs/SETUP.md](docs/SETUP.md)를 참고한다.

## 환경변수

민감 정보는 코드에 넣지 않고 환경변수로 넘긴다.
`firebase-key.json`과 `*.env`는 저장소에 포함하지 않는다.

| 변수명 | 설명 |
|--------|------|
| `DB_PASSWORD` | PostgreSQL `postgres` 계정 비밀번호 |
| `GEMINI_API_KEY` | Google AI Studio에서 발급한 Gemini API 키 |

## 문서

- [기획서 및 요구사항 정의서](docs/PLANNING.md)
- [WBS 및 작업 분해](docs/WBS.md)
- [아키텍처 설계 문서](docs/ARCHITECTURE.md)
- [ADR (아키텍처 의사결정 기록)](docs/ADR.md)
- [Setup / Deploy / Testing 가이드](docs/SETUP.md)
- [AGENTS.md (AI 에이전트 활용 정책)](AGENTS.md)

## 현재 구현 범위

아래 항목은 화면 또는 API가 있으나, 서버 영속 연동이 끝나지 않았다.

- 로그인·회원가입·연결코드는 기기 로컬(`SharedPreferences`)에 저장된다.
- FCM 토큰 등록 API는 수신 로그만 남기고 DB에 저장하지 않는다.
- 보호자 건강 알림·주간 감정 수치는 화면용 데이터다.
- 앱이 호출하는 `POST /api/tts`는 백엔드에 아직 없다.
- 백엔드 자동화 테스트는 아직 없다.

## 향후 계획

- 회원·연결코드를 서버 계정으로 이전
- 통화 요약과 감정 점수를 서버에 저장하고 보호자 화면에 연결
- FCM 토큰 저장 후 건강 경고 푸시
- TTS API와 자동화 테스트 추가

## 라이선스

본 프로젝트는 학습 목적으로 제작되었습니다.
