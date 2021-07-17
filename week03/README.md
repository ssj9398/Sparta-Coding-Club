# 완성 url
- http://selectshop.govpped.com:3572
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
