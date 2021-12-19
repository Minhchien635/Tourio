package com.tourio.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    public Button groupReportButton, tourButton, tourTypeButton, groupButton, locationButton, costTypeButton, customerButton, employeeButton, jobButton;
    @FXML
    private AnchorPane content;

    @FXML
    private VBox menu;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Load tour fxml into content pane
            loadView("tour_table");

            // Disable all other button active styles
            for (Node node : menu.getChildren()) {
                node.getStyleClass().remove("active");
            }

            // Set tour menu button as active
            tourButton.getStyleClass().add("active");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set onclick events for menu buttons
        setMenuButtonOnAction(groupReportButton, "group_report");
        setMenuButtonOnAction(tourButton, "tour_table");
        setMenuButtonOnAction(tourTypeButton, "tour_type_table");
        setMenuButtonOnAction(groupButton, "group_table");
        setMenuButtonOnAction(locationButton, "location_table");
        setMenuButtonOnAction(costTypeButton, "cost_type_table");
        setMenuButtonOnAction(customerButton, "customer_table");
        setMenuButtonOnAction(employeeButton, "employee_table");
        setMenuButtonOnAction(jobButton, "job_table");
    }

    public void setMenuButtonOnAction(Button button, String fxmlFileName) {
        button.setOnAction(actionEvent -> {
            try {
                // Load fxml into content pane
                loadView(fxmlFileName);

                // Disable all other button active styles
                for (Node node : menu.getChildren()) {
                    node.getStyleClass().remove("active");
                }

                // Add active class to clicked button
                button.getStyleClass().add("active");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void loadView(String fxmlFileName) throws IOException {
        // Load fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tourio/fxml/" + fxmlFileName + ".fxml"));
        Parent fxml = loader.load();

        // Set fxml into content pane
        content.getChildren().setAll(fxml);
    }
}