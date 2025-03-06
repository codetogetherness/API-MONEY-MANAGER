package com.backend_api.money_manager.controller.users.category;

import com.backend_api.money_manager.service.category.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class CategoryUserController {

    @Autowired
    CategoryService categoryService;

    @Operation(
            summary = "Secure data",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @GetMapping("/categories/{name}")
    public ResponseEntity<Object> getAll(@PathVariable String name) {
        return categoryService.getAll(name);
    }

}
