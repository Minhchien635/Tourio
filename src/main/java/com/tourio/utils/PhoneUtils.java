package com.tourio.utils;

public class PhoneUtils {
    private static String regexPhone = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$";

    // +(123) - 456-78-90
    public static boolean isPhone(String data) {
        if (data.matches(regexPhone)) {
            return true;
        }
        return false;
    }
}
