# Sparta-Coding-Club
## 개발환경
1. 인텔리제이
2. arc
3. java1.8

## 세팅
- spring web
- mysql
- h2 database
- spirng data jpa
- lombok

## 인텔리제이 단축키
- 환경설정 : C + A + s
- Sysout 단축 : sout
- 정렬 : C + A + l

## Java기초
1. public static void
- return 없으면 void
- 있으면 자료형

2. main에서 호출해서 사용하려면 메소드에 static 있어야함

3. getter
```java
public type getVariable(){
return this.variable;
}
```
4. setter
```java
public void setVariable(String variable)(
this.variable = variable;
}
```

- Json을 돌려 준다. RestController

### 그레이들(Gradle)
- 다른사람이 만든 도구 내려받기
1. javascript - npm
2. python - pip
3. java -mavenCentral, jcenter

## 디비(h2)세팅
1. application.properties 세팅 후 
2. h2 console 확인
    - localhost:8080/h2-console에서 확인

## JPA
- spring.jpa.show-sql=true (쿼리 보기)
- 클래스와 리포지토리 생성
- abstract 직접구현 안됨 상속으로만 사용가능
- repository에 저장
- findAll로 찾기
- extends : 뒤에껄 상속해서 가져와서씀

## 스프링의 3가지 영역
1. Controller : 가장 바깥 부분, 요청/응답을 처리함.

2. Service : 중간 부분, 실제 중요한 작동이 많이 일어나는 부분

3. Repo : 가장 안쪽 부분, DB와 맞닿아 있음.
    - (Repository, Entity)

- Update 는 Service 부분에 작성
- Repository 생성, 삭제, 조회

## API
1.get(R)

2 .post(C)

3.put(U)

4.delete(D)

- 주소의 명사는 복수형, 동사x

## JAVASCRIPT
- 백틱
```javascript
let name = '내 이름';
let text = `${name}님의 스프링 5주 완주를 축하합니다!`;
console.log(text);
```


## 3주차 숙제 
### 목록의 시간을 조회 시간으로부터 24시간 이내로하기
- spring jpa localtime between
- 현재시간 LocalDateTime.now()
- 하루전 LocalDateTime.now().minusDays(1)

- findAllByModifiedAtBetweenOrderByModifiedAtDesc
  - 수정시간들 사이를 수정시간을 기준으로 내림차순으로 찾기
- 매개변수 (startTime, endTime) 
  - 두시간(startTime, endTime) 사이에 수정된 시간 찾기

![image](https://user-images.githubusercontent.com/48196352/125170742-656ae300-e1eb-11eb-97a5-26396800c602.png)
