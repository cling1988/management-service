package com.app.common;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormatter;

public class LocalDatetimeUtil {

    //Date : yyyy-MM-dd
    //Datetime: yyyy-MM-ddThh:mm:ss.zzz

    public static LocalDate parseDate(String date){
        if(null!=date)
            return LocalDate.parse(date);
        return null;
    }

    public static LocalDateTime parseDateTime(String datetime){
        if(null!=datetime)
            return LocalDateTime.parse(datetime);
        return null;
    }

    public static String nowDateString(){
        return LocalDate.now().toString();
    }

    public static String nowDateTimeString(){
        return LocalDateTime.now().toString();
    }



}
