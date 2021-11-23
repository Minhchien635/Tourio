package com.tourio;

import com.tourio.controllers.MainController;
import com.tourio.utils.StageBuilder;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        MainController controller = new MainController();

        new StageBuilder("main", controller, "Tourio - Quản lý du lịch")
                .setStage(stage)
                .build()
                .show();
    }
}