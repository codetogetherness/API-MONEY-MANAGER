package com.backend_api.money_manager.controller.admin.category;

import com.backend_api.money_manager.dto.request.s3bucket.S3CategoryRequest;
import com.backend_api.money_manager.service.category.CategoryActionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/v1/admin")
public class CategoryActionController {

    @Autowired
    CategoryActionService categoryActionService;

    @Operation(
            summary = "Secure data",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @PostMapping("/category/action/upload")
    public ResponseEntity<Object> uploadActionFile(@RequestBody S3CategoryRequest request) throws SQLException {
        return categoryActionService.uploadFileAction(request);
    }
}
