package com.tourio;

import com.tourio.controllers.TourController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        TourController tourController = new TourController();
        tourController.show();
    }

    public static void main(String[] args) {
        launch();
    }
}