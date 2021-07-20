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