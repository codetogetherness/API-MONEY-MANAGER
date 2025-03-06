package com.backend_api.money_manager.controller.users.TypeAction;

import com.backend_api.money_manager.dto.request.s3bucket.S3CategoryRequest;
import com.backend_api.money_manager.service.category.CategoryActionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/v1/user")
public class TypeActionController {

    @Autowired
    CategoryActionService categoryActionService;

    @Operation(
            summary = "Secure data",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @GetMapping("/category/action")
    public ResponseEntity<Object> getCategoryAction() {
        return categoryActionService.getActionType();
    }

}
