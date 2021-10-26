package com.tourio;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/tourio/fxml/main.fxml"));
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("Quản lí du lịch");
        stage.setScene(new Scene(root, 697, 528));
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}