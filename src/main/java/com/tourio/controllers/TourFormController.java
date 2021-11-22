package com.tourio.controllers;

import com.tourio.dao.TourDAO;
import com.tourio.models.Tour;
import com.tourio.models.TourLocationRel;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class TourFormController extends BaseFormController {
    @FXML
    public TableColumn<TourPrice, String>
            priceAmountColumn,
            priceStartColumn,
            priceEndColumn,
            priceActiveColumn;

    @FXML
    public HBox priceActionButtons, locationActionButtons;

    @FXML
    public TextField nameTextField;

    @FXML
    public ComboBox<TourType> typeComboBox;

    @FXML
    public TextArea descriptionTextArea;

    @FXML
    public TableView<TourPrice> priceTableView;

    @FXML
    public ListView<TourLocationRel> locationListView;

    public TourTableController tourTableController;

    public Tour tour;

    public ObservableList<TourLocationRel> locations = FXCollections.observableArrayList();

    public ObservableList<TourPrice> prices = FXCollections.observableArrayList();

    public ObservableList<TourType> types = FXCollections.observableArrayList();

    private void initPriceTable() {
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

        // Data binding
        priceTableView.setItems(prices);
    }

    private void initLocationList() {
        // Render
        locationListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(TourLocationRel item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                    return;
                }

                setText(item.getLocation().getName());
            }
        });

        // Data binding
        locationListView.setItems(locations);
    }

    private void initTypeComboBox() {
        // Render
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

        // Data
        types.setAll(TourDAO.getTypes());
        typeComboBox.setItems(types);
    }

    protected void initReadOnly() {
        nameTextField.setDisable(true);
        typeComboBox.setDisable(true);
        descriptionTextArea.setDisable(true);
        priceActionButtons.getChildren().clear();
        locationActionButtons.getChildren().clear();
        saveButton.setManaged(false);
    }

    public void initDefaultValues() {
        nameTextField.setText(tour.getName());
        descriptionTextArea.setText(tour.getDescription());
        typeComboBox.setValue(tour.getTourType());
        prices.setAll(tour.getTourPrices());
        locations.setAll(tour.getTourLocationRels());
    }


    public void onSaveClick(ActionEvent e) {
        String name = nameTextField.getText();
        if (name == null || name.trim().isEmpty()) {
            AlertUtils.showWarning("Hãy nhập tên tour");
            return;
        }

        TourType tourType = typeComboBox.getValue();
        if (tourType == null) {
            AlertUtils.showWarning("Hãy chọn loại tour");
            return;
        }

        String description = descriptionTextArea.getText();
        if (description == null || description.trim().isEmpty()) {
            AlertUtils.showWarning("Hãy nhập thông tin tour");
            return;
        }

        if (prices.isEmpty()) {
            AlertUtils.showWarning("Hãy thêm ít nhất 1 giá tour");
            return;
        }

        if (locations.isEmpty()) {
            AlertUtils.showWarning("Hãy thêm ít nhất 1 địa điểm tour");
            return;
        }

        tour.setName(name);
        tour.setTourType(tourType);
        tour.setDescription(description);
        tour.setTourPrices(prices);
        //tour.setTourLocationRels(locations);

        TourDAO.saveTour(tour);

        tourTableController.loadData();

        onCancelClick(e);
    }

    public void onAddPriceClick() throws IOException {
        // Init controller
        AddTourPriceController controller = new AddTourPriceController();
        controller.tourFormController = this;

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
        stage.showAndWait();
    }

    public void onEditPriceClick() throws IOException {
        TourPrice tourPrice = priceTableView.getSelectionModel().getSelectedItem();

        if (tourPrice == null) {
            AlertUtils.showWarning("Hãy chọn giá cần sửa");
            return;
        }

        // Init controller
        EditTourPriceController controller = new EditTourPriceController();
        controller.tourFormController = this;
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
        stage.showAndWait();
    }

    public void onDeletePriceClick() {
        int index = priceTableView.getSelectionModel().getSelectedIndex();

        if (index == -1) {
            AlertUtils.showWarning("Hãy chọn giá muốn xóa");
            return;
        }

        prices.remove(index);
    }

    public void onAddLocationClick() throws IOException {
        // Init controller
        AddTourLocationController controller = new AddTourLocationController();
        controller.tourFormController = this;

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
        stage.showAndWait();
    }

    public void onEditLocationClick() throws IOException {
        TourLocationRel tourLocationRel = locationListView.getSelectionModel().getSelectedItem();

        if (tourLocationRel == null) {
            AlertUtils.showWarning("Hãy chọn địa điểm muốn sửa");
            return;
        }

        // Init controller
        EditTourLocationController controller = new EditTourLocationController();
        controller.tourFormController = this;
        controller.tourLocationRel = tourLocationRel;

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
        stage.showAndWait();
    }

    public void onDeleteLocationClick() {
        int index = locationListView.getSelectionModel().getSelectedIndex();

        if (index == -1) {
            AlertUtils.showWarning("Hãy chọn địa điểm muốn xóa");
            return;
        }

        locations.remove(index);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTypeComboBox();
        initLocationList();
        initPriceTable();

        if (tour == null) {
            tour = new Tour();
        } else {
            initDefaultValues();
        }

        if (readOnly) {
            initReadOnly();
        }
    }
}
