package com.tourio.utils;

import javafx.scene.control.Alert;

public class AlertUtils {
    public static void show(Alert.AlertType alertType, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void showWarning(String content) {
        show(Alert.AlertType.WARNING, content);
    }
}
