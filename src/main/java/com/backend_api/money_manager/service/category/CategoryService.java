package com.backend_api.money_manager.service.category;

import com.backend_api.money_manager.dto.request.category.CategoryIdRequest;
import com.backend_api.money_manager.dto.request.category.CategoryRequest;
import com.backend_api.money_manager.dto.response.category.CategoryResponse;
import com.backend_api.money_manager.dto.request.s3bucket.S3CategoryIdRequest;
import com.backend_api.money_manager.dto.request.s3bucket.S3CategoryRequest;
import org.springframework.http.ResponseEntity;

public interface CategoryService {
    ResponseEntity<Object> create(CategoryRequest request);

    ResponseEntity<Object> update(CategoryIdRequest request);
    ResponseEntity<Object> uploadFile(S3CategoryRequest s3CategoryRequest);
    ResponseEntity<Object> updateFile(S3CategoryIdRequest request);

    ResponseEntity<Object> getAll(String name);
    ResponseEntity<Object> getId(String id);
    ResponseEntity<Object> delete(String id);
    ResponseEntity<Object> deleteSecond(CategoryIdRequest request);
}
