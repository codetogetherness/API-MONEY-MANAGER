package com.backend_api.money_manager.service.category;

import com.backend_api.money_manager.dto.request.s3bucket.S3CategoryRequest;
import org.springframework.http.ResponseEntity;

public interface CategoryActionService {

    ResponseEntity<Object> uploadFileAction(S3CategoryRequest s3CategoryRequest);
    ResponseEntity<Object> getActionType();
}
