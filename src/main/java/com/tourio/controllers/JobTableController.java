package com.tourio.controllers;

import com.tourio.dao.GroupDAO;
import com.tourio.dao.JobDAO;
import com.tourio.models.Group;
import com.tourio.models.Job;
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

public class JobTableController extends BaseTableController<Tour> {
    ObservableList<Job> jobs = FXCollections.observableArrayList();

    @FXML
    private TableView<Job> table;

    @FXML
    private TableColumn<Job, String> jobNameColumn;

    public void onCreateClick(ActionEvent event) throws IOException {
        JobFormController controller = new JobFormController();
        controller.jobTableController = this;

        new StageBuilder("job_form", controller, "Thêm công việc")
                .setModalOwner(event)
                .setDimensionsAuto()
                .build()
                .showAndWait();
    }

    public void onEditClick(ActionEvent event) throws IOException {
        Job job = table.getSelectionModel().getSelectedItem();

        if (job == null) {
            AlertUtils.showWarning("Hãy chọn công việc cần sửa");
            return;
        }

        JobFormController controller = new JobFormController();
        controller.jobTableController = this;
        controller.job = job;

        new StageBuilder("job_form", controller, "Sửa công việc")
                .setModalOwner(event)
                .setDimensionsAuto()
                .build()
                .showAndWait();
    }

    public void onDeleteClick(ActionEvent event) {
        Job job = table.getSelectionModel().getSelectedItem();

        if (job == null) {
            AlertUtils.showWarning("Hãy chọn công việc cần xóa");
            return;
        }

        List<Group> groups = GroupDAO.getAll();
        long index = groups.stream()
                .filter(t -> t.getGroupEmployeeRels()
                        .stream()
                        .anyMatch(p -> p.getJob().getId() == job.getId()))
                .count();

        if (index != 0) {
            AlertUtils.showWarning("Không thể xóa. Đã có nhân viên làm công viêc này");
            return;
        }

        Alert alert = AlertUtils.alert(Alert.AlertType.CONFIRMATION, "Chắc chắn xóa");
        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.OK) {
            JobDAO.delete(job);
            loadData();
            return;
        }
        if (option.get() == ButtonType.CANCEL) {
            return;
        }
    }

    public void initTable() {
        // Job name column render
        jobNameColumn.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(data.getValue().getName());
            return property;
        });

        // Bind table with jobs observable list
        table.setItems(jobs);
    }

    public void loadData() {
        // Get all jobs and set to job observable list
        jobs.setAll(JobDAO.getAll());
        table.refresh();
    }
}