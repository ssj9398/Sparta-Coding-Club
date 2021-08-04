package com.sparta.springcore.service;

import com.sparta.springcore.model.Folder;
import com.sparta.springcore.model.User;
import com.sparta.springcore.repository.FolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FolderService {
    // 멤버 변수 선언
    private final FolderRepository folderRepository;

    // 생성자: ProductFolderService() 가 생성될 때 호출됨
    @Autowired
    public FolderService(FolderRepository folderRepository) {
        // 멤버 변수 생성
        this.folderRepository = folderRepository;
    }

    // 회원 ID 로 등록된 모든 폴더 조회
    public List<Folder> getFolders(User user) {
        return folderRepository.findAllByUser(user);
    }

    public List<Folder> createFolders(List<String> folderNameList, User user) {
        List<Folder> folderList = new ArrayList<>();
        for (String folderName : folderNameList) {
            Folder folder = new Folder(folderName, user);
            folderList.add(folder);
        }
        folderList = folderRepository.saveAll(folderList);
        return folderList;
    }
}