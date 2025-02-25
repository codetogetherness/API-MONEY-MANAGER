package com.backend_api.money_manager.dto.response.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor

public class CategoryActionResponse {
    private String name;

    private String icon;
}
