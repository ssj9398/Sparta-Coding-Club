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
