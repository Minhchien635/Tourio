package com.tourio.controllers;

import com.tourio.models.TourPrice;
import com.tourio.utils.Notification;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class CreateTourPriceController implements Initializable {

    private AddTourController addTourController;

    @FXML
    private Button createPriceBtn;

    @FXML
    private DatePicker date_start, date_end;

    @FXML
    private TextField price;

    // Add tourPrice into priceList of addController
    public void onCreateClick() throws IOException {
        if (checkInputFields()) {
            TourPrice tourPrice = new TourPrice();
            tourPrice.setAmount(Float.parseFloat(price.getText()));
            tourPrice.setDateStart(Date.from(Instant.from((date_start.getValue()).atStartOfDay(ZoneId.systemDefault()))));
            tourPrice.setDateEnd(Date.from(Instant.from((date_end.getValue()).atStartOfDay(ZoneId.systemDefault()))));
            addTourController.getTourPriceList().add(tourPrice);

            addTourController.initDataPrice();

            Stage stage = (Stage) createPriceBtn.getScene().getWindow();
            stage.close();
        }
    }

    private boolean checkInputFields() {
        if (date_start.getValue() == null || date_end.getValue() == null || price.getText() == "") {
            Notification.show("WARNING", "Thông báo", "Các trường không được để trống");
            return false;
        }

        if (!isNumber(price.getText())
        ) {
            Notification.show("WARNING", "Thông báo", "Giá không hợp lệ");
            return false;
        }

        if (date_start.getValue().isAfter(date_end.getValue())) {
            Notification.show("WARNING", "Thông báo", "Ngày bắt đầu phải trước ngày kết thúc");
            return false;
        }

        return true;
    }

    // Check data is number
    private boolean isNumber(String data) {
        try {
            Float.parseFloat(data);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //
    public void init(AddTourController addTourController) {
        this.addTourController = addTourController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        date_start.setEditable(false);
        date_start.setEditable(false);
    }
}
