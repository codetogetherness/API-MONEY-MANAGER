package com.backend_api.money_manager.controller.users.generateExcel;

import com.backend_api.money_manager.service.transaction_history.ExcelGenerator;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.OutputStream;

@RestController
@RequestMapping("/api/v1")
public class GenerateData {

    @Autowired
    ExcelGenerator excelGenerator;

    @GetMapping("/export-data")
    public void setExcelGenerate(HttpServletResponse response) throws IOException {
      excelGenerator.generateExcelFile(response);
    }

}
