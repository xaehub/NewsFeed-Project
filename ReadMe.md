# 📰 NewsFeed 프로젝트

Java 기반으로 구현한 SNS 스타일의 피드 서비스입니다.  
사용자는 게시글을 작성하고, 댓글을 남기며, 좋아요 및 팔로우 기능을 통해 다른 사용자와 상호작용할 수 있습니다.

---

## 📁 프로젝트 구조

```
NewsFeed
└── src
    └── main
        ├── java
        │   └── com
        │       └── sparta
        │           └── newsfeed
        │               ├── controller       # API 컨트롤러
        │               ├── dto              # 요청/응답 DTO
        │               ├── entity           # JPA 엔티티
        │               ├── repository       # JPA 리포지토리
        │               ├── security         # 보안 설정 및 예외 처리
        │               └── service          # 비즈니스 로직
        └── resources
            ├── static
            ├── templates
            └── application.properties
```


## ⚙️ 기술 스택

- Java 17+
- Spring Boot
- Spring Data JPA
- MySQL
- Lombok
- Spring Security
- Gradle

---

## ✨ 주요 기능

| 기능 | 설명 |
|------|------|
| 회원가입 / 로그인 | 사용자 인증, JWT 또는 세션 (추가 구현 여부 따라 다름) |
| 게시글 CRUD | 게시글 작성, 수정, 삭제, 조회 |
| 댓글 기능 | 게시글에 댓글 작성/수정/삭제 |
| 좋아요 기능 | 게시글/댓글에 좋아요 |
| 팔로우 기능 | 사용자 간 팔로우/언팔로우 |
| 프로필 관리 | 사용자 정보 수정, 프로필 보기 |

---

## 🛠️ 실행 방법

```bash
# 1. 프로젝트 클론
git clone https://github.com/yourusername/newsfeed.git

# 2. DB 설정 (application.properties 수정)
spring.datasource.url=jdbc:mysql://localhost:3306/newsfeed
spring.datasource.username=your_db_user
spring.datasource.password=your_db_password

# 3. 실행
./gradlew bootRun
```
💡 username과 password는 로컬 환경에 맞게 설정하세요.
## 📌 패키지 설명
controller: REST API 엔드포인트 정의

dto: 계층 간 데이터 전달 객체 (Request/Response)

entity: DB 테이블과 매핑되는 도메인 클래스

repository: DB 접근 레이어

service: 핵심 비즈니스 로직 담당

security: 로그인 필터, 전역 예외 처리, 보안 설정 등

## 📞 문의
### TEAM 2조 욱호와 I들을 찾아주세요.