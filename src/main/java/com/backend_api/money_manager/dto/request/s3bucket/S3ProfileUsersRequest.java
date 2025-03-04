package com.backend_api.money_manager.dto.request.s3bucket;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class S3ProfileUsersRequest {
    private String fullName;

    private String profileIcon;

    private String phoneNumber;

    private String ext;

    public String getFileName() {
        return fullName;
    }

    public String getExt() {
        return ext;
    }

    public void setFileName(String fullName) {
        this.fullName = fullName;
    }

    public String getBase64() {
        return profileIcon;
    }

    public void setBase64(String profileIcon) {
        this.profileIcon = profileIcon;
    }
}
