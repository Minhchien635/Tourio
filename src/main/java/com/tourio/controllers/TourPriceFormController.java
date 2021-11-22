package com.tourio.controllers;

import com.tourio.models.TourPrice;
import com.tourio.utils.AlertUtils;
import com.tourio.utils.DateUtils;
import com.tourio.utils.NumberUtils;
import com.tourio.utils.WindowUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TourPriceFormController extends BaseCreateController {
    @FXML
    public DatePicker startDatePicker, endDatePicker;

    @FXML
    public TextField priceTextField;

    public TourCreateController tourFormController;

    public TourPrice tourPrice = new TourPrice();

    public void onSaveClick(ActionEvent event) {
        LocalDate startDate = startDatePicker.getValue();
        if (startDate == null) {
            AlertUtils.showWarning("Hãy chọn ngày bắt đầu");
            return;
        }

        LocalDate endDate = endDatePicker.getValue();
        if (endDate == null) {
            AlertUtils.showWarning("Hãy chọn ngày kết thúc");
            return;
        }

        if (startDate.isAfter(endDate)) {
            AlertUtils.showWarning("Ngày bắt đầu phải trước ngày kết thúc");
            return;
        }

        String price = priceTextField.getText();
        if (price == null || price.trim().isEmpty()) {
            AlertUtils.showWarning("Hãy nhập giá");
            return;
        }

        if (!NumberUtils.isLong(price)) {
            AlertUtils.showWarning("Giá không hợp lệ");
            return;
        }

        // If this TourPrice is new (not in database)
        if (tourPrice.getId() == null) {
            tourPrice.setTour(tourFormController.tour);
            tourFormController.tourPrices.add(tourPrice);
        }

        tourPrice.setAmount(Long.parseLong(price.trim()));
        tourPrice.setDateStart(DateUtils.parseDate(startDate));
        tourPrice.setDateEnd(DateUtils.parseDate(endDate));

        WindowUtils.closeWindow(event);
    }

    protected void initReadOnly() {
        startDatePicker.setEditable(false);
        startDatePicker.setEditable(false);
    }

    public void initDefaultValues() {
        startDatePicker.setValue(DateUtils.parseLocalDate(tourPrice.getDateStart()));
        endDatePicker.setValue(DateUtils.parseLocalDate(tourPrice.getDateEnd()));
        priceTextField.setText(tourPrice.getAmount().toString());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (tourPrice.getId() != null) {
            initDefaultValues();
        }
    }
}
