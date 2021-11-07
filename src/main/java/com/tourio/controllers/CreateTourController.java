package com.tourio.controllers;

import com.tourio.dao.TourDAO;
import com.tourio.models.Tour;
import com.tourio.models.TourLocationRel;
import com.tourio.models.TourPrice;
import com.tourio.utils.PriceFormatter;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CreateTourController implements Initializable {
    @FXML
    private ComboBox<String> location_comboBox;

    @FXML
    private ListView<String> location_listView;

    @FXML
    private ChoiceBox<String> type_choiceButton;

    @FXML
    private TextField name_textField;

    @FXML
    private TextArea description_textArea;

    @FXML
    private Button createTour_button= new Button();

    @FXML
    private  Button price_button = new Button();

    private void  init(){
        createTour_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });
        price_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init();
    }
}
