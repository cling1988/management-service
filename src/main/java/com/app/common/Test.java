package com.app.common;

import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import java.time.ZonedDateTime;
import java.util.Date;

public class Test {

    public static void main(String abc []){
        Instant i = Instant.now();
        System.out.println(i);

        DateTime dt = new DateTime(new Date());
        System.out.println(dt);

        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);

        LocalDate ld = LocalDate.now();
        System.out.println(ld);

        ZonedDateTime now2 = ZonedDateTime.now();
        System.out.println(now2);

    }
}
