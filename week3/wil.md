# 주제

1. ERD를 이용한 DB설계
2. JPA 개념
3. JPA를 활용한 DB 구현하기

# 이론

- JPA : DB와 자바를 매핑하는 도구로, JPA를 사용해서 엔티티 클래스로 DB를 정의할 수 있다.

### DB 설계

문제 상황은 크게 **개체(엔티티)**와 그 사이의 **관계**로 나타낼 수 있다.

- 개체(E): 문제 상황을 구성하는 요소
- 관계(R): 개체와 개체 사이의 관계
- **ER Model** : 문제 상황을 개체와 관계로 표현하는 방법

이때 ER Model을 다이어그램으로 표현한 것을 ER Diagram, 즉 **ERD**라고 한다.

개체와 관계는 세부적인 특징인 속성(Attribute)을 가질 수 있다.

- ex> ‘사람’ **개체**는 ‘이름’, ‘나이’ 와 같은 속성을 갖는다.
- ex> ‘친구’ **관계**는 시작된 ’연도’ 와 같은 속성을 갖는다.

이때 하나의 개체를 식별할 수 있는 속성을 **PK(Primary Key)**라고 한다.

### 기본키(PK)와 외래키(FK)

1. 기본키(Primary Key)

기본키는 테이블에서 각 행(레코드)을 고유하게 식별할 수 있는 하나 이상의 열(컬럼)을 의미한다.

기본키는

**1. 중복 불가,**

**2. NULL 불가,**

**3. 한 테이블에는 하나의 기본키만 존재**한다

는 특징이 있고, 기본키는 단일 열 또는 여러 열의 조합(복합키)일 수 있다.

1. 외래키 (Foreign Key)

외래키는 다른 테이블의 기본키를 참조하는 열로, 두 테이블 사이의 관계를 연결해준다.

외래키는

**1. 외래키 열의 값은 참조하는 테이블의 기본키에 존재하는 값이어야 함,**

**2. 데이터 무결성 보장,**

**3. 중복 가능, 4. NULL 허용**

이라는 특징이 있다.

### ERD 예시

개체는 사각형, 관계는 마름모로 그린다.


ER Model은 다음과 같이 DB로 구현할 수 있다.

- 개체 → 테이블로 구현
- 관계 → 테이블 / 외래키로 구현
- 속성 → 테이블의 칼럼

### ERD로 DB를 설계해보자.

[ERDCloud](https://www.erdcloud.com/) 로 ERD를 그릴 수 있다.

### 관계

- 1:N : 회원은 여러 동아리에 소속될 수 있다.
- 1:M : 동아리는 여러 회원을 가질 수 있다.
- N:M : 회원-동아리의 관계이다.
    - 이 경우는 테이블로 구현한다.
    - (회원 PK - 동아리 PK) 쌍을 저장하는 테이블을 만들면 된다.

관계는 꼭 서로 다른 엔티티끼리 맺지 않아도 된다.

예를 들면, 유저-유저 사이의 같은 엔티티끼리 ‘친구’ 관계를 맺는 것이 가능하다.

### 엔티티

엔티티는 자바와 데이터베이스가 소통하는 단위로,

테이블의 데이터 하나(레코드)는 **엔티티 객체 하나**로 매핑된다.

## JPA

Java Persistence API

데이터베이스에서 읽어온 데이터를 자바 객체로 매핑하는 자바의 표준 기술이다. (ORM)


엔티티 클래스를 정의하면, JPA가 엔티티 클래스 정의를 보고 테이블을 생성하는 SQL과 CRUD SQL을 알아서 작성하고 실행한다.

따라서 JPA를 사용하면 SQL을 작성하는 시간을 줄일 수 있다.

→ JPA에게 어떤 데이터 A 조회를 요청하면 JPA가 SQL을 작성해 DB를 탐색한다. 거기에서 찾은 데이터 A를 객체로 만들어 응답한다.

# 실습

## 1. DB 연결하기

지금 실습에서는 간단하게 사용할 수 있는 H2 데이터베이스를 사용할 것이다.

이 H2 데이터베이스를 프로젝트에 연결하기 위해 build.gradle에 **JPA**와 **H2 데이터베이스** 의존성을 추가해야한다. (1주차에 이미 설정함)

```java
dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	compileOnly 'org.projectlombok:lombok'
	runtimeOnly 'com.h2database:h2'
	annotationProcessor 'org.projectlombok:lombok'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
	testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
}
```

build.gradle을 수정했으면 꼭 새로고침이 필요하다.

### Application.yml

DB와 어플리케이션이 소통을 하려면 어플리케이션이 DB에 접속해야하는데, 어떤 경로로 접속해야하는지에 대한 접속 정보를 적어주어야한다.

`src> main> resources >application.properties` 파일에 DB 접속 정보를 작성하면 된다. 이때 편의를 위해 파일을 yml 확장자로 바꾼다.

이 Application.yml 파일은 설정 파일이다.

```yaml
spring:
  application:
    name: todo-api

  datasource:
    url: jdbc:h2:mem:todo;MODE=MYSQL

  h2:
    console:
      enabled: true

  jpa:
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        dialect: org.hibernate.dialect.MySQL8Dialect
```

## 2. ERD 만들기

[ERDCloud](https://www.erdcloud.com/) 에 접속해서 ERD를 만든다.


User 한 명당 여러 개의 Todo를 가질 수 있으므로 **일대다 관계**이다.

그리고 User는 Todo를 한 개도 갖지 않을 수도 있으므로 **Zero or One or Many** 관계 선을 선택해서 잇는다.

일대다 관계는 보통 **외래키**로 구현한다.

- 식별 관계 : 관계 대상의 PK를 자신의 PK로도 사용하는 것
- 비-식별 관계 : 관계 대상의 PK를 자신의 FK로만 사용하는 것

## 3. DB 접속하기

TodoApiApplication 을 실행하고,

[localhost:8080/h2-console](http://localhost:8080/h2-console) 에 접속해서 `application.yml` 이라는 설정파일에 작성했던 **jdbc url**을 복사 붙여넣기 한다.


이제 connect를 누르면, 그 데이터베이스에 연결된다.


지금은 아직 아무런 테이블도 없다.

## 4. JPA로 테이블을 만들어보자 - 엔티티 클래스

com.example.todo-api 패키지 밑에 todo 라는 새로운 패키지를 만들고, Todo 라는 엔티티 클래스를 만든다.


Todo.java 엔티티 클래스에 필드 값을 모두 작성한다.

엔티티 클래스에는 `@Entity`와 `@Id` 어노테이션이 필요하다.

그리고 데이터가 추가될 때마다 자동으로 id 값이 자동 증가하도록 하기 위해 `@GeneratedValue` 를 사용한다.(mySQL에서 타입은 IDENTITY로 설정)

```java
package com.example.todo_api.todo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id 값 자동으로 1씩 증가 // IDENTITY: 키 값 결정을 DB에 위임함
    private Long id; // Long은 MySQL에서 bigint와 매칭됨

    private String content;

    private boolean isChecked;

}

```

그 후에 TodoApiApplication 어플리케이션을 실행하면 밑의 사진처럼 콘솔에 SQL이 찍힌다.


다시 [localhost:8080/h2-console](http://localhost:8080/h2-console) 로 가서 데이터베이스에 테이블이 추가되었는지 확인해보자.

(새로고침시 다시 나가져서 다시 connect 해야한다.)


새로운 TODO 테이블이 만들어졌고, SELECT 문으로 실행을 해보면 아직 아무런 데이터가 없기 때문에 비어있는 테이블이 출력된다.

그런데 ERD를 만들 때 todo_id와 같이 어떤 id인지 명시를 했었는데 객체에서는 그냥 id, content로 나온다.

객체에서 todoId와 같이 쓰는 것도 가능은 하지만, 나중에 todo.todoId로 데이터를 가져와야해서 번거롭고 불편하다.

`@Column(name = "todo_id")` 을 통해 객체에서는 id로 쓰고 데이터베이스에서는 todo_id로 자동으로 되게 만들어 줄 수 있다.

매개변수에 `columnDefinition = "varchar(200)"` 를 추가해서 타입을 정확히 설정해줄 수도 있다.

```java
@Column(name = "todo_content", columnDefinition = "varchar(200)")
private String content;
```

다시 어플리케이션을 실행해보고 콘솔을 확인해보면,


설정한대로 데이터베이스에는 더 정확하게 column 이름이 `todo_id` 등으로 설정되었다.

H2 database에서도 확인해보면,


칼럼 이름이 잘 설정되었다.

### 외래키 설정은 어떻게 해야할까 ?

외래키 구현을 위한 개념 알고가기 !


**외래키 필드에 필요한 2가지 어노테이션**



- `@JoinColumn` : 외래키 컬럼 정보를 명시
- `@ManyToOne` / `@OneToOne` 등 : 해당 외래키로 생기는 연관 관계 종류를 나타냄

**엔티티의 연관관계**



- `@**ManytoOne**` : N:1 관계 (대부분)
- `@OnetoOne` : 1:1 관계
- `@ManytoMany` : N:M관계를 나타내며, 이는 외래키 대신 테이블로 구현하므로 사용하지 않는다.
- `@OnetoMany` : 1:N 관계를 나타내며, 1에 해당하는 엔티티에 M에 대한 연관 관계를 명시하는 양방향 매핑에 사용된다.


**연관관계의 종류 : fetch**

이 속성으로 연결된 엔티티를 언제 가져올 지 명시할 수 있다.

fetch type에는 EAGER, LAZY 2가지가 있다.

- EAGER : 즉시 로딩
- LAZY : 지연 로딩


**외래키 컬럼**을 나타낼 때는 Long 타입의 외래키 필드 대신, 해당 엔티티 타입의 **엔티티 객체를 필드로** 가지도록 설계한다.

member 패키지를 새롭게 만들고 Member 클래스도 만들어준다. (user 로 설정하면 기존 데이터베이스 테이블과 헷갈릴 수 있어서 member로 설정함)

Todo 클래스에서 했던 것과 같이 Member 클래스에 필드들을 작성하고, 다시 어플리케이션을 실행하면,


todo 테이블 뿐만 아니라, member 테이블 SQL도 같이 작성된다.

H2 database도 다시 새로고침해보면, member 테이블도 존재할 것이다.

이제 Todo 클래스에 `member_id`를 외래키로 설정해주자.

해당 엔티티 타입의 **엔티티 객체를 필드로** 가지도록 해야한다.

```java
@JoinColumn(name = "member_id")
@ManyToOne(fetch = FetchType.LAZY) // 1:다 관계
private Member member;
```

외래키는 연관관계이므로 `@JoinColumn`을 추가한다.

그리고 어떤 관계에 있는지도 설정해준다.

여기서 LAZY 타입으로 fetch해서 지연 로딩을 했다. Todo 객체 정보를 가져올 때 연결된 User 객체의 정보는 **필요할 때** 가져온다는 것을 의미한다.

이렇게 외래키를 엔티티로 저장하면,

JPA가 자동으로 **외래키**로 만들어주고, 연관된 데이터가 필요할 때 **자동으로 join 쿼리**가 실행되면서 연관된 데이터를 얻는다.

## 5. 엔티티 생성자 만들기

엔티티 클래스를 가지고 새로운 객체를 만드려면 엔티티 생성자가 필요하다.

윈도우에서는 alt + insert 을 통해 자동으로 생성자를 만들 수 있다.


id값은 자동으로 만들어지니까 나머지 값들을 모두 선택한다.

```java
public Todo(String content, boolean isChecked, Member member) {
        this.content = content;
        this.isChecked = isChecked;
        this.member = member;
    }
```

확인을 누르면 자동으로 생성자 코드가 생성된다.

하지만 JPA가 동작하려면 아무런 인자가 없는 생성자도 있어야 하는데, 이를 위해서 lombok에서 제공하는
`@NoArgsConstructor(access = AccessLevel.*PROTECTED*)`

어노테이션을 추가해서 인자가 없는 생성자를 만든다.

그리고 여기서 Todo는 모두가 볼 필요는 없으므로 접근제한자를 PROTECTED로 만들어주었다.

## 6. 추가 ++

또 한가지!

모든 필드가 `private`이므로 외부에서 참조할 때 필드가 무엇인지 알기 위해 lombok의 `@Getter` 을 추가해준다.

# 정리

- ERD를 그려서 DB를 설계할 수 있다.
- JPA는 DB와 자바 객체를 매핑하는 도구이며, 이때 DB와 매핑되는 자바 객체를 엔티티라고 한다.
- JPA를 사용해서 엔티티 클래스로  DB를 정의할 수 있다.
- 엔티티 클래스에는 `@Entity`와 `@Id` 가 필요하다.
- 일반 컬럼에는 `@Column`으로 컬럼 명과 컬럼 타입을 지정할 수 있다.