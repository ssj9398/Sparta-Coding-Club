package com.sparta.springcore.model;

import com.sparta.springcore.Timestamped;
import com.sparta.springcore.dto.ProductRequestDto;
import com.sparta.springcore.util.URLValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;

@Setter
@Getter // get 함수를 일괄적으로 만들어줍니다.
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity // DB 테이블 역할을 합니다.
public class Product extends Timestamped {
    // ID가 자동으로 생성 및 증가합니다.
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    // 반드시 값을 가지도록 합니다.
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String link;

    @Column(nullable = false)
    private int lprice;

    @Column(nullable = false)
    private int myprice;

    @Column(nullable = false)
    private Long userId;

    @ManyToMany
    private List<Folder> folderList;

    public Product(ProductRequestDto requestDto, Long userId) {
        // 입력값 Validation
        if (userId == null || userId < 0) {
            throw new IllegalArgumentException("회원 Id 가 유효하지 않습니다.");
        }

        if (requestDto.getTitle() == null || requestDto.getTitle().isEmpty()) {
            throw new IllegalArgumentException("저장할 수 있는 상품명이 없습니다.");
        }

        if (!URLValidator.urlValidator(requestDto.getImage())) {
            throw new IllegalArgumentException("상품 이미지 URL 포맷이 맞지 않습니다.");
        }

        if (!URLValidator.urlValidator(requestDto.getLink())) {
            throw new IllegalArgumentException("상품 최저가 페이지 URL 포맷이 맞지 않습니다.");
        }

        if (requestDto.getLprice() <= 0) {
            throw new IllegalArgumentException("상품 최저가가 0 이하입니다.");
        }

        // 관심상품을 등록한 회원 Id 저장
        this.userId = userId;
        this.title = requestDto.getTitle();
        this.image = requestDto.getImage();
        this.link = requestDto.getLink();
        this.lprice = requestDto.getLprice();
        this.myprice = 0;
    }

    // 관심 상품의 가격 변경 시 사용합니다.
    public void updateMyPrice(int price) {
        this.myprice = price;
    }

    public void addFolder(Folder folder) {
        this.folderList.add(folder);
    }
}