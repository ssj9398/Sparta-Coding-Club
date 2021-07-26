# Sparta-Coding-Club
## API 설계

1. 회원관리 기능 api

|Name|tag|Request|Response|
|-----|---|---|---|
|회원가입|GET /user/singup|-|회원가입HTML|
||POST /user/signup|회원 가입에 필요한 정보|-|
|회원로그인|GET /user/login|-|회원 로그인 HTML|
||POST/user/login|아이디, 패스워드|-|
|회원로그인|GET /user/logout|-|-|

2. 상품 등록 및 조회 api

|Name|tag|설명|
|-----|---|---|
|상품 등록(회원별)|POST/api/products|로그인되어 있는 회원 별로 상품 등록|
|상품 조회(회원별)|GET/api/products|로그인되어 있는 회원이 등록한 상품만 조회|
|상품 등록(관리자)|GET/api/admin/products|모든 회원의 상품 조회|


## 스프링 시큐리티
1. build.gradle 추가
```java
// 스프링 시큐리티
implementation 'org.springframework.boot:spring-boot-starter-security'
// Thymeleaf (뷰 템플릿 엔진)
implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
```

2. WebSecurityConfig 추가
```java
package com.sparta.springcore.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();

        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }
}
```

3. 유의사항
    - 스프링 시큐리티는 추가해주지 않으면 모든 것을 막아버리기 때문에(css, js도 추가 해줘야함)

```java
http.authorizeRequests()
                // image 폴더를 login 없이 허용
                .antMatchers("/images/**").permitAll()
                // css 폴더를 login 없이 허용
                .antMatchers("/css/**").permitAll()
                // 그 외 모든 요청은 인증과정 필요
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/user/login")
                .loginProcessingUrl("/user/login")
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
                .logout()
                .permitAll();
```
4. 로그인 과정
- ![image](https://user-images.githubusercontent.com/48196352/126368029-009acf4b-556a-4c95-a846-e88a7ffd593a.png)
- ![image](https://user-images.githubusercontent.com/48196352/126368063-4d70c04f-2621-4175-8f75-f7f957d37218.png)

인증/인가 성공 시에만, Controller 에게 회원 정보 전달

## 비밀번호 암호화
1. 해쉬함수를 통해 암호화하여 비밀번호를 저장하게 되면 일방향 암호 알고리즘이라 풀수가 없음
2. 스프링 시큐리티에서 제공해주면서 권고되고 있는 BCrypt 해쉬함수
```java
@Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }
```

## 카카오 로그인 API
- ![image](https://user-images.githubusercontent.com/48196352/126368671-85030b48-5804-4f8c-a7d0-b657e4fbd335.png)

1. 카카오서버에서 받은 사용자 정보를 이용해 회원가입
```java
// 우리 DB 에서 회원 Id 와 패스워드
        // 회원 Id = 카카오 nickname
        String username = nickname;
        // 패스워드 = 카카오 Id + ADMIN TOKEN
        String password = kakaoId + ADMIN_TOKEN;

        // DB 에 중복된 Kakao Id 가 있는지 확인
        User kakaoUser = userRepository.findByKakaoId(kakaoId)
                .orElse(null);

        if (kakaoUser == null) {
            // 패스워드 인코딩
            String encodedPassword = passwordEncoder.encode(password);
            // ROLE = 사용자
            UserRole role = UserRole.USER;

            kakaoUser = new User(nickname, encodedPassword, email, role, kakaoId);
            userRepository.save(kakaoUser);
```

2. 로그인 처리
```java
// 로그인 처리
        Authentication kakaoUsernamePassword = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(kakaoUsernamePassword);
        SecurityContextHolder.getContext().setAuthentication(authentication);
```

## 숙제
- 기존 회원이 동일한 이메일로 카카오로 로그인 가능하게 구현
- 참고
1. 카카오id 사용자 조회
```
User kakaoUser = userRepository.findByKakaoId(kakaoId)
                .orElse(null);
```
2. 로그인 처리
```java
// 스프링 시큐리티 통해 로그인 처리
UserDetailsImpl userDetails = new UserDetailsImpl(kakaoUser);
Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
SecurityContextHolder.getContext().setAuthentication(authentication);
```
3. 동일 이메일 체크
```java
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByKakaoId(Long kakaoId);
		// 추가! Email 주소가 같은 사용자 조회
    Optional<User> findByEmail(String email);
}
```
4. 기존 사용자에게 kakao ID값 추가
```java
existUser.setKakaoId(kakaoId);
userRepository.save(existUser);
```

## 테스트
### JUnit 단위테스트
- 프로그램을 작은 단위로 쪼개서 각 단위가 정확하게 동작하는지 검사

1. JUnit 사용 설정
- build.gradle 의존성 주입
```java
dependencies {
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
}

test {
    useJUnitPlatform()
}
```

2. 테스트 파일 생성
- 테스트 하려는 파일로 접근하여 Generate -> Test 생성해준다.
- 테스트 코드를 작성해 준다.
```java
package com.sparta.springcore.model;

import com.sparta.springcore.dto.ProductRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    @Test
    @DisplayName("정상 케이스")
    void createProduct_Normal() {
        // given
        Long userId = 100L;
        String title = "오리온 꼬북칩 초코츄러스맛 160g";
        String image = "https://shopping-phinf.pstatic.net/main_2416122/24161228524.20200915151118.jpg";
        String link = "https://search.shopping.naver.com/gate.nhn?id=24161228524";
        int lprice = 2350;

        ProductRequestDto requestDto = new ProductRequestDto(
                title,
                image,
                link,
                lprice
        );

        // when
        Product product = new Product(requestDto, userId);

        // then
        assertNull(product.getId());
        assertEquals(userId, product.getUserId());
        assertEquals(title, product.getTitle());
        assertEquals(image, product.getImage());
        assertEquals(link, product.getLink());
        assertEquals(lprice, product.getLprice());
        assertEquals(0, product.getMyprice());
    }
}
```

### Edge 케이스를 고려한 단위 테스트
1. 다양한 테스트 Edge 케이스 고려
- null의 경우
- ex)
```
1) 상품명이 null 로 들어올 경우
2) 상품명이 빈 문자열인 경우에도 저장을 해야 할까? 
    (UI 에는 어떻게 표시될까?)
```
- 협의를 통한 처리

2. TDD(Test-Driven Development)
- AS-IS) 설계 → 개발 → 테스트 **(→ 설계 수정)** 순서를
- TO-BE) 설계 → 테스트 **(→설계 수정)** → 개발로 변경
![image](https://user-images.githubusercontent.com/48196352/126905209-bc4d686b-b1b1-4300-b429-8f51923bb242.png)

### Mock object 단위 테스트
- Mockito framework: Mock 객체를 쉽게 만들 수 있는 방법 제공

### 통합테스트
1. 단위 테스트 (Unit Test)
    - 하나의 모듈이나 클래스에 대해 세밀한 부분까지 테스트 가능
    - 모듈 간에 상호 작용 검증 못함
2. 통합 테스트 (Integration Test)
    - 두 개 이상의 모듈이 연결된 상태를 테스트
    - 모듈 간의 연결에서 발생하는 에러 검증 가능
3. E2E 테스트 (End to End Test)
    - 실제 사용자의 실행 환경과 거의 동일한 환경에서 테스트 진행 (=블랙박스 테스팅)

### 스프링부트를 이용한 통합 테스트
- 통합 테스트
    - 여러 단위 테스트를 하나의 통합된 테스트로 수행
    - Controller → Service → Repository
- 단위 테스트 시 스프링 동작 안 함

    예제 코드)

    ```java
    class ProductTest {

        @Autowired
        ProductService productService;

    		// ...

    		@Test
        @DisplayName("정상 케이스")
        void createProduct_Normal() {
    				// ...

    				****// 에러 발생! productService 가 null
            Product productByService  = productService.createProduct(requestDto, userId);
    ```

- "@SpringBootTest"
    - 스프링 부트가 제공하는 테스트 어노테이션.
    - 테스트 수행 시 스프링이 동작함
        - Spring IoC 사용 가능
        - Repository 사용해 DB CRUD 가능
    - End to End 테스트도 가능
        - **Client 요청** → Controller → Service → Repository → **Client 응답**
- @Order(1), @Order(2), ...
    - 테스트의 순서를 정할 수 있음
