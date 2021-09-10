package com.bauerperception.itassetmanager.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeUtil {

    public static String exportLocalDateToMySQL(LocalDate myDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return formatter.format(myDate);
    }

    public static LocalDate importMySQLToLocalDate(String purchasedDateString) {
        return LocalDate.parse(purchasedDateString);
    }
}
