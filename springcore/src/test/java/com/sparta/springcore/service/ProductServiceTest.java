package com.sparta.springcore.service;

import com.sparta.springcore.dto.ProductMypriceRequestDto;
import com.sparta.springcore.dto.ProductRequestDto;
import com.sparta.springcore.model.Product;
import com.sparta.springcore.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    ProductRepository productRepository;

    @Test
    @DisplayName("updateProduct() 에 의해 관심 가격이 3만원으로 변경되는지 확인")
    void updateProduct_Normal() {
        // given
        Long productId = 100L;
        int myprice = 30000;

        ProductMypriceRequestDto requestMyPriceDto = new ProductMypriceRequestDto(
                myprice
        );

        Long userId = 12345L;
        ProductRequestDto  requestProductDto = new ProductRequestDto(
                "오리온 꼬북칩 초코츄러스맛 160g",
                "https://shopping-phinf.pstatic.net/main_2416122/24161228524.20200915151118.jpg",
                "https://search.shopping.naver.com/gate.nhn?id=24161228524",
                2350
        );

        Product product = new Product(requestProductDto, userId);

        ProductService productService = new ProductService(productRepository);
        when(productRepository.findById(productId))
                .thenReturn(Optional.of(product));

        // when
        Product result = productService.updateProduct(productId, requestMyPriceDto);

        // then
        assertEquals(myprice, result.getMyprice());
    }

    @Test
    @DisplayName("updateProduct() 에 의해 관심 가격이 100원 이하인 경우 에러 발생")
    void updateProduct_abnormal() {
        // given
        Long productId = 100L;
        int myprice = 50;

        ProductMypriceRequestDto requestMyPriceDto = new ProductMypriceRequestDto(
                myprice
        );

        Long userId = 12345L;
        ProductRequestDto  requestProductDto = new ProductRequestDto(
                "오리온 꼬북칩 초코츄러스맛 160g",
                "https://shopping-phinf.pstatic.net/main_2416122/24161228524.20200915151118.jpg",
                "https://search.shopping.naver.com/gate.nhn?id=24161228524",
                2350
        );

        Product product = new Product(requestProductDto, userId);

        ProductService productService = new ProductService(productRepository);
        when(productRepository.findById(productId))
                .thenReturn(Optional.of(product));

        // when
        // then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            productService.updateProduct(productId, requestMyPriceDto);
        });
    }
}