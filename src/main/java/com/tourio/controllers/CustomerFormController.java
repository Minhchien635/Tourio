package com.tourio.controllers;

import com.tourio.dao.CustomerDAO;
import com.tourio.enums.SexType;
import com.tourio.models.Customer;
import com.tourio.utils.AlertUtils;
import com.tourio.utils.PhoneUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerFormController extends BaseFormController {
    @FXML
    public TextField nameTextField, phoneTextField, idNumberTextField, nationalityTextField, addressTextField;

    @FXML
    public ComboBox<SexType> sexTypeComboBox;

    public CustomerTableController customerTableController;

    public Customer customer = new Customer();

    public ObservableList<SexType> sexTypes = FXCollections.observableArrayList();

    private void initSexTypeComboBox() {
        // Load data
        sexTypes.setAll(SexType.values());

        // Bind data
        sexTypeComboBox.setItems(sexTypes);
    }

    @Override
    public void initReadOnly() {
        nameTextField.setDisable(true);
        sexTypeComboBox.setDisable(true);
        phoneTextField.setDisable(true);
        idNumberTextField.setDisable(true);
        nationalityTextField.setDisable(true);
        addressTextField.setDisable(true);
        saveButton.setManaged(false);
    }

    @Override
    public void initDefaultValues() {
        nameTextField.setText(customer.getName());
        sexTypeComboBox.setValue(customer.getSex());
        phoneTextField.setText(customer.getPhone());
        idNumberTextField.setText(customer.getCccd());
        nationalityTextField.setText(customer.getNationality());
        addressTextField.setText(customer.getAddress());
    }

    @Override
    public void onSaveClick(ActionEvent e) {
        String name = nameTextField.getText();
        if (name == null || name.trim().isEmpty()) {
            AlertUtils.showWarning("Hãy nhập tên");
            return;
        }

        SexType sexType = sexTypeComboBox.getSelectionModel().getSelectedItem();
        if (sexType == null) {
            AlertUtils.showWarning("Hãy chọn giới tính");
            return;
        }

        String phone = phoneTextField.getText();
        if (phone == null || phone.trim().isEmpty()) {
            AlertUtils.showWarning("Hãy nhập số điện thoại");
            return;
        }

        if (!PhoneUtils.isPhone(phone)) {
            AlertUtils.showWarning("Số điện thoại không hợp lệ");
            return;
        }

        String idNumber = idNumberTextField.getText();
        if (idNumber == null || idNumber.trim().isEmpty()) {
            AlertUtils.showWarning("Hãy nhập số căn cước công dân");
            return;
        }

        String nationality = nationalityTextField.getText();
        if (nationality == null || nationality.trim().isEmpty()) {
            AlertUtils.showWarning("Hãy nhập quốc tịch");
            return;
        }

        String address = addressTextField.getText();
        if (address == null || address.trim().isEmpty()) {
            AlertUtils.showWarning("Hãy nhập địa chỉ");
            return;
        }

        customer.setName(name);
        customer.setSex(sexType);
        customer.setPhone(phone);
        customer.setCccd(idNumber);
        customer.setNationality(nationality);
        customer.setAddress(address);

        CustomerDAO.save(customer);

        customerTableController.loadData();
        closeWindow(e);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initSexTypeComboBox();
        if (customer.getId() != null) {
            initDefaultValues();
        }

        if (read_only) {
            initReadOnly();
        }
    }
}