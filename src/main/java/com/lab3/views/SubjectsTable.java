package com.lab3.views;

import com.lab3.DBModels.Subject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class SubjectsTable {
    private TableView<Subject> table;

    public SubjectsTable() {
        table = new TableView<>();

        TableColumn<Subject, String> nameColumn = new TableColumn<>("Subject Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        table.getColumns().addAll(nameColumn);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setTableMenuButtonVisible(false);
    }

    public TableView<Subject> getTable() {
        return table;
    }

    public void setStudents(List<Subject> subjects) {
        ObservableList<Subject> observableList = FXCollections.observableArrayList(subjects);
        table.setItems(observableList);
    }
}
