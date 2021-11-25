package com.tourio.controllers;

import com.tourio.dao.CostTypeDAO;
import com.tourio.dao.GroupDAO;
import com.tourio.models.CostType;
import com.tourio.models.Group;
import com.tourio.models.Tour;
import com.tourio.utils.AlertUtils;
import com.tourio.utils.StageBuilder;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class CostTypeTableController extends BaseTableController<Tour> {
    ObservableList<CostType> costTypes = FXCollections.observableArrayList();

    @FXML
    private TableView<CostType> table;

    @FXML
    private TableColumn<CostType, String> costTypeNameColumn;

    public void onCreateClick(ActionEvent event) throws IOException {
        CostTypeFormController controller = new CostTypeFormController();
        controller.costTypeTableController = this;

        new StageBuilder("cost_type_form", controller, "Thêm loại phí")
                .setModalOwner(event)
                .setDimensionsAuto()
                .build()
                .showAndWait();
    }

    public void onEditClick(ActionEvent event) throws IOException {
        CostType costType = table.getSelectionModel().getSelectedItem();

        if (costType == null) {
            AlertUtils.showWarning("Hãy chọn loại phí cần sửa");
            return;
        }

        CostTypeFormController controller = new CostTypeFormController();
        controller.costTypeTableController = this;
        controller.costType = costType;

        new StageBuilder("cost_type_form", controller, "Sửa loại phí")
                .setModalOwner(event)
                .setDimensionsAuto()
                .build()
                .showAndWait();
    }

    public void onDeleteClick(ActionEvent event) {
        CostType costType = table.getSelectionModel().getSelectedItem();

        if (costType == null) {
            AlertUtils.showWarning("Hãy chọn loại phí cần xóa");
            return;
        }

        List<Group> groups = GroupDAO.getAll();
        long index = groups.stream()
                .filter(t -> t.getGroupCostRels()
                        .stream()
                        .anyMatch(p -> p.getCostType().getId() == costType.getId()))
                .count();

        if (index != 0) {
            AlertUtils.showWarning("Không thể xóa. Đã có đoàn khách chọn loại phí này này");
            return;
        }

        Alert alert = AlertUtils.alert(Alert.AlertType.CONFIRMATION, "Chắc chắn xóa");
        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.OK) {
            CostTypeDAO.delete(costType);
            loadData();
            return;
        }
        if (option.get() == ButtonType.CANCEL) {
            return;
        }
    }

    public void initTable() {
        // CostType name column render
        costTypeNameColumn.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(data.getValue().getName());
            return property;
        });

        // Bind table with costTypes observable list
        table.setItems(costTypes);
    }

    public void loadData() {
        // Get all costTypes and set to costType observable list
        costTypes.setAll(CostTypeDAO.getAll());
        table.refresh();
    }
}