package com.booking.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CreateExcelData {
    public static void main(String[] args) {
        try {
            String filePath = System.getProperty("user.dir") + 
                System.getProperty("file.separator") + "data" + 
                System.getProperty("file.separator") + "test-data.xlsx";
            
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("TestData");

            // Create header row
            Row headerRow = sheet.createRow(0);
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            Cell headerCell1 = headerRow.createCell(0);
            headerCell1.setCellValue("Location");
            headerCell1.setCellStyle(headerStyle);

            Cell headerCell2 = headerRow.createCell(1);
            headerCell2.setCellValue("CheckInDate");
            headerCell2.setCellStyle(headerStyle);

            Cell headerCell3 = headerRow.createCell(2);
            headerCell3.setCellValue("CheckOutDate");
            headerCell3.setCellStyle(headerStyle);

            // Calculate dates
            LocalDate checkInDate = LocalDate.now().plusDays(7); // One week from today
            LocalDate checkOutDate = checkInDate.plusDays(4); // 4 days after check-in

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            // Add test data row
            Row dataRow = sheet.createRow(1);
            dataRow.createCell(0).setCellValue("Alexandria");
            dataRow.createCell(1).setCellValue(checkInDate.format(formatter));
            dataRow.createCell(2).setCellValue(checkOutDate.format(formatter));

            // Auto-size columns
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);

            // Write to file
            FileOutputStream fileOut = new FileOutputStream(filePath);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();

            System.out.println("Excel file created successfully at: " + filePath);
        } catch (IOException e) {
            System.err.println("Error creating Excel file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

