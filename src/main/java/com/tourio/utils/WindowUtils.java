package com.tourio.utils;

import javafx.scene.Node;
import javafx.stage.Window;

import java.util.EventObject;

public class WindowUtils {
    public static Window getOwner(EventObject event) {
        Node source = (Node) event.getSource();
        return source.getScene().getWindow();
    }
}
