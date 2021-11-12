package com.tourio.utils;

import javafx.scene.control.Alert;

public class Notification {
    public static void show(String alertType, String tile, String content) {
        Alert alert = new Alert(Alert.AlertType.valueOf(alertType));
        alert.setTitle(tile);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
