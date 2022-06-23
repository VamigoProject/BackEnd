# Vamigo(리뷰 공유 SNS) Backend 저장소

## 사용 기술
서버 : `spring` <br/>
Database & Search Engine : `mariaDB` , `Elastic Search`

## 구동 방법
1. Repository의 파일 다운
2. `/src/main/resources`디렉토리에 `application.properties`파일 생성 후 아래와 같이 작성
``` Javascript
  #DB Connection : MairaDB
  spring.datasource.url = jdbc:mariadb:{MariaDB서버 주소/DB이름} ex)jdbc:mariadb:localhost:3306/vamigo
  spring.datasource.username= {MariaDB 계정명} ex)root
  spring.datasource.password= {MariaDB 패스워드} ex)1q2w3e4r
  spring.datasource.driver-class-name= org.mariadb.jdbc.Driver

  spring.jpa.hibernate.ddl-auto=update
  spring.jpa.generate-ddl=false
  spring.jpa.show-sql=true
  server.port={spring서버 포트 설정} ex)8080

  spring.jpa.properties.hibernate.validator.apply_to_ddl = false

  # email-properties
  spring.mail.host=smtp.gmail.com
  spring.mail.port=587
  spring.mail.username={SMTP가능한 gmail} ex)user@gmail.com
  spring.mail.password={gmail 패스워드} ex)1q2w3e4r
  spring.mail.properties.mail.smtp.starttls.enable=true
  spring.mail.properties.mail.smtp.starttls.required=true
  spring.mail.properties.mail.smtp.auth=true
  spring.mail.properties.mail.smtp.ssl.enable =true
  spring.mail.properties.mail.smtp.ssl.trust=smtp.gmail.com
  org.springframework.mail.MailAuthenticationException.Authentication =  failed;
  spring.mail.properties[mail.smtp.connectiontimeout]=10000
  spring.mail.properties[mail.smtp.timeout]=10000
  spring.mail.properties[mail.smtp.writetimeout]=10000


  #JWT Options
  vamigopj.app.jwtSecret = {JWT 시크릿 키 입력}
  vamigopj.app.jwtExpirationMs = 3600000
  vamigopj.app.jwtRefreshExpirationsMs = 86400000

  #JPA



  #elasticsearch
  elasticsearch.url={elasticsearch 주소:포트 입력} ex)localhost:9200


  #????
  spring.jackson.serialization.fail_on_empty_beans=false
```


## 구현된 기능
### 로그인 및 회원가입
 - 메일인증을 통한 중복체크
 - 가입한 메일과 패스워드로 로그인
 - 가입한 메일을 통해 비밀번호 찾기
### 리뷰 작성, 수정, 삭제
 - 리뷰를 작성한 작품을 선택
 - 코멘트와 별점 설정
 - 스포일러 존재여부에 따라 스포일러 여부 체크
 - 기존에 작성된 리뷰에 대하여 코멘트, 별점, 스포일러 여부 수정
 - 본인이 작성한 리뷰의 경우 삭제 가능
### 프로필 관리
 - 본인 및 다른 사용자들에게 보여질 프로필 이미지 수정
 - 자기소개 및 닉네임 수정
 - 본인과 관계를 맺고 있는 사용자 보기
 - 본인이 작성한 리뷰 보기
 - 본인이 좋아요를 누른 리뷰 보기
 - 작성한 리뷰에 대한 통계 보기
### 관계 맺기
 - 사용자 닉네임을 기반으로 검색
 - 사용자에 대해 팔로우 및 언팔로우
 - 본인의 프로필과 동일하게 대상의 프로필 열람 가능
### 리뷰에 대한 반응
 - 각각의 리뷰에 대해 댓글 작성 및 삭제
 - 좋아요를 통해 해당 리뷰에 대해 반응
 - 부적절한 리뷰에 대한 항목별 신고
### 트렌드 보기
 - 주간별 작품별 작성된 리뷰의 개수를 집계하여 순위 산출
 - 작품별 리뷰 보기 기능 제공(트렌드가 아닌 작품들은 검색을 통해 가능)
### 통계 보기
 - 사용자가 작성한 리뷰를 종류별로 집계하여 시각화
 - 다른 사용자와의 비교를 위한 통계 제공
