package com.example.valven.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ValvenUtil {

    private ValvenUtil(){}

    public static boolean isNullOrEmptyObject(Object obj) {
        return (obj == null || obj == "");
    }

    public static final String formatLocalDateTimeAsString(LocalDateTime localDateTime, String dateFormat){
        var formatter = DateTimeFormatter.ofPattern(dateFormat);
        return localDateTime.format(formatter);
    }

}
