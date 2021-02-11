package com.example.chatlistassignment.utils;

import android.util.Pair;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static java.util.Optional.ofNullable;

public class DateUtils {


    public static Pair<String, String> getHeaderDateAndTime(Date date) {
        DateTime dateTime = new DateTime(ofNullable(date).orElse(new Date()));
        if (dateTime.toLocalDate().equals(new LocalDate())) {
            return new Pair<>("Today", getFormattedDateForChatMessage(date));
        } else if (dateTime.toLocalDate().equals(new LocalDate().minusDays(1))) {
            return new Pair<>("Yesterday", getFormattedDateForChatMessage(date));
        } else if (new Interval(DateTime.now().withTimeAtStartOfDay().minusDays(6), Days.SIX)
                .contains(date.getTime())) {
            return new Pair<>(dateTime.dayOfWeek().getAsText(Locale.getDefault()), getFormattedDateForChatMessage(date));
        } else if (new Interval(DateTime.now().withTimeAtStartOfDay().minusYears(1), Years.ONE)
                .contains(date.getTime())) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yy");
            return new Pair<>(dateTimeFormatter.print(dateTime), getFormattedDateForChatMessage(date));
        } else {
            DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("MMM dd");
            return new Pair<>(dateTimeFormatter.print(dateTime), getFormattedDateForChatMessage(date));
        }

    }


    private static DateFormat createDateFormat(String dateStringFormat, Locale locale, TimeZone timeZone) {
        DateFormat dateFormat = new SimpleDateFormat(dateStringFormat, locale);
        dateFormat.setTimeZone(timeZone);
        return dateFormat;
    }

    public static String getFormattedDateForChatMessage(Date date) {
        if (date == null) {
            return "";
        } else {
            String time = createDateFormat("hh:mm a", Locale.ENGLISH, TimeZone.getDefault()).format(date);
            return time.replace("am", "AM").replace("pm", "PM");
        }
    }


}
