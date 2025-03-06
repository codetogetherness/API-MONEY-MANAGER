package com.backend_api.money_manager.controller.admin.category;

import com.backend_api.money_manager.dto.request.category.CategoryIdRequest;
import com.backend_api.money_manager.dto.request.category.CategoryRequest;
import com.backend_api.money_manager.dto.request.s3bucket.S3CategoryIdRequest;
import com.backend_api.money_manager.dto.request.s3bucket.S3CategoryRequest;
import com.backend_api.money_manager.service.category.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/v1/admin")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(
            summary = "Secure data",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @PostMapping("/category/upload")
    public ResponseEntity<Object> uploadFile(@RequestBody S3CategoryRequest request) throws SQLException {
        return categoryService.uploadFile(request);
    }

    @Operation(
            summary = "Secure data",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @PostMapping("/category/upload/update")
    public ResponseEntity<Object> updateFile(@RequestBody S3CategoryIdRequest request) {
        return categoryService.updateFile(request);
    }

    @Operation(
            summary = "Secure data",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @PostMapping("/category")
    public ResponseEntity<Object> create(@RequestBody CategoryRequest request){
        return categoryService.create(request);
    }

    @Operation(
            summary = "Secure data",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @GetMapping("/category/list/{name}")
    public ResponseEntity<Object> getAll(@PathVariable String name) {
        return categoryService.getAll(name);
    }

    @Operation(
            summary = "Secure data",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @GetMapping("/category/{id}")
    public ResponseEntity<Object> getId(@PathVariable("id") String id) {
        return categoryService.getId(id);
    }

    @Operation(
            summary = "Secure data",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @PostMapping("/category/update")
    public ResponseEntity<Object> updateCategory(@RequestBody CategoryIdRequest request) {
        return categoryService.update(request);
    }

    @Operation(
            summary = "Secure data",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @PostMapping("/category/delete/{id}")
    public ResponseEntity<Object> softDelete(@PathVariable("id") String id) {
        return categoryService.delete(id);
    }

    @Operation(
            summary = "Secure data",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @PostMapping("/category/delete")
    public ResponseEntity<Object> softSecondDelete(@RequestBody CategoryIdRequest request) {
        return categoryService.deleteSecond(request);
    }

}
