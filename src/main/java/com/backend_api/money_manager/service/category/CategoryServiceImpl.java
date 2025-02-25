package com.backend_api.money_manager.service.category;

import com.backend_api.money_manager.dto.request.category.CategoryIdRequest;
import com.backend_api.money_manager.dto.request.category.CategoryRequest;
import com.backend_api.money_manager.dto.request.s3bucket.S3CategoryIdRequest;
import com.backend_api.money_manager.dto.request.s3bucket.S3CategoryRequest;
import com.backend_api.money_manager.entity.Category;
import com.backend_api.money_manager.exception.ResponseHandler;
import com.backend_api.money_manager.helper.StorageUtil;
import com.backend_api.money_manager.repository.CategoryActionRepository;
import com.backend_api.money_manager.repository.CategoryRepository;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.io.File;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    CategoryRepository categoryRepository;

    private static final String UPLOAD_FOLDER_NAME = "category";


    @Autowired
    StorageUtil storageUtil;

    @Autowired
    CategoryActionRepository categoryActionRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public ResponseEntity<Object> create(CategoryRequest request){
        ModelMapper modelMapper = new ModelMapper();
        Category categoryMapper = modelMapper.map(request, Category.class);
        categoryMapper.setId(UUID.randomUUID().toString());
        var data = categoryRepository.save(categoryMapper);
        return ResponseHandler.generateResponseSuccess(data);
    }

    @Override
    public ResponseEntity<Object> update(CategoryIdRequest request) {
        var category = categoryRepository.findById(request.getId()).orElseThrow();
        category.setName(request.getName());
        category.setIcon(request.getIcon());
        var save = categoryRepository.save(category);
        return ResponseHandler.generateResponseSuccess(save);
    }

    @SneakyThrows
    @Override
    public ResponseEntity<Object> uploadFile(S3CategoryRequest s3CategoryRequest) {
        Category category = new Category();
        var findAction = categoryActionRepository.findById(s3CategoryRequest.getActionId()).orElseThrow();

        category.setId(UUID.randomUUID().toString());
        category.setName(s3CategoryRequest.getFileName());
        category.setCategoryAction(findAction);
        if (s3CategoryRequest.getBase64() != null && !s3CategoryRequest.getBase64().isEmpty()) {
            File file = storageUtil.convertBase64ToFile(s3CategoryRequest.getBase64(), s3CategoryRequest.getFileName());
            if (file != null) {

                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                Path targetPath = uploadPath.resolve(file.getName());
                Files.copy(file.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
                category.setIcon("/uploads/" + file.getName());
                Files.deleteIfExists(file.toPath());
            }
        } else {
            category.setIcon(null);
        }


        categoryRepository.save(category);


        return ResponseHandler.generateResponseSuccess(category);

    }

    @SneakyThrows
    @Override
    public ResponseEntity<Object> updateFile(S3CategoryIdRequest s3CategoryIdRequest) {
        var category = categoryRepository.findById(s3CategoryIdRequest.getId()).orElseThrow();
        category.setName(s3CategoryIdRequest.getFileName());
        if (s3CategoryIdRequest.getBase64() != null && !s3CategoryIdRequest.getBase64().isEmpty()) {
            File file = storageUtil.convertBase64ToFile(s3CategoryIdRequest.getBase64(), s3CategoryIdRequest.getFileName());
            if (file != null) {
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                Path targetPath = uploadPath.resolve(file.getName());
                Files.copy(file.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);

                category.setIcon(targetPath.toString());
                Files.deleteIfExists(file.toPath());
            }
        } else {
            category.setIcon(null);
        }


        categoryRepository.save(category);

        return ResponseHandler.generateResponseSuccess(category);
    }

    @Override
    public ResponseEntity<Object> getAll(String name) {
        var data = categoryRepository.findAllByCategoryActions(name);
        return ResponseHandler.generateResponseSuccess(data);
    }

    @Override
    public ResponseEntity<Object> getId(String id) {
        var data = categoryRepository.findById(id).orElseThrow();
        return ResponseHandler.generateResponseSuccess(data);
    }

    @Override
    public ResponseEntity<Object> delete(String id) {
        var category = categoryRepository.findById(id).orElseThrow();
        category.setIsDelete(true);
        var save = categoryRepository.save(category);
        return ResponseHandler.generateResponseSuccess(save);
    }

    @Override
    public ResponseEntity<Object> deleteSecond(CategoryIdRequest request) {
        var category = categoryRepository.findById(request.getId()).orElseThrow();
        category.setIsDelete(true);
        var save = categoryRepository.save(category);
        return ResponseHandler.generateResponseSuccess(save);
    }

}
