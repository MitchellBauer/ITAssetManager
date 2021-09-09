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

    public static Calendar importUTCMySQLToCal(String strDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = sdf.parse(strDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static String exportCalToUTCMySQL(Calendar parCalendar) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String calendarString = sdf.format(parCalendar.getTime());
        return calendarString;
    }

    public static String formatCalForTblView(Calendar parCalendar) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String calendarString = sdf.format(parCalendar.getTime());
        return calendarString;
    }

    public static Calendar localDateToCalendar(LocalDate localDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        //assuming start of day
        calendar.set(localDate.getYear(), localDate.getMonthValue()-1, localDate.getDayOfMonth());
        return calendar;
    }

    public static LocalDate calendarToLocalDate(Calendar parCal){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String calendarString = sdf.format(parCal.getTime());
        LocalDate date = LocalDate.parse(calendarString);
        return date;
    }

    public static Calendar combineDatePickerAndCombo(LocalDate userInputtedDate, String userInputtedTime) throws ParseException {
        DateTimeFormatter inputtedFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime date = LocalDateTime.parse(userInputtedDate.toString() + " " + userInputtedTime, inputtedFormat);
        Calendar calResult = Calendar.getInstance();
        calResult.clear();
        calResult.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth(), date.getHour(), date.getMinute());
        return calResult;
    }

    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(Calendar.getInstance().getTime());
    }

    /**
     * getApptHour is for populating a ComboBox with the time extracted from the given Calendar data.
     *
     * @param parCal - Assumes Data is from MySQL
     * @return - Returns hh:mm a formatting to fit with ComboBox
     */
    public static String getApptHour(Calendar parCal) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        return sdf.format(parCal.getTime());
    }

    /**
     * stringToOnlyHourTime is for converting Combobox time into real world time for comparison
     *
     * @param inputtedTime
     * @return
     */
    public static LocalTime stringToOnlyHourTime(String inputtedTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        return LocalTime.parse(inputtedTime, dateTimeFormatter);
    }
}
