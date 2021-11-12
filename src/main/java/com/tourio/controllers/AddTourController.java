package com.tourio.controllers;

import com.tourio.dao.TourDAO;
import com.tourio.models.Location;
import com.tourio.models.Tour;
import com.tourio.models.TourPrice;
import com.tourio.models.TourType;
import com.tourio.utils.Notification;
import com.tourio.utils.PriceFormatter;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AddTourController implements Initializable {
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
    List<TourPrice> tourPriceList = new ArrayList<>();
    List<Location> locationList = new ArrayList<>();
    TourController tourController;
    DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
    ObservableList<TourType> tourTypes = FXCollections.observableArrayList(TourDAO.getTypes());
    ObservableList<String> locations = FXCollections.observableArrayList();
    ObservableList<TourPrice> prices = FXCollections.observableArrayList();
    @FXML
    private TextField nameTextField;
    @FXML
    private ComboBox<TourType> typeComboBox;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private TableView<TourPrice> priceTableView;
    @FXML
    private ListView<String> locationListView;

    public List<TourPrice> getTourPriceList() {
        return tourPriceList;
    }

    public List<Location> getLocationList() {
        return locationList;
    }

    public void init(TourController tourController) {
        this.tourController = tourController;
    }

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
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setText(null);
                    return;
                }

                setText(item);
            }
        });

        // Set locations observable list as data
        locationListView.setItems(locations);
    }

    public void initTypeList() {
        tourTypes.setAll(TourDAO.getTypes());

        typeComboBox.setItems(tourTypes);

        Callback<ListView<TourType>, ListCell<TourType>> factory = lv -> new ListCell<TourType>() {

            @Override
            protected void updateItem(TourType item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }

        };

        typeComboBox.setCellFactory(factory);
        typeComboBox.setButtonCell(factory.call(null));
    }

    // Set data into price table
    public void initDataPrice() {
        prices.setAll(tourPriceList);
        priceTableView.refresh();
    }

    // Set data into location listview
    public void initDataLocation() {
        int size = locationList.size();
        locations.clear();
        for (int i = 0; i < size; i++) {
            int index = i + 1;
            locations.add(index + ". " + locationList.get(i).getName());
        }
        locationListView.refresh();
    }

    // Add data into location list
    public void addDataLocation() {
        if (locationList.size() > 0) {
            locations.add(locationList.size() + ". " + locationList.get(locationList.size() - 1).getName());
            locationListView.refresh();
        }
    }

    public void onOkClick() throws IOException {

    }

    public void onSaveClick() {
        Tour tour = new Tour();
        List<Location> locations = new ArrayList<>(locationList);
        List<TourPrice> tourPrices = new ArrayList<>(tourPriceList);

        tour.setName(nameTextField.getText());
        tour.setTypeId(typeComboBox.getValue().getId());
        tour.setDescription(descriptionTextArea.getText());

        TourDAO.createTour(tour, tourPrices, locations);

        tourController.initData();
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    public void onCancelClick() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void onAddPriceClick() throws IOException {
        // Create view window
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tourio/fxml/create_tour_price.fxml"));
        loader.setController(new CreateTourPriceController());
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("Tạo giá tour");
        stage.initModality(Modality.APPLICATION_MODAL);

        // Pass tour into controller
        AddTourController addTourController = this;
        CreateTourPriceController createTourPriceController = loader.getController();
        createTourPriceController.init(addTourController);

        // Show window
        stage.showAndWait();
    }

    public void onEditPriceClick() throws IOException {
        TourPrice tourPrice = priceTableView.getSelectionModel().getSelectedItem();

        if (tourPrice != null) {
            // Create view window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tourio/fxml/create_tour_price.fxml"));
            loader.setController(new EditTourPriceController());
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setTitle("Sửa giá");
            stage.initModality(Modality.APPLICATION_MODAL);

            // Pass tour into controller
            AddTourController addTourController = this;
            EditTourPriceController editTourPriceController = loader.getController();
            editTourPriceController.init(tourPrice, addTourController);

            // Show window
            stage.showAndWait();

        } else {
            Notification.show("INFORMATION", "Thông báo", "Chọn giá cần sửa");
        }
    }

    public void onDeletePriceClick() {
        tourPriceList.remove(priceTableView.getSelectionModel().getSelectedItem());
        initDataPrice();
    }

    public void onAddLocationClick() throws IOException {
        // Create view window
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tourio/fxml/add_tour_location.fxml"));
        loader.setController(new AddTourLocationController());
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("Thêm địa chỉ");
        stage.initModality(Modality.APPLICATION_MODAL);

        // Pass tour into controller
        AddTourController addTourController = this;
        AddTourLocationController addTourLocationController = loader.getController();
        addTourLocationController.init(addTourController);

        // Show window
        stage.showAndWait();
    }

    public void onEditLocationClick() throws IOException {
        int indexItem = locationListView.getSelectionModel().getSelectedIndex();

        if (indexItem != -1) {
            Location location = locationList.get(indexItem);

            // Create view window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tourio/fxml/add_tour_location.fxml"));
            loader.setController(new EditTourLocationController());
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setTitle("Thêm địa chỉ");
            stage.initModality(Modality.APPLICATION_MODAL);

            // Pass tour into controller
            AddTourController addTourController = this;
            EditTourLocationController editTourLocationController = loader.getController();
            editTourLocationController.init(indexItem, location, addTourController);

            // Show window
            stage.showAndWait();
        } else {
            Notification.show("WARNING", "Thông báo", "Chọn địa điểm cần sửa");
        }
    }

    public void onDeleteLocationClick() {
        int indexItem = locationListView.getSelectionModel().getSelectedIndex();
        if (indexItem != -1) {
            locationList.remove(indexItem);
            initDataLocation();
        }

    }

    public void initViewOnly() {
        // Hide save and cancel button
        okButton.setManaged(false);
        descriptionTextArea.setWrapText(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTypeList();
        initPriceTable();
        initLocationList();
        initViewOnly();
    }
}
