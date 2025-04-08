# 웹과 HTTP

## 웹이란?

1. 웹

   : 컴퓨터가 연결된 빠른 네트워크. 인터넷.

    - `클라이언트-서버` 패러다임을 갖는다.
    - 클라이언트: 데이터의 `생성/조회/수정/삭제` **요청**을 전송
    - 서버: 요청대로 동작을 수행하고 **응답**을 전송
   
2. 브라우저

   : 컴퓨터에 들어있는 웹사이트를 보기 위한 **프로그램**

    - 항상 **요청**을 보내는 개체이다.

   ex > 인터넷 익스플로러, 구글 크롬, 사파리, 파이어폭스

3. 클라이언트

   : 브라우저를 통해 웹사이트를 보기 위한 **도구, 장치, 프로그램**

   ex > 데스크톱 컴퓨터, 노트북, 스마트폰 등의 디지털 디바이스


## HTTP

웹에서는 HTTP라는 프로토콜을 사용한다.

- HTML 문서와 같은 리소스들을 가져올 수 있게 해주는 애플리케이션 계층의 **프로토콜(**데이터 전송 규칙**)**
- **클라이언트-서버 프로토콜** : (웹브라우저)수신자 측에 의해 요청이 초기화되는 프로토콜
    - 요청(request) : 클라이언트에 의해 전송되는 메시지
    - 응답(response) : 서버에서 응답으로 전송되는 메시지
- **확장 가능**하다: 클라이언트와 서버가 새로운 헤더의 시맨틱에 대해 간단한 합의만 한다면, 언제든지 새로운 기능을 추가할 수 있다.
- **상태는 없지만**(stateless) **세션은 있다**.
    - 대신 **HTTP cookie**는 상태가 있는 **세션**을 만들게 해준다.
- **HTTP 메소드**
    - `GET` : 데이터 가져오기 (조회)
    - `POST` : 데이터 게시하기 (생성)
    - `PUT` : 데이터 교체하기 (수정) → 기존 데이터를 없애고 새로 만든다.
    - `PATCH` : 데이터 수정하기 (수정) → 일부분만 수정한다.
    - `DELETE` : 데이터 삭제하기 (삭제)
- **상태 코드**

  : `2`로 시작이면 성공, `4`로 시작하면 클라이언트 잘못으로 실패

    - `200` : ok (처리 성공)
    - `201` : created (데이터 생성 성공)
    - `400` : bad request (클라이언트 요청 오류)
    - `404` : not found (요청 데이터 없음)
    - `500` : internal server error (서버 에러)

### HTTP 흐름

1. TCP 연결을 연다.
2. HTTP 메시지를 전송한다.

    ```
    GET / HTTP/1.1
    Host: developer.mozilla.org
    Accept-Language: fr
    ```

3. 서버가 주는 응답을 읽어들인다.

    ```
    HTTP/1.1 200 OK
    Date: Sat, 09 Oct 2010 14:28:02 GMT
    Server: Apache
    Last-Modified: Tue, 01 Dec 2009 20:18:22 GMT
    ETag: "51142bc1-7449-479b075b2891b"
    Accept-Ranges: bytes
    Content-Length: 29769
    Content-Type: text/html
    
    <!DOCTYPE html... (here comes the 29769 bytes of the requested web page)
    ```

4. 연결을 닫거나 다른 요청들을 위해 재사용한다.

### HTTP 메시지


- HTTP 헤더 : **통신**에 대한 정보
- HTTP 바디 : 주고 받으려는 **실제 데이터** (보통 json)

### URL 구조

URL은 우편 주소와 비슷하다.

- `scheme` : 사용하려는 우편 서비스
- `domain name` : 시/마을
- `port` : 우편 번호
- `path`: 배달되어야 할 건물 → 웹 서버에 있는 리소스 경로
- `parameters`: 건물의 아파트 번호 등 상세 주소 → 웹 서버에 제공되는 추가 정보(key, value)
- `anchor` : 메일을 보낸 실제 사람  → fragment 식별자


1. path parameter

   예시> `http://www.example.com/user/{user_id}/nickname`

2. query string

   예시> `http://www.example.com/post/search?page=1&keyword=hello`


# 벡엔드와 프론트엔드


자주 변하지 않는 **화면 UI**와, 자주 변하는 **컨텐츠**를 분리해야하므로,

**프론트엔드**와 **벡엔드**가 분리되었다.

1. 프론트: 화면에 채울 데이터를 벡엔드에게 요청한다.
2. 벡엔드: **DB**에서 가져온 데이터로 프론트에게 응답한다.
3. **프론트엔드**는 **벡엔드**로부터 받아온 데이터로 화면을 구성하여 다시 **브라우저**에게 전달한다.

# REST API

## API란?

Application Programming **Interface**

응용프로그램에서 사용할 수 있도록, 운영체제나 프로그래밍 언어가 제공하는 기능을 제어할 수 있게 만든 인터페이스

ex> 항공편 예약을 위해 휴대폰에서 Expedia 앱을 가져오거나, Slack을 Google Drive에 연결하는 것

→ 애플리케이션을 **서로 연결하여 서로 통신하는 방법**을 정의한 것이다.

청소기 전원을 킬 때, 우리는 청소기 전원 버튼의 작동방식을 고려하지 않고 버튼만 누른다.

## REST API


### REST:

**리소스(url) 중심**의 설계를 통해 클라이언트와 서버가 통신하는 방식

- **RE**presentational **S**tate **T**ransfer : **리소스를 HTTP 표준에 따라 CRUD 방식으로 관리**하는 설계 원칙( `C` - create, `R` - read, `U` - update, `D` - delete)

HTTP를 잘 사용하기 위해, `URL`과 `HTTP 메소드`를 사용해서,

- `URL`로 **어떤 자원에 접근** 할 것인지,
- `메소드`로 **어떤 행위를 할 것**인지 표현하여 설계된 API

- 조건 - 클라이언트와 서버는 **종속적이지 않아야** 한다.
    - **클라이언트-서버** 커뮤니케이션 : 요청 간에 클라이언트 정보가 저장되지 않고, 각 요청이 분리되어 있고 서로 연결되어있지 않다.
    - **Stateless** (상태 없음)

  → 클라이언트가 요청할 때마다 벡엔드에게 자기 정보를 보내야 하고, 서버는 요청 받을 때마다 클라이언트의 정보를 확인해야 한다.

- 정리
    - Client의 요청을 어느 Server라도 동일하게 처리할 수 있다.
    - Client측에서는 Server측을 신경 쓸 필요 없이, API 호출만 하면 원하는 응답을 받을 수 있다.

# API 명세서 작성 과제

로그인, 좋아요와 같은 동작은 path에 `동사`를 쓰고, `POST`로 호출한다.

```
POST /login
POST /logout
POST /register
POST /like
```

`todo` API 명세서

- 할 일 전체 조회

    ```
    GET /todo/list
    ```

- 할 일 생성

    ```
    POST /todo
    ```

- (특정) 할 일 수정

    ```
    PATCH /todo/{todo_id}
    ```

- (특정) 할 일 삭제

    ```
    DELETE /todo/{todo_id}
    ```

- (특정) 할 일 체크

    ```
    POST /todo/{todo_id}/checked
    ```

- (특정) 할 일 체크 해제

    ```
    POST /todo/{todo_id}/uncheck
    ```