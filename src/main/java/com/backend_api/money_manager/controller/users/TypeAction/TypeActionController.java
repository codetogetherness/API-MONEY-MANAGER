package com.backend_api.money_manager.controller.users.TypeAction;

import com.backend_api.money_manager.dto.request.s3bucket.S3CategoryRequest;
import com.backend_api.money_manager.service.category.CategoryActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/v1/user")
public class TypeActionController {

    @Autowired
    CategoryActionService categoryActionService;

    @GetMapping("/category/action")
    public ResponseEntity<Object> getCategoryAction() {
        return categoryActionService.getActionType();
    }

}
