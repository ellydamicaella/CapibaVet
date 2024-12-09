package br.com.start.meupet.common.utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public final class DateTimeUtils {
    public static LocalTime convertToDateTime(String dateOfBirthStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return LocalTime.parse(dateOfBirthStr, formatter);
    }

    public static String formatDateTime(LocalTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return dateTime.format(formatter);
    }
}
