![header](https://capsule-render.vercel.app/api?type=waving&color=auto&height=200&section=header&text=구름%20하늘&fontSize=70)

## ☁️ 구름하늘 팀
👩‍💻 Spring_6기 A반 6조 개발자 TMI 공유 블로그 👩‍💻

## 🔧 개발 환경
- `Java 8`
- `JDK 17.0.7`
- Database: MySQL Server 8.0
- ORM: JPA

## 🔧 사용한 Tool
<img src="https://img.shields.io/badge/git-F05032?style=flat&logo=git&logoColor=white">
<img src="https://img.shields.io/badge/github-181717?style=flat&logo=github&logoColor=white">
<img src ="https://img.shields.io/badge/Java-007396?&style=flat&logo=Java&logoColor=white">
<img src="https://img.shields.io/badge/intellijidea-000000?style=flat&logo=intellijidea&logoColor=white">

## 📍 프로그램 기능
1) 사용자 인증 기능
  - 회원가입 기능
  - 로그인 및 로그아웃 기능
     - JWT 활용
     - 사용자는 자신의 계정으로 서비스에 로그인하고 로그아웃 가능
2) 계정 관리
  - 프로필 수정 기능
     - 이름, 한줄 소개와 같은 기본적인 정보 수정 가능
3) 게시물 CRUD 기능
  - 게시물 작성, 조회, 수정, 삭제 기능
     - 게시물 조회를 제외한 나머지 기능들은 전부 인가(Authorization) 개념이 적용되어야 하며, 이는 JWT와 같은 토큰으로 검증 가능
     - 오로지 본인만 게시글 수정, 삭제 가능
4) 뉴스 피드 기능
  - 뉴스 피드 페이지 (전체 조회 페이지)
     - 사용자가 다른 사용자의 게시물을 한 눈에 볼 수 있는 뉴스 피드 페이지

### 📍 추가 구현
- 댓글 CRUD 기능
   - 댓글 작성, 조회, 수정, 삭제 기능
      - 사용자는 게시물에 댓글을 작성할 수 있고, 본인의 댓글은 수정 및 삭제가 가능해야 함
      - 또한, 게시물과 마찬가지로 댓글 조회를 제외한 나머지 기능들은 전부 인가(Authorization) 개념이 적용되어야 함
   - 댓글 작성, 수정, 삭제 시 새로고침 기능
      - 프론트엔드에서 댓글 작성, 수정 및 삭제를 할 때마다 조회 API를 다시 호출해 자연스럽게 최신 댓글 목록을 화면에 보여줄 수 있도록 하기
- 좋아요 기능
   - 게시물 및 댓글 좋아요 / 좋아요 취소 기능
      - 사용자가 게시물이나 댓글에 좋아요를 남기거나 취소할 수 있어야 함
      - 본인이 작성한 게시물에 좋아요는 남길 수 없도록 하기
- 프론트엔드 만들기
   - 백엔드에서 제공하는 API를 통해 서버와 통신하는 프론트엔드 구현하기

## 📆 프로젝트 진행 일정
- 첫째 날 : 프로젝트 내용 정리, 역할 분담
- 둘째 날 : 개인 역할 수행
- 셋째 날 : 개인 담당 코드 취합 후 테스트, 추가기능 구현 역할 분담
- 넷째 날 : 추가기능 취합 코드 테스트, 발표 역할 분담
- 다섯째 날: 코드 마무리, 제출 자료 정리, 발표 자료 제출, 발표

## 👩‍💻 팀원 역할
- 고은아: 뉴스피드 기능
- 신성민: 사용자 로그인 회원가입 로그아웃 기능, 좋아요 기능
- 유수인: 사용자 정보 수정 및 비밀번호 수정 기능
- 이수연: 게시물 CRUD 기능, 댓글 CRUD 기능, 댓글 좋아요 기능, 프론트 엔드

## 📜 API 명세서
상세 링크: https://documenter.getpostman.com/view/27928373/2s93zB5246

API (@RestController)

|기능|Method|URL|
|:---:|:---:|:---|
|회원가입|POST|/dev/user/signup|
|로그인|POST|/dev/user/login|
|로그아웃|-|/dev/user/logout|
|게시글 작성|POST|/dev/post|
|게시글 수정|PUT|/dev/post/{postid}|
|게시글 삭제|DELETE|/dev/post/{postid}|
|게시글 좋아요 등록|POST|/dev/like/{postid}|
|게시글 좋아요 취소|DELETE|/dev/like/{postid}|
|댓글 작성|POST|/dev/post/{postid}/comment|
|댓글 수정|PUT|/dev/post/{postid}/comment/{commentid}|
|댓글 삭제|DELETE|/dev/post/{postid}/comment/{commentid}|
|댓글 좋아요 등록|POST|/dev/like/{postid}/{commentid}|
|댓글 좋아요 취소|DELETE|/dev/like/{postid}/{commentid}|
|회원 정보 수정|PUT|/dev/profile|
|비밀번호 확인|POST|/dev/profile/password|
|비밀번호 수정|PUT|/dev/prifile/passwordupdate|

페이지 반환 API (@Controller)

|페이지명|Method|URL|
|:---:|:---:|:---|
|메인 페이지|GET|/|
|로그인 페이지|GET|/dev/user/login-page|
|회원가입 페이지|GET|/dev/user/signup|
|선택 게시글 페이지|GET|/dev/post/{postid}|
|게시글 수정&작성 페이지|GET|/dev/post?id=postid(required = false)|
|회원 페이지|GET|/dev/mypage|
|회원 정보 수정 페이지|GET|/dev/profile|
|비밀번호 확인 페이지|GET|/dev/profile/password|
|비밀번호 수정 페이지|GET|/dev/profile/passwordupdate|

## 📃 ERD
1) 테이블
   
- users (User.java)
  
|컬럼명|데이터타입|기능|
|:---:|:---:|:---|
|user_id (PK)|BIGINT|사용자ID|
|username|VARCHAR|사용자 이름(ID)|
|realname|VARCHAR|사용자 닉네임|
|password|VARCHAR|사용자 비밀번호|
|role|enum(‘admin’, ‘user’)|사용자 역할|
|introduction|VARCHAR|사용자 한줄소개|

- post (Post.java)
  
|컬럼명|데이터타입|기능|
|:---:|:---:|:---|
|post_id (PK)|BIGINT|게시글ID|
|user_id (FK)|BIGINT|사용자ID|
|title|VARCHAR|게시글 제목|
|content|VARCHAR|게시글 내용|
|like_count|INT|게시글 좋아요 수|
|created_at|DATETIME|게시글 작성일|
|modified_at|DATETIME|게시글 수정일|

- comment (Comment.java)
  
|컬럼명|데이터타입|기능|
|:---:|:---:|:---|
|id (PK)|BIGINT|댓글ID|
|username (FK)|BIGINT|사용자ID|
|post_id (FK)|BIGINT|게시글ID|
|commentcontents|VARCHAR|댓글 내용|
|like_count|INT|댓글 좋아요 수|

- likes (Like.java)
  
|컬럼명|데이터타입|기능|
|:---:|:---:|:---|
|like_id (PK)|BIGINT|게시글좋아요ID|
|user_id (FK)|BIGINT|사용자ID|
|post_id (FK)|BIGINT|게시글ID|

- commentlikes (Commentlike.java)
  
|컬럼명|데이터타입|기능|
|:---:|:---:|:---|
|commentlike_id (PK)|BIGINT|댓글좋아요ID|
|user_id (FK)|BIGINT|사용자ID|
|post_id (FK)|BIGINT|게시글ID|
|comment_id (FK)|BIGINT|댓글ID|

2) 테이블 관계
<img src="https://github.com/azuressu/Blog_CloudSky/assets/74248726/db6945e2-d9f4-41bf-89b0-616a8e564291"  width="800"/>


## 💻 와이어프레임
<img src="https://github.com/azuressu/Blog_CloudSky/assets/74248726/b9e8420b-f0c6-4a01-9b63-1074665d24b3"  width="800"/>
<img src="https://github.com/azuressu/Blog_CloudSky/assets/74248726/bfa6408e-55fc-473d-a381-18064ad49499"  width="800"/>
<img src="https://github.com/azuressu/Blog_CloudSky/assets/74248726/7729ed63-f031-4c87-8c1f-8a2059d614e4"   width="800"/>
<img src="https://github.com/azuressu/Blog_CloudSky/assets/74248726/5cfeb72a-6d30-4334-9ce8-08ca3cdb03d9"   width="800"/>

## 🗂️ 파일 구성
1) config 패키지 (@Configuration)

|파일명|설명|
|:---:|:---|
|PasswordConfig.java|비밀번호 인코더|
|WebSecurityConfig.java|스프링 웹 세큐리티 사용|

2) controller 패키지 (@RestController / @Controller)
   
|파일명|설명|
|:---:|:---|
|CommentController.java|댓글 컨트롤러|
|LikeController.java|좋아요 컨트롤러|
|PostController.java|게시글 컨트롤러|
|PostViewController.java|게시글 뷰 컨트롤러|
|ProfileController.java|사용자 정보 컨트롤러|
|ProfileViewController.java|사용자 정보 뷰 컨트롤러|
|UserController.java|사용자 컨트롤러|

3) dto 패키지
   
|파일명|설명|
|:---:|:---|
|ApiResponseDto.java|사용자에게 응답 반환|
|CommentRequestDto.java|댓글 반환|
|CommentResponseDto.java|댓글 요청|
|LoginRequestDto.java|로그인 요청|
|PasswordRequestDto.java|비밀번호 요청|
|PostRequestDto.java|게시글 요청|
|PostResponseDto.java|게시글 반환|
|ProfilePostListResponseDto.java|사용자의 게시글 반환|
|ProfileRequestDto.java|사용자의 정보 요청|
|ProfileResponseDto.java|사용자의 정보 반환|
|SignupRequestDto.java|회원가입 요청|

4) entity 패키지 (@Entity)
   
|파일명|설명|
|:---:|:---|
|Comment.java|댓글|
|Commentlike.java|댓글 좋아요|
|Like.java|게시글 좋아요|
|Post.java|게시글|
|Timestamped.java|작성일 수정일|
|User.java|사용자|
|UserRoleEnum.java|사용자 역할|

5) jwt 패키지
   
|파일명|설명|
|:---:|:---|
|JwtAuthenticationFilter.java|JWT 기반 인증|
|JwtAuthorizationFilter.java|JWT 기반 인가|
|JwtUtil.java|JWT 생성 및 검증|

6) repository 패키지

|파일명|설명|
|:---:|:---|
|CommentRepository.java|댓글 레포지토리|
|CommentlikeRepository.java|댓글 좋아요 레포지토리|
|LikeRepository.java|게시글 좋아요 레포지토리|
|PostRepository.java|게시글 레포지토리|
|UserRepository.java|사용자 레포지토리|

7) security 패키지
   
|파일명|설명|
|:---:|:---|
|UserDetailsImpl.java|UserDetails 구현체|
|UserDetailsServiceImpl.java|UserDetailsService 구현체|

8) service 패키지 (@Service)
   
|파일명|설명|
|:---:|:---|
|CommentService.java|댓글 서비스|
|LikeService.java|좋아요 서비스|
|PostService.java|게시글 서비스|
|UserService.java|사용자 서비스|

![Footer](https://capsule-render.vercel.app/api?type=waving&color=auto&height=200&section=footer)
