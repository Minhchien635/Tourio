package com.tourio.controllers;

import com.tourio.models.TourPrice;
import com.tourio.utils.Notification;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class AddTourPriceController implements Initializable {

    public AddTourController addTourController;

    @FXML
    public Button createPriceBtn;

    @FXML
    public DatePicker date_start, date_end;

    @FXML
    public TextField price;

    public void onCreateClick() {
        if (checkInputFields()) {
            TourPrice tourPrice = new TourPrice();
            tourPrice.setAmount(Long.parseLong(price.getText()));
            tourPrice.setDateStart(Date.from(Instant.from((date_start.getValue()).atStartOfDay(ZoneId.systemDefault()))));
            tourPrice.setDateEnd(Date.from(Instant.from((date_end.getValue()).atStartOfDay(ZoneId.systemDefault()))));
            addTourController.tourPrices.add(tourPrice);

            Stage stage = (Stage) createPriceBtn.getScene().getWindow();
            stage.close();
        }
    }

    private boolean checkInputFields() {
        if (date_start.getValue() == null || date_end.getValue() == null || price.getText().isEmpty()) {
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

    private boolean isNumber(String data) {
        try {
            Float.parseFloat(data);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        date_start.setEditable(false);
        date_start.setEditable(false);
    }
}
