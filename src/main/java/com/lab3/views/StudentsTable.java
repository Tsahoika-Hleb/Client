package com.lab3.views;


import com.lab3.DBModels.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class StudentsTable {
    private TableView<Student> table;

    public StudentsTable() {
        table = new TableView<>();


        TableColumn<Student, String> lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<Student, Integer> groupNumberColumn = new TableColumn<>("Group Number");
        groupNumberColumn.setCellValueFactory(new PropertyValueFactory<>("groupNumber"));

        table.getColumns().addAll(lastNameColumn, groupNumberColumn);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setTableMenuButtonVisible(false);
    }

    public TableView<Student> getTable() {
        return table;
    }

    public void setStudents(List<Student> students) {
        ObservableList<Student> observableList = FXCollections.observableArrayList(students);
        table.setItems(observableList);
    }
}
