package com.backend_api.money_manager.dto.request.s3bucket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class S3CategoryRequest {
    private String name;

    private String icon;
    private String ext;
    private String actionId;

    public String getFileName() {
        return name;
    }

    public String getExt() {
        return ext;
    }

    public void setFileName(String name) {
        this.name = name;
    }

    public String getBase64() {
        return icon;
    }

    public void setBase64(String icon) {
        this.icon = icon;
    }

}
