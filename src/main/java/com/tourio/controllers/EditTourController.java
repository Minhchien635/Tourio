package com.tourio.controllers;

import com.tourio.dao.TourDAO;
import com.tourio.models.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EditTourController extends AddTourController {
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

    public TourTableController tourTableController;

    public Tour tour;

    public EditTourController() {
        super();
    }

    public void onSaveClick(ActionEvent e) {
        List<Location> locations = new ArrayList<>(this.locations);
        List<TourPrice> tourPrices = new ArrayList<>(this.tourPrices);

        tour.setName(nameTextField.getText());
        tour.setTourType(typeComboBox.getValue());
        tour.setDescription(descriptionTextArea.getText());

        TourDAO.updateTour(tour, tourPrices, locations);

        tourTableController.loadData();

        Node source = (Node) e.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);

        nameTextField.setText(tour.getName());

        int indexType = 0;
        int sizeType = tourTypes.size();
        for (int i = 0; i < sizeType; i++) {
            if (tourTypes.get(i).getName().equals(tour.getTourType().getName())) {
                indexType = i;
                break;
            }
        }

        typeComboBox.setValue(tourTypes.get(indexType));

        descriptionTextArea.setText(tour.getDescription());

        tourPrices.addAll(tour.getTourPrices());

        List<TourLocationRel> tourLocationRels = tour.getTourLocationRels();
        for (TourLocationRel tourLocationRel : tourLocationRels) {
            locations.add(tourLocationRel.getLocation());
        }
    }
}
