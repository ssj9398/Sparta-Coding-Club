package com.sparta.week04.controller;

import com.sparta.week04.models.Product;
import com.sparta.week04.models.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProductRestController {

    private final ProductRepository productRepository;
    @GetMapping("/api/products")
    public List<Product> readProducts(){
        return productRepository.findAll();
    }

}
