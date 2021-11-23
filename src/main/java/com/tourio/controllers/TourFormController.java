package com.tourio.controllers;

import com.tourio.dao.TourDAO;
import com.tourio.dao.TourTypeDAO;
import com.tourio.models.Tour;
import com.tourio.models.TourLocationRel;
import com.tourio.models.TourPrice;
import com.tourio.models.TourType;
import com.tourio.utils.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
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
    public HBox
            priceActionButtons,
            locationActionButtons;

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

    public Tour tour = new Tour();

    public ObservableList<TourLocationRel> tourLocationRels = FXCollections.observableArrayList();

    public ObservableList<TourPrice> tourPrices = FXCollections.observableArrayList();

    public ObservableList<TourType> tourTypes = FXCollections.observableArrayList();

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
            property.setValue(DateUtils.format(data.getValue().getDateStart()));
            return property;
        });

        // Price end date column render
        priceEndColumn.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(DateUtils.format(data.getValue().getDateEnd()));

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

        // Bind data
        priceTableView.setItems(tourPrices);
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

                item.setSequence((long) getIndex() + 1);
                setText(item.getSequence() + ". " + item.getLocation().getName());
            }
        });

        // Bind data
        locationListView.setItems(tourLocationRels);
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

        // Load data
        tourTypes.setAll(TourTypeDAO.getAll());

        // Bind data
        typeComboBox.setItems(tourTypes);
    }

    public void onAddPriceClick(ActionEvent event) throws IOException {
        // Init controller
        TourPriceFormController controller = new TourPriceFormController();
        controller.tourFormController = this;

        // Show modal
        new StageBuilder("tour_price_form", controller, "Tạo giá tour")
                .setModalOwner(event)
                .setDimensionsAuto()
                .build()
                .showAndWait();
    }

    public void onEditPriceClick(ActionEvent event) throws IOException {
        TourPrice tourPrice = priceTableView.getSelectionModel().getSelectedItem();

        if (tourPrice == null) {
            AlertUtils.showWarning("Hãy chọn giá cần sửa");
            return;
        }

        // Init controller
        TourPriceFormController controller = new TourPriceFormController();
        controller.tourFormController = this;
        controller.tourPrice = tourPrice;

        // Show modal
        new StageBuilder("tour_price_form", controller, "Sửa giá")
                .setModalOwner(event)
                .setDimensionsAuto()
                .build()
                .showAndWait();
    }

    public void onDeletePriceClick() {
        int index = priceTableView.getSelectionModel().getSelectedIndex();

        if (index == -1) {
            AlertUtils.showWarning("Hãy chọn giá muốn xóa");
            return;
        }

        tourPrices.remove(index);
    }

    public void onAddLocationClick(ActionEvent event) throws IOException {
        // Init controller
        TourLocationFormController controller = new TourLocationFormController();
        controller.tourFormController = this;

        // Show modal
        new StageBuilder("tour_location_form", controller, "Thêm địa điểm")
                .setModalOwner(event)
                .setDimensionsAuto()
                .build()
                .showAndWait();
    }

    public void onDeleteLocationClick() {
        int index = locationListView.getSelectionModel().getSelectedIndex();

        if (index == -1) {
            AlertUtils.showWarning("Hãy chọn địa điểm muốn xóa");
            return;
        }

        tourLocationRels.remove(index);
    }

    @Override
    public void initReadOnly() {
        nameTextField.setDisable(true);
        typeComboBox.setDisable(true);
        descriptionTextArea.setDisable(true);
        priceActionButtons.getChildren().clear();
        locationActionButtons.getChildren().clear();
        saveButton.setManaged(false);
    }

    @Override
    public void initDefaultValues() {
        nameTextField.setText(tour.getName());
        descriptionTextArea.setText(tour.getDescription());
        typeComboBox.setValue(tour.getTourType());
        tourPrices.setAll(tour.getTourPrices());
        tourLocationRels.setAll(tour.getTourLocationRels());
    }

    @Override
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

        if (tourPrices.isEmpty()) {
            AlertUtils.showWarning("Hãy thêm ít nhất 1 giá tour");
            return;
        }

        if (tourLocationRels.isEmpty()) {
            AlertUtils.showWarning("Hãy thêm ít nhất 1 địa điểm tour");
            return;
        }

        tour.setName(nameTextField.getText());
        tour.setTourType(typeComboBox.getValue());
        tour.setDescription(descriptionTextArea.getText());

        if (tour.getId() == null) {
            TourDAO.create(tour, tourPrices, tourLocationRels);
        } else {
            TourDAO.update(tour, tourPrices, tourLocationRels);
        }

        tourTableController.loadData();
        closeWindow(e);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTypeComboBox();
        initLocationList();
        initPriceTable();

        if (tour.getId() != null) {
            initDefaultValues();
        }

        if (read_only) {
            initReadOnly();
        }
    }
}
