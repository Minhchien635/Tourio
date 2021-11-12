package com.tourio.controllers;

import com.tourio.dao.TourDAO;
import com.tourio.models.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UpdateTourController extends AddTourController {
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
    TourController tourController;
    Tour tour;
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

    public UpdateTourController() {
        super();
    }

    public void onSaveClick() {
        List<Location> locations = new ArrayList<>(locationList);
        List<TourPrice> tourPrices = new ArrayList<>(tourPriceList);

        tour.setName(nameTextField.getText());
        tour.setTypeId(typeComboBox.getValue().getId());
        tour.setDescription(descriptionTextArea.getText());

        TourDAO.updateTour(tour, tourPrices, locations);

        tourController.initData();

        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    public void initData(TourController tourController, Tour tour) {
        this.tour = tour;
        this.tourController = tourController;

        nameTextField.setText(tour.getName());

        int indexType = 0;
        int sizeType = tourTypes.size();
        for (int i = 0; i < sizeType; i++) {
            if (tourTypes.get(i).getName().equals(tour.getType().getName())) {
                indexType = i;
                break;
            }
        }
        typeComboBox.setValue(tourTypes.get(indexType));

        descriptionTextArea.setText(tour.getDescription());

        tourPriceList = tour.getPrices();
        List<TourLocationRel> tourLocationRels = tour.getTourRels();
        for (TourLocationRel tourLocationRel : tourLocationRels) {
            locationList.add(tourLocationRel.getLocation());
        }

        initDataPrice();
        initDataLocation();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
    }
}
