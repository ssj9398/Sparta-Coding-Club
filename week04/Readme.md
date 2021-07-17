## 네이버 나만의 셀렉샵

- 네이버 api : https://developers.naver.com/products/intro/plan/

### 요구사항
1. 키워드로 상품 검색 후 결과 목록으로 보여주기

2. 관심 상품 등록

3. 관심 상품 조회

4. 관심 상품에 원하는 가격 등록 후 가격보다 낮은 경우 표시
------------------------------------------------

### API 설계

|요구사항|method|반환타입|
|------|---|---|
|키워드로 상품 검색 후 결과 목록으로 보여주기|GET|List<ItemDto>|
|관심 상품 등록|POST|Product|
|관심 상품 조회|GET|List<Product>|
|관심 상품에 원하는 가격 등록 후 가격보다 낮은 경우 표시|PUT|id|
------------------------------------------------

### 3계층 설계

1. Controller
    - ProductRestController : 관심 상품 관련 컨트롤러

    - SearchRequestController : 검색 관련 컨트롤러

2. Service
    - ProductService : 관심 상품 가격 변경

3. Repository
    - Product : 관심 상품 테이블
    - ProductRepository : 관심 상품 조회, 저장
    - ProductRequestDto : 관심 상품 등록하기
    - ProductMypriceRequestDto : 관심 상품 변경하기
    - ItemDto : 검색 결과
------------------------------------------------

### 구현
1.Product, ProductRepository, TimeStamped, ProductRestController 생성
    
    
### 스케줄러 어노테이션
// 초, 분, 시, 일, 월, 주 순서
    @Scheduled(cron = "0 0 1 * * *")

메인에 @EnableScheduling 써서 사용해 주어야 한다.
    
    
------------------------------------------------------------------------------------------

## AWS로 배포해보기
### 스프링 부트 MYSQL 연결

1. RDS 구매, 포트열기
2. intellij에서 Mysql 연결해주기
```
spring.datasource.url=jdbc:mysql://나의엔드포인트:3306/myselectshop
spring.datasource.username=나의USERNAME
spring.datasource.password=나의패스워드
spring.jpa.hibernate.ddl-auto=update
```

3. EC2 서버 구매 및 접속
  1. 프리티어로 구매
  2. 접속 (gitbash)
- ssh -i /path/my-key-pair.pem ubuntu@"구매한 ec2서버 ip"
    - 리눅스 명령어
```
ls: 내 위치의 모든 파일을 보여준다.

pwd: 내 위치(폴더의 경로)를 알려준다.

mkdir: 내 위치 아래에 폴더를 하나 만든다.

cd [갈 곳]: 나를 [갈 곳] 폴더로 이동시킨다.

cd .. : 나를 상위 폴더로 이동시킨다.

cp -r [복사할 것] [붙여넣기 할 것]: 복사 붙여넣기

rm -rf [지울 것]: 지우기

sudo [실행 할 명령어]: 명령어를 관리자 권한으로 실행한다.
sudo su: 관리가 권한으로 들어간다. (나올때는 exit으로 나옴)
```

4. EC2 배포하기
tasks -> build -> build 더블클릭후 build -> libs 안 스냅샷.jar를 filezila를 통해 업로드 해준다.
OpenJdk 설치도 해주어야햠
```
sudo apt-get update
sudo apt-get install openjdk-8-jdk
java -version
```
스프링 부트 작동시키기
```
java -jar JAR파일명.jar
```
원격 접속을 종료하더라도 서버가 계속 돌게 하기
```
nohup java -jar JAR파일명.jar &
```


포트번호를 빼고 접속 가능하게 아래 입력후 재기동 해주면 됨
```
sudo iptables -t nat -A PREROUTING -i eth0 -p tcp --dport 80 -j REDIRECT --to-port 8080
```

------------------------------------------------------------------------------------------
    
![image](https://user-images.githubusercontent.com/48196352/125892730-31cf103f-ca50-4682-8b73-cbe36fb76611.png)
    
![image](https://user-images.githubusercontent.com/48196352/125892753-5f4568c4-e6ee-4988-9cff-e66bffa7903b.png)


