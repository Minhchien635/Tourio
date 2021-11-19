package com.tourio.utils;

public class AlertUtils {
    public static void alert(String content) {
        Notification.show("WARNING", "Thông báo", content);
    }
}
