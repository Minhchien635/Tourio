package com.tourio.controllers;

import com.tourio.models.Location;
import com.tourio.models.Tour;
import com.tourio.models.TourLocationRel;
import com.tourio.models.TourPrice;
import com.tourio.utils.PriceFormatter;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class TourDetailController implements Initializable {
    @FXML
    public ScrollPane contentScrollPane;

    @FXML
    public TableColumn<TourPrice, String>
            priceAmountColumn,
            priceStartColumn,
            priceEndColumn,
            priceActiveColumn;
    @FXML
    public Button
            priceAddButton,
            priceEditButton,
            priceDeleteButton,
            locationAddButton,
            locationEditButton,
            locationDeleteButton,
            okButton,
            saveButton,
            cancelButton;
    DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
    ObservableList<TourLocationRel> locations = FXCollections.observableArrayList();
    ObservableList<TourPrice> prices = FXCollections.observableArrayList();
    @FXML
    private TextField nameTextField;
    @FXML
    private ComboBox typeComboBox;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private TableView<TourPrice> priceTableView;
    @FXML
    private ListView<TourLocationRel> locationListView;

    public void initPriceTable() {
        // Price amount column render
        priceAmountColumn.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(PriceFormatter.format(data.getValue().getAmount()));
            return property;
        });

        // Price start date column render
        priceStartColumn.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(dateFormatter.format(data.getValue().getDateStart()));
            return property;
        });

        // Price end date column render
        priceEndColumn.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(dateFormatter.format(data.getValue().getDateEnd()));

            return property;
        });

        // Price active column render
        priceActiveColumn.setCellValueFactory(row -> {
            SimpleStringProperty property = new SimpleStringProperty();
            TourPrice data = row.getValue();
            Date start = data.getDateStart();
            Date end = data.getDateEnd();
            Date now = new Date();
            boolean isActive = now.after(start) && now.before(end);
            property.setValue(isActive ? "x" : "");
            return property;
        });

        // Set prices observable list as data
        priceTableView.setItems(prices);
    }

    public void initLocationList() {
        // Row render
        locationListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(TourLocationRel item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setText(null);
                    return;
                }

                Location location = item.getLocation();
                int sequenceLocation = item.getSequence();
                setText(sequenceLocation + ". " + location.getName());
            }
        });

        // Set locations observable list as data
        locationListView.setItems(locations);
    }

    public void initData(Tour tour) {
        // Set data into text fields
        nameTextField.setText(tour.getName());
        typeComboBox.setValue(tour.getType().getName());
        descriptionTextArea.setText(tour.getDescription());

        // Set data into price table
        prices.setAll(tour.getPrices());

        // Set data into location list
        locations.setAll(tour.getTourRels());
    }

    public void onOkClick() {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

    public void onSaveClick() {
    }

    public void onCancelClick() {

    }

    public void onAddPriceClick() {
    }

    public void onEditPriceClick() {

    }

    public void onDeletePriceClick() {

    }

    public void onAddLocationClick() {

    }

    public void onEditLocationClick() {

    }

    public void onDeleteLocationClick() {

    }

    public void initViewOnly() {
        // Disable text fields
        nameTextField.setEditable(false);
        descriptionTextArea.setEditable(false);
        descriptionTextArea.setWrapText(true);

        // Disable action buttons from tables and list views
        for (Button button : new Button[]{
                priceAddButton,
                priceEditButton,
                priceDeleteButton,
                locationAddButton,
                locationEditButton,
                locationDeleteButton
        }) {
            button.setDisable(true);
        }

        // Hide save and cancel button
        saveButton.setManaged(false);
        cancelButton.setManaged(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initPriceTable();
        initLocationList();
        initViewOnly();
    }
}
