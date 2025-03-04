package com.backend_api.money_manager.dto.response.chart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class StickChart {
    private String label;
    private String total;
}
