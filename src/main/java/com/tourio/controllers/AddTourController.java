package com.tourio.controllers;

import com.tourio.dao.TourDAO;
import com.tourio.models.Location;
import com.tourio.models.Tour;
import com.tourio.models.TourPrice;
import com.tourio.models.TourType;
import com.tourio.utils.AlertUtils;
import com.tourio.utils.DateFormatter;
import com.tourio.utils.PriceFormatter;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class AddTourController implements Initializable {
    @FXML
    public TableColumn<TourPrice, String>
            priceAmountColumn,
            priceStartColumn,
            priceEndColumn,
            priceActiveColumn;

    @FXML
    public TextField nameTextField;

    @FXML
    public ComboBox<TourType> typeComboBox;

    @FXML
    public TextArea descriptionTextArea;

    @FXML
    public TableView<TourPrice> priceTableView;

    @FXML
    public ListView<Location> locationListView;

    public TourTableController tourTableController;

    public ObservableList<Location> locations = FXCollections.observableArrayList();

    public ObservableList<TourPrice> tourPrices = FXCollections.observableArrayList();

    public ObservableList<TourType> tourTypes = FXCollections.observableArrayList();

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
            property.setValue(DateFormatter.format(data.getValue().getDateStart()));
            return property;
        });

        // Price end date column render
        priceEndColumn.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(DateFormatter.format(data.getValue().getDateEnd()));

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
        priceTableView.setItems(tourPrices);
    }

    public void initLocationList() {
        // Row render
        locationListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Location item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                    return;
                }

                setText(item.getName());
            }
        });

        // Set locations observable list as data
        locationListView.setItems(locations);
    }

    public void initTypeComboBox() {
        tourTypes.setAll(TourDAO.getTypes());
        typeComboBox.setItems(tourTypes);

        Callback<ListView<TourType>, ListCell<TourType>> factory = (lv) ->
                new ListCell<>() {
                    @Override
                    protected void updateItem(TourType item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty ? "" : item.getName());
                    }
                };

        typeComboBox.setCellFactory(factory);
        typeComboBox.setButtonCell(factory.call(null));
    }

    public void onSaveClick(ActionEvent e) {
        String name = nameTextField.getText();
        if (name == null || name.trim().isEmpty()) {
            AlertUtils.alert("Hãy nhập tên tour");
            return;
        }

        TourType tourType = typeComboBox.getValue();
        if (tourType == null) {
            AlertUtils.alert("Hãy chọn loại tour");
            return;
        }

        String description = descriptionTextArea.getText();
        if (description == null || description.trim().isEmpty()) {
            AlertUtils.alert("Hãy nhập thông tin tour");
            return;
        }

        Tour tour = new Tour();
        tour.setName(name);
        tour.setTourType(tourType);
        tour.setDescription(description);

        TourDAO.createTour(tour, tourPrices, locations);

        tourTableController.loadData();

        Node source = (Node) e.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void onCancelClick(ActionEvent e) {
        Node source = (Node) e.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void onAddPriceClick() throws IOException {
        // Init controller
        AddTourPriceController controller = new AddTourPriceController();
        controller.addTourController = this;

        // Load view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tourio/fxml/add_tour_price.fxml"));
        loader.setController(controller);

        // Render view window
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("Tạo giá tour");
        stage.initModality(Modality.APPLICATION_MODAL);

        // Show window
        stage.showAndWait();
    }

    public void onEditPriceClick() throws IOException {
        TourPrice tourPrice = priceTableView.getSelectionModel().getSelectedItem();

        if (tourPrice == null) {
            AlertUtils.alert("Hãy chọn giá cần sửa");
        }

        // Init controller
        EditTourPriceController controller = new EditTourPriceController();
        controller.addTourController = this;
        controller.tourPrice = tourPrice;

        // Load view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tourio/fxml/add_tour_price.fxml"));
        loader.setController(controller);

        // Render view window
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("Sửa giá");
        stage.initModality(Modality.APPLICATION_MODAL);

        // Show window
        stage.showAndWait();
    }

    public void onDeletePriceClick() {
        int index = priceTableView.getSelectionModel().getSelectedIndex();
        tourPrices.remove(index);
    }

    public void onAddLocationClick() throws IOException {
        // Init controller
        AddTourLocationController controller = new AddTourLocationController();
        controller.addTourController = this;

        // Load view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tourio/fxml/add_tour_location_new.fxml"));
        loader.setController(controller);

        // Render view window
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("Thêm địa chỉ");
        stage.initModality(Modality.APPLICATION_MODAL);

        // Show window
        stage.showAndWait();
    }

    public void onEditLocationClick() throws IOException {
        int index = locationListView.getSelectionModel().getSelectedIndex();

        if (index == -1) {
            AlertUtils.alert("Hãy chọn địa điểm muốn sửa");
            return;
        }

        // Init controller
        EditTourLocationController controller = new EditTourLocationController();
        controller.addTourController = this;
        controller.index = index;

        // Load view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tourio/fxml/add_tour_location_new.fxml"));
        loader.setController(controller);

        // Render view window
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("Thêm địa chỉ");
        stage.initModality(Modality.APPLICATION_MODAL);

        // Show window
        stage.showAndWait();
    }

    public void onDeleteLocationClick() {
        int indexItem = locationListView.getSelectionModel().getSelectedIndex();

        if (indexItem == -1) {
            AlertUtils.alert("Hãy chọn địa điểm muốn xóa");
            return;
        }

        locations.remove(indexItem);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTypeComboBox();
        initPriceTable();
        initLocationList();
    }
}
