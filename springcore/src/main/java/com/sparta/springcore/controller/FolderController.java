package com.sparta.springcore.controller;

import com.sparta.springcore.dto.FolderCreateRequestDto;
import com.sparta.springcore.exception.ApiException;
import com.sparta.springcore.model.Folder;
import com.sparta.springcore.model.Product;
import com.sparta.springcore.model.User;
import com.sparta.springcore.security.UserDetailsImpl;
import com.sparta.springcore.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class FolderController {
    // 멤버 변수 선언
    private final FolderService folderService;

    @Autowired
    public FolderController(FolderService folderService) {
        // 멤버 변수 생성
        this.folderService = folderService;
    }

    // 회원이 등록한 모든 폴더 조회
    @GetMapping("/api/folders")
    public List<Folder> getFolders(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return folderService.getFolders(userDetails.getUser());
    }

    // 회원이 폴더 추가
    @PostMapping("/api/folders")
    public List<Folder> addFolders(@RequestBody FolderCreateRequestDto folderCreateRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<String> folderNames = folderCreateRequestDto.getFolderNames();
        return folderService.createFolders(folderNames, userDetails.getUser());
    }

    // 회원이 등록한 폴더 내 모든 상품 조회
    @GetMapping("/api/folders/{folderId}/products")
    public Page<Product> getProductsOnFolder(@PathVariable("folderId") Long folderId,
                                             @RequestParam("page") int page,
                                             @RequestParam("size") int size,
                                             @RequestParam("sortBy") String sortBy,
                                             @RequestParam("isAsc") boolean isAsc,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        page = page - 1;
        return folderService.getProductsOnFolder(userDetails.getUser(), page, size, sortBy, isAsc, folderId);
    }
}