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
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class TourPriceFormController extends BaseFormController {
    @FXML
    public DatePicker startDatePicker, endDatePicker;

    @FXML
    public TextField priceTextField;

    public TourFormController tourFormController;

    public TourPrice tourPrice;

    @Override
    public void onSaveClick(ActionEvent e) {
        LocalDate startLocalDate = startDatePicker.getValue();
        if (startLocalDate == null) {
            AlertUtils.showWarning("Hãy chọn ngày bắt đầu");
            return;
        }

        LocalDate endLocalDate = endDatePicker.getValue();
        if (endLocalDate == null) {
            AlertUtils.showWarning("Hãy chọn ngày kết thúc");
            return;
        }

        if (startLocalDate.isAfter(endLocalDate)) {
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

        Date startDate = DateUtils.parseDate(startLocalDate);
        Date endDate = DateUtils.parseDate(endLocalDate);
        Optional<TourPrice> conflictedTourPrice = tourFormController.tourPrices
                .stream()
                .filter(p -> DateUtils.isOverlapping(p.getDateStart(), p.getDateEnd(), startDate, endDate))
                .findFirst();

        if (conflictedTourPrice.isPresent() && conflictedTourPrice.get() != tourPrice) {
            AlertUtils.showWarning("Khoảng thời gian này bị trùng lặp với giá đã tạo");
            return;
        }

        if (tourPrice == null) {
            tourPrice = new TourPrice();
            tourPrice.setTour(tourFormController.tour);
            tourPrice.setAmount(Long.parseLong(priceTextField.getText().trim()));
            tourPrice.setDateStart(startDate);
            tourPrice.setDateEnd(endDate);
            tourFormController.tourPrices.add(tourPrice);
        } else {
            tourPrice.setAmount(Long.parseLong(priceTextField.getText().trim()));
            tourPrice.setDateStart(startDate);
            tourPrice.setDateEnd(endDate);
            tourFormController.priceTableView.refresh();
        }

        closeWindow(e);
    }

    @Override
    public void initReadOnly() {
    }

    public void initFormValues() {
        try {
            startDatePicker.setValue(DateUtils.parseLocalDate(tourPrice.getDateStart()));
            endDatePicker.setValue(DateUtils.parseLocalDate(tourPrice.getDateEnd()));
            priceTextField.setText(tourPrice.getAmount().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void initDatePickers() {
        startDatePicker.setEditable(false);
        endDatePicker.setEditable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initDatePickers();

        if (tourPrice != null) {
            initFormValues();
        }
    }
}