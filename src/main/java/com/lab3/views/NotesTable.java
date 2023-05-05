package com.lab3.views;


import com.lab3.DBModels.SubjectScore;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class NotesTable {
    private TableView<SubjectScore> table;

    public NotesTable() {
        table = new TableView<>();

        TableColumn<SubjectScore, String> studentColumn = new TableColumn<>("Student");
        studentColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));

        TableColumn<SubjectScore, Integer> subjectColumn = new TableColumn<>("Subject");
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subjectName"));

        TableColumn<SubjectScore, Integer> noteColumn = new TableColumn<>("Note");
        noteColumn.setCellValueFactory(new PropertyValueFactory<>("note"));

        table.getColumns().addAll(studentColumn, subjectColumn, noteColumn);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setTableMenuButtonVisible(false);
    }

    public TableView<SubjectScore> getTable() {
        return table;
    }

    public void setStudents(List<SubjectScore> scores) {
        ObservableList<SubjectScore> observableList = FXCollections.observableArrayList(scores);
        table.setItems(observableList);
    }
}
