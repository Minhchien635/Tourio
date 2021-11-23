package com.tourio.controllers;

import com.tourio.models.TourPrice;
import com.tourio.utils.AlertUtils;
import com.tourio.utils.DateUtils;
import com.tourio.utils.NumberUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TourPriceFormController extends BaseFormController {
    @FXML
    public DatePicker startDatePicker, endDatePicker;

    @FXML
    public TextField priceTextField;

    public TourFormController tourFormController;

    public TourPrice tourPrice;

    public void syncTourPrice() {
        tourPrice.setAmount(Long.parseLong(priceTextField.getText().trim()));
        tourPrice.setDateStart(DateUtils.parseDate(startDatePicker.getValue()));
        tourPrice.setDateEnd(DateUtils.parseDate(endDatePicker.getValue()));
    }

    @Override
    public void onSaveClick(ActionEvent e) {
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

        if (tourPrice == null) {
            tourPrice = new TourPrice();
            tourPrice.setTour(tourFormController.tour);
            syncTourPrice();
            tourFormController.tourPrices.add(tourPrice);
        } else {
            syncTourPrice();
            tourFormController.priceTableView.refresh();
        }

        closeWindow(e);
    }

    @Override
    public void initReadOnly() {
    }

    public void initDefaultValues() {
        startDatePicker.setValue(DateUtils.parseLocalDate(tourPrice.getDateStart()));
        endDatePicker.setValue(DateUtils.parseLocalDate(tourPrice.getDateEnd()));
        priceTextField.setText(tourPrice.getAmount().toString());
    }

    public void initDatePickers() {
        startDatePicker.setEditable(false);
        startDatePicker.setEditable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initDatePickers();

        if (tourPrice != null) {
            initDefaultValues();
        }
    }
}
