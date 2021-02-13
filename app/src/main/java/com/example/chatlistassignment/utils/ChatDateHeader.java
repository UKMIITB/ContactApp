package com.example.chatlistassignment.utils;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

public class ChatDateHeader {

    public static String getChatDateHeader(Date date) {
        DateTime dateTime = new DateTime(date);

        if (dateTime.toLocalDate().equals(new LocalDate()))
            return "Today";
        else if (dateTime.toLocalDate().equals(new LocalDate().minusDays(1)))
            return "Yesterday";
        else {
            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy/MM/dd");
            return formatter.print(dateTime);
        }
    }
}
