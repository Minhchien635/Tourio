package com.tourio.controllers;

import com.tourio.dao.TourDAO;
import com.tourio.models.*;
import com.tourio.utils.PriceFormatter;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class TourDetailController implements Initializable {
    @FXML
    public TableColumn<TourPrice, String>
            priceAmountColumn,
            priceStartColumn,
            priceEndColumn,
            priceActiveColumn;

    @FXML
    public Button okButton;

    @FXML
    private TextField nameTextField;

    @FXML
    private ComboBox<TourType> typeComboBox;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private TableView<TourPrice> priceTableView;

    @FXML
    private ListView<TourLocationRel> locationListView;

    DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
    ObservableList<TourLocationRel> locations = FXCollections.observableArrayList();
    ObservableList<TourPrice> prices = FXCollections.observableArrayList();

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
                long sequenceLocation = item.getSequence();
                setText(sequenceLocation + ". " + location.getName());
            }
        });

        // Set locations observable list as data
        locationListView.setItems(locations);
    }

    public void initTypeComboBox() {
        ObservableList<TourType> types = FXCollections.observableArrayList();
        types.setAll(TourDAO.getTypes());
        typeComboBox.setItems(types);

        // Render list
        typeComboBox.setCellFactory(tourTypeListView ->
                new ListCell<>() {
                    @Override
                    protected void updateItem(TourType item, boolean empty) {
                        super.updateItem(item, empty);

                        setText(empty ? "" : item.getName());
                    }
                }
        );

        // Render selected
        typeComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(TourType item) {
                return item == null ? null : item.getName();
            }

            @Override
            public TourType fromString(String s) {
                return null;
            }
        });
    }

    public void initData(Tour tour) {
        nameTextField.setText(tour.getName());
        descriptionTextArea.setText(tour.getDescription());
        typeComboBox.setValue(tour.getTourType());
        prices.setAll(tour.getTourPrices());
        locations.setAll(tour.getTourLocationRels());
    }

    public void onOkClick() {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initPriceTable();
        initLocationList();
        initTypeComboBox();
    }
}
