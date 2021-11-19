package com.tourio.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {
    public static final DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    public static String format(Date date) {
        return formatter.format(date);
    }
}
