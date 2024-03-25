package com.booking.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateHelper {
    private static final DateTimeFormatter DISPLAY_FORMATTER = 
        DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static String formatDate(LocalDate date) {
        return date.format(DISPLAY_FORMATTER);
    }

    public static String formatDateForDisplay(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        return date.format(formatter);
    }

    public static boolean areDatesEqual(LocalDate date1, LocalDate date2) {
        return date1.equals(date2);
    }

    public static boolean containsDate(String text, LocalDate date) {
        String day = String.valueOf(date.getDayOfMonth());
        String month = String.valueOf(date.getMonthValue());
        return text.contains(day) && text.contains(month);
    }
}

