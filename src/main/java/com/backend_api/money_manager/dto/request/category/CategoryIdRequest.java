package com.backend_api.money_manager.dto.request.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CategoryIdRequest {

    private String id;

    private String name;

    private String icon;
}
