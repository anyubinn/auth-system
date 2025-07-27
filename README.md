# 🛡️ JWT 인증 시스템 - Spring Boot 프로젝트

사용자 인증, JWT 기반 로그인, 역할 기반 권한 제어를 구현한 인메모리 Spring Boot 애플리케이션입니다.  
회원가입, 로그인, 관리자 권한 부여 기능을 포함하며 모든 API는 `application/json` 형식으로 응답합니다.

---

## 🛠️ 기술 스택

- Java 17
- Spring Boot 3.5.4
- Spring Security
- JWT
- Swagger
- Gradle
- AWS EC2 (배포 환경)

---

## 🚀 실행 방법

### 실행 방법 (로컬 실행 기준)

```bash
# 의존성 설치 및 빌드
./gradlew clean build

# 프로젝트 실행
./gradlew bootRun

# 또는 jar 실행
java -jar build/libs/auth-system-0.0.1-SNAPSHOT.jar
```

🔗 배포 정보

Swagger UI: http://13.48.46.183:8080/swagger

API Endpoint (EC2): http://13.48.46.183:8080