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

public class EditTourPriceController implements Initializable {

    public AddTourController addTourController;

    public TourPrice tourPrice;

    @FXML
    public Button createPriceBtn;

    @FXML
    public DatePicker date_start, date_end;

    @FXML
    public TextField price;

    public void onCreateClick() throws IOException {
        if (!checkInputFields()) {
            return;
        }

        tourPrice.setDateStart(Date.from(Instant.from((date_start.getValue()).atStartOfDay(ZoneId.systemDefault()))));
        tourPrice.setDateEnd(Date.from(Instant.from((date_end.getValue()).atStartOfDay(ZoneId.systemDefault()))));
        tourPrice.setAmount(Long.parseLong(price.getText()));

        Stage stage = (Stage) createPriceBtn.getScene().getWindow();
        stage.close();
    }

    public boolean checkInputFields() {
        if (!isNumber(price.getText())) {
            Notification.show("WARNING", "Thông báo", "Giá không hợp lệ");
            return false;
        }

        if (date_start.getValue().isAfter(date_end.getValue())) {
            Notification.show("WARNING", "Thông báo", "Ngày bắt đầu phải trước ngày kết thúc");
            return false;
        }

        return true;
    }

    public boolean isNumber(String data) {
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
        date_end.setEditable(false);

        //Date.from(Instant.from((date_start.getValue()).atStartOfDay(ZoneId.systemDefault())))
        date_start.setValue(tourPrice.getDateStart().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
        );

        date_end.setValue(tourPrice.getDateEnd().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
        );

        price.setText(String.valueOf(tourPrice.getAmount()));
    }
}
