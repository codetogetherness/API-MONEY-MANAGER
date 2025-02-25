package com.backend_api.money_manager.dto.request.payment;

import java.util.Map;

public class DynamicRequest {
    private Map<String, Object> data;

    // Getter and Setter
    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DynamicRequestDTO{" +
                "data=" + data +
                '}';
    }
}
