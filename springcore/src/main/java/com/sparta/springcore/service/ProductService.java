package com.sparta.springcore.service;

import com.sparta.springcore.dto.ProductMypriceRequestDto;
import com.sparta.springcore.dto.ProductRequestDto;
import com.sparta.springcore.model.Folder;
import com.sparta.springcore.model.Product;
import com.sparta.springcore.model.User;
import com.sparta.springcore.repository.FolderRepository;
import com.sparta.springcore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ProductService {
    // 멤버 변수 선언
    private final ProductRepository productRepository;
    private final FolderRepository folderRepository;
    private static final int MIN_PRICE = 100;

    // 생성자: ProductService() 가 생성될 때 호출됨
    @Autowired
    public ProductService(ProductRepository productRepository, FolderRepository folderRepository) {
        // 멤버 변수 생성
        this.productRepository = productRepository;
        this.folderRepository = folderRepository;
    }

    public Page<Product> getProducts(Long userId, int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        return productRepository.findAllByUserId(userId, pageable);
    }

    @Transactional // 메소드 동작이 SQL 쿼리문임을 선언합니다.
    public Product createProduct(ProductRequestDto requestDto, Long userId) {
        // 요청받은 DTO 로 DB에 저장할 객체 만들기
        Product product = new Product(requestDto, userId);
        productRepository.save(product);
        return product;
    }

    @Transactional // 메소드 동작이 SQL 쿼리문임을 선언합니다.
    public Product updateProduct(Long id, ProductMypriceRequestDto requestDto) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );

        // 변경될 관심 가격이 유효한지 확인합니다.
        int myPrice = requestDto.getMyprice();
        if (myPrice < MIN_PRICE) {
            throw new IllegalArgumentException("유효하지 않은 관심 가격입니다. 최소 " + MIN_PRICE + " 원 이상으로 설정해 주세요.");
        }

        product.updateMyPrice(myPrice);
        return product;
    }

    // 모든 상품 조회 (관리자용)
    public Page<Product> getAllProducts(int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        return productRepository.findAll(pageable);
    }

    @Transactional
    public Product addFolder(Long productId, Long folderId, User user) {
        // 1) 상품를 조회합니다.
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new NullPointerException("해당 관심상품 아이디가 존재하지 않습니다.")
        );

        // 2) 관심상품을 조회합니다.
        Folder folder = folderRepository.findById(folderId).orElseThrow(
                () -> new NullPointerException("해당 폴더 아이디가 존재하지 않습니다.")
        );

        // 3) 조회한 폴더와 관심상품이 모두 로그인한 회원의 소유인지 확인합니다.
        Long userId = user.getId();
        Long productUserId = product.getUserId();
        Long folderUserId = folder.getUser().getId();

        if (!userId.equals(productUserId) || !userId.equals(folderUserId)) {
            throw new IllegalArgumentException("회원님의 관심상품이 아니거나, 폴더가 아니어서 추가하지 못했습니다.");
        }

        // 4) 상품에 폴더를 추가합니다.
        product.addFolder(folder);
        return product;
    }
}