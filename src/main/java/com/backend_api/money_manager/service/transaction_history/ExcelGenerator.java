package com.backend_api.money_manager.service.transaction_history;

import com.backend_api.money_manager.entity.HistoryTransaction;
import com.backend_api.money_manager.repository.HistoryTransactionRepository;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

@Service
public class ExcelGenerator {

    @Autowired
    private HistoryTransactionRepository historyTransactionRepository;

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    // Initialize workbook and sheet only once
    public void initializeWorkbook() {
        workbook = new XSSFWorkbook(); // Create workbook once
        sheet = workbook.createSheet("HistoryTransaction"); // Create sheet
    }

    // Create the header row with bold and specific font size
    public void writeHeader() {
        Row row = sheet.createRow(0);
        CellStyle style = createCellStyle(16, true); // Header style: bold and font size 16
        createCell(row, 0, "ID", style);
        createCell(row, 1, "Amount", style);
        createCell(row, 2, "Title", style);
        createCell(row, 3, "Description", style);
    }

    // Create a new cell and apply the style
    public void createCell(Row row, int columnCount, Object valueOfCell, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (valueOfCell instanceof Integer) {
            cell.setCellValue((Integer) valueOfCell);
        } else if (valueOfCell instanceof Long) {
            cell.setCellValue((Long) valueOfCell);
        } else if (valueOfCell instanceof String) {
            cell.setCellValue((String) valueOfCell);
        } else if (valueOfCell instanceof Boolean) {
            cell.setCellValue((Boolean) valueOfCell);
        }
        cell.setCellStyle(style); // Apply style to the cell
    }

    // Write data rows to the Excel sheet
    public void write() {
        int rowCount = 1;
        CellStyle style = createCellStyle(14, false); // Regular cell style with font size 14
        List<HistoryTransaction> historyTransactions = historyTransactionRepository.findAll();
        for (HistoryTransaction record : historyTransactions) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, record.getId(), style);
            createCell(row, columnCount++, record.getAmount(), style);
            createCell(row, columnCount++, record.getTitle(), style);
            createCell(row, columnCount++, record.getDescription(), style);
        }
    }


    private CellStyle createCellStyle(int fontHeight, boolean isBold) {
        CellStyle style = workbook.createCellStyle(); // Create style tied to the same workbook
        XSSFFont font = workbook.createFont(); // Create font tied to the same workbook
        font.setFontHeightInPoints((short) fontHeight);
        font.setBold(isBold);
        style.setFont(font);
        return style;
    }

    public void generateExcelFile(HttpServletResponse response) throws IOException {

        initializeWorkbook();
        writeHeader();
        write();

        String id = UUID.randomUUID().toString();
        String filename = id + "history_transactions.xlsx";
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();
    }
}
