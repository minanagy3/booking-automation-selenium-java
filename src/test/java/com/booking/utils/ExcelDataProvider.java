package com.booking.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ExcelDataProvider {
    private Workbook workbook;
    private Sheet sheet;

    public ExcelDataProvider(String filePath) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        this.workbook = new XSSFWorkbook(fis);
        this.sheet = workbook.getSheetAt(0);
    }

    public TestData getTestData(int rowNumber) {
        Row row = sheet.getRow(rowNumber);
        
        return new TestData(
            getCellValueAsString(row.getCell(0)),
            getCellValueAsString(row.getCell(1)),
            getCellValueAsString(row.getCell(2))
        );
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                }
                return String.valueOf((long) cell.getNumericCellValue());
            default:
                return "";
        }
    }

    public void close() throws IOException {
        if (workbook != null) {
            workbook.close();
        }
    }

    public static LocalDate calculateCheckInDate() {
        return LocalDate.now().plusDays(7); // One week from today
    }

    public static LocalDate calculateCheckOutDate(LocalDate checkInDate) {
        return checkInDate.plusDays(4); // 4 days after check-in
    }

    public static LocalDate parseDate(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return null;
        }
        
        // Handle different date formats
        if (dateString.contains("/")) {
            String[] parts = dateString.split("/");
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int year = Integer.parseInt(parts[2]);
            return LocalDate.of(year, month, day);
        }
        
        return LocalDate.parse(dateString);
    }

    public static class TestData {
        private String location;
        private String checkInDate;
        private String checkOutDate;

        public TestData(String location, String checkInDate, String checkOutDate) {
            this.location = location;
            this.checkInDate = checkInDate;
            this.checkOutDate = checkOutDate;
        }

        public String getLocation() {
            return location;
        }

        public String getCheckInDate() {
            return checkInDate;
        }

        public String getCheckOutDate() {
            return checkOutDate;
        }
    }
}

