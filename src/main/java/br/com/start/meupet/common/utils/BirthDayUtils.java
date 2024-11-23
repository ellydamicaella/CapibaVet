package br.com.start.meupet.common.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BirthDayUtils {

    public static LocalDate convertToDate(String dateOfBirthStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(dateOfBirthStr, formatter);
    }

    public static String formatDateOfBirth(LocalDate dateOfBirth) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dateOfBirth.format(formatter);
    }
}
