package com.tourio.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private AnchorPane content;

    @FXML
    private VBox menu;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadTourView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadView(String fxmlName, String title, String buttonId) throws IOException {
        // Load fxml file
        Parent fxml = FXMLLoader.load(getClass().getResource("/com/tourio/fxml/" + fxmlName + ".fxml"));

        // Set fxml into content pane
        content.getChildren().setAll(fxml);

        // Disable all other button active styles
        for (Node node : menu.getChildren()) {
            node.getStyleClass().remove("active");
        }

        // Add active class to clicked button
        Button button = (Button) menu.lookup("#" + buttonId);
        button.getStyleClass().add("active");
    }

    @FXML
    private void loadTourView() throws IOException {
        loadView("tour", "Tour", "tourButton");
    }

    @FXML
    private void loadTourTypeView() throws IOException {
        loadView("tour-type", "Loại tour", "tourTypeButton");
    }

    @FXML
    private void loadGroupView() throws IOException {
        loadView("group", "Đoàn khách", "groupButton");
    }

    @FXML
    private void loadLocationView() throws IOException {
        loadView("location", "Địa điểm", "locationButton");
    }

    @FXML
    private void loadCostTypeView() throws IOException {
        loadView("cost-type", "Loại chi phí", "costTypeButton");
    }

    @FXML
    private void loadCustomerView() throws IOException {
        loadView("customer", "Khách hàng", "customerButton");
    }

    @FXML
    private void loadEmployeeView() throws IOException {
        loadView("employee", "Nhân viên", "employeeButton");
    }

    @FXML
    private void loadJobView() throws IOException {
        loadView("job", "Công việc", "jobButton");
    }
}