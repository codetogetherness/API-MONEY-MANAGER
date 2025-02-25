package com.backend_api.money_manager.service.category;

import com.backend_api.money_manager.dto.request.s3bucket.S3CategoryRequest;
import com.backend_api.money_manager.entity.CategoryAction;
import com.backend_api.money_manager.exception.ResponseHandler;
import com.backend_api.money_manager.helper.StorageUtil;
import com.backend_api.money_manager.repository.CategoryActionRepository;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.io.File;


@Service
public class CategoryActionServiceImpl implements CategoryActionService{

    @Autowired
    CategoryActionRepository categoryActionRepository;

    @Autowired
    StorageUtil storageUtil;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @SneakyThrows
    @Override
    public ResponseEntity<Object> uploadFileAction(S3CategoryRequest s3CategoryRequest) {
        CategoryAction categoryAction = new CategoryAction();

        categoryAction.setId(UUID.randomUUID().toString());
        categoryAction.setName(s3CategoryRequest.getFileName());

        if (s3CategoryRequest.getBase64() != null && !s3CategoryRequest.getBase64().isEmpty()) {
            File file = storageUtil.convertBase64ToFile(s3CategoryRequest.getBase64(), s3CategoryRequest.getFileName());
            if (file != null) {
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                Path targetPath = uploadPath.resolve(file.getName());
                Files.copy(file.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
                categoryAction.setIcon("/uploads/" + targetPath.toString());
                Files.deleteIfExists(file.toPath());
            }
        } else {
            categoryAction.setIcon(null);
        }

        categoryActionRepository.save(categoryAction);

        return ResponseHandler.generateResponseSuccess(categoryAction);

    }

    @Override
    public ResponseEntity<Object> getActionType() {
        try{
            var response =  categoryActionRepository.findAll();
            return ResponseHandler.generateResponseSuccess(response);
        }catch (Exception e){
            return ResponseHandler.generateResponseError(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),"");
        }
    }


}
