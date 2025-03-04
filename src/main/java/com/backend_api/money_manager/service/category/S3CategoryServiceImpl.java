package com.backend_api.money_manager.service.category;

import com.backend_api.money_manager.component.AmazonClient;
import com.backend_api.money_manager.dto.request.s3bucket.S3CategoryIdRequest;
import com.backend_api.money_manager.dto.request.s3bucket.S3CategoryRequest;
import com.backend_api.money_manager.entity.Category;
import com.backend_api.money_manager.exception.ResponseHandler;
import com.backend_api.money_manager.helper.StorageUtil;
import com.backend_api.money_manager.repository.category.CategoryRepository;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

import java.util.UUID;

@Service
public class S3CategoryServiceImpl implements S3CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    private static final String UPLOAD_FOLDER_NAME = "public-files";

    @Autowired
    AmazonClient amazonClient;

    @Autowired
    StorageUtil storageUtil;

    
    @Override
    public ResponseEntity<Object> uploadFile(S3CategoryRequest s3CategoryRequest) {
        Category category = new Category();

        category.setId(UUID.randomUUID().toString());
        category.setName(s3CategoryRequest.getFileName());

        File file = storageUtil.convertBase64ToFile(s3CategoryRequest.getBase64(), s3CategoryRequest.getFileName());
        String amazon = amazonClient.uploadFileToBucket( storageUtil.generateFileName() + s3CategoryRequest.getExt(),file,UPLOAD_FOLDER_NAME);
        category.setIcon(amazon);
        categoryRepository.save(category);

        try {
            FileUtils.forceDelete(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ResponseHandler.generateResponseSuccess(category);

    }


    @Override
    public ResponseEntity<Object> updateFile(S3CategoryIdRequest s3CategoryIdRequest) {
        var category = categoryRepository.findById(s3CategoryIdRequest.getId()).orElseThrow();
        category.setName(s3CategoryIdRequest.getFileName());

        File file = storageUtil.convertBase64ToFile(s3CategoryIdRequest.getBase64(), s3CategoryIdRequest.getFileName());
        String amazon = amazonClient.uploadFileToBucket( storageUtil.generateFileName() + s3CategoryIdRequest.getExt(),file,UPLOAD_FOLDER_NAME);
        category.setIcon(amazon);
        categoryRepository.save(category);

        try {
            FileUtils.forceDelete(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ResponseHandler.generateResponseSuccess(category);

    }



}
