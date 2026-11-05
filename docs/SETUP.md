# 늘봄 — Setup, Deploy & Testing 가이드

개발 환경 준비, 실행, 배포, 확인 절차이다.

---

## 1. 사전 요구사항

- JDK 21
- PostgreSQL (로컬 `localhost:5432`)
- Flutter SDK (프로젝트 제약: Dart `^3.10.1`)
- Android 에뮬레이터 또는 기기
- Google AI Studio Gemini API 키
- (푸시 테스트 시) Firebase 서비스 계정 `firebase-key.json`

확인:

```bash
java -version
flutter --version
```

---

## 2. 저장소

```bash
git clone https://github.com/baejooho/elder-care-app-2026.git
cd elder-care-app-2026
```

---

## 3. 데이터베이스

PostgreSQL에 데이터베이스 `eldercare`를 만든다.
사용자명은 설정 파일 기준 `postgres`이다.

```sql
CREATE DATABASE eldercare;
```

비밀번호는 환경변수로 넘긴다. 앱 실행 시 Hibernate가
`ddl-auto: update`로 `elders`, `call_history` 테이블을 맞춘다.

---

## 4. 환경변수

| 변수명 | 설명 |
|--------|------|
| `DB_PASSWORD` | PostgreSQL 비밀번호 |
| `GEMINI_API_KEY` | Gemini API 키 |

PowerShell 예:

```powershell
$env:DB_PASSWORD = "비밀번호"
$env:GEMINI_API_KEY = "발급받은 키"
```

`firebase-key.json`은 백엔드가 Firebase Admin을 초기화할 때 사용한다.
이 파일과 `.env`는 커밋하지 않는다.

---

## 5. 백엔드 실행

```bash
cd backend
./gradlew bootRun
```

Windows에서는 `gradlew.bat bootRun`을 사용한다.
기본 포트는 8080이다.

주요 API:

| 메서드 | 경로 | 설명 |
|--------|------|------|
| GET | `/api/elders` | 어르신 목록 |
| POST | `/api/elders` | 어르신 등록 |
| GET | `/api/elders/search?name=` | 이름 검색 |
| GET | `/api/elders/attention-needed` | 주의 필요 어르신 |
| GET | `/api/call-history/recent` | 최근 통화 |
| POST | `/api/call-history` | 통화 저장 |
| POST | `/api/conversation` | `{ "message": "..." }` → `{ "response": "..." }` |
| POST | `/api/fcm/register` | 토큰 수신 (DB 저장 없음) |
| POST | `/api/fcm/test` | 테스트 푸시 |

---

## 6. Flutter 앱 실행

```bash
cd flutter_app
flutter pub get
flutter run
```

`ApiService.baseUrl`은 `http://10.0.2.2:8080`이다.
Android 에뮬레이터에서 호스트 PC의 8080으로 연결되는 주소이다.
실기기에서는 PC의 LAN 주소로 바꿔야 한다.

---

## 7. 배포

현재 배포 단위는 GitHub `main` 브랜치
(`baejooho/elder-care-app-2026`)이다.

1. 비밀 파일(`firebase-key.json`, `.env`)이 스테이징에 없는지 확인한다.
2. 문서와 소스만 커밋한다.
3. `git push origin main`

별도 서버 호스팅 절차는 아직 없다. 호스팅 시에도
`DB_PASSWORD`, `GEMINI_API_KEY`와 Firebase 키는 환경에만 둔다.

---

## 8. 테스트

백엔드 `src/test`와 앱 위젯 테스트 시나리오는 아직 없다.
아래는 수동으로 확인하는 흐름이다.

| 항목 | 방법 | 기대 결과 |
|------|------|-----------|
| 역할 선택 | 앱 최초 실행 | 어르신/보호자 선택 화면 |
| 어르신 가입 | 이름, 전화번호 입력 | 6자리 연결코드 표시 |
| 연결코드 재확인 | 어르신 홈 더보기 → 내 연결코드 | 가입 때와 같은 코드 |
| 보호자 연결 | 보호자 화면에서 그 코드 입력 | 같은 기기에 코드가 있으면 연결 |
| 잘못된 코드 | 6자리가 아닌 값, 또는 없는 코드 | 안내 문구 |
| AI 대화 | 백엔드 실행 후 홈에서 대화 | Gemini 짧은 응답 |
| 통화 기록 API | `GET /api/call-history/recent` | JSON 목록 또는 빈 배열 |

자동화 테스트는 WBS의 W10이다.
