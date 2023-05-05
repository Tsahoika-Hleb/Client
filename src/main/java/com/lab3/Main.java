package com.lab3;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lab3.DBModels.Student;
import com.lab3.DBModels.Subject;
import com.lab3.DBModels.SubjectScore;
import com.lab3.views.NotesTable;
import com.lab3.views.StudentsTable;
import com.lab3.views.SubjectsTable;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {
    private Stage stage;
    private Scene homeScene;
    private Scene studentsScene;
    private Scene subjectsScene;
    private Scene notesScene;

    private DatabaseManagerInterface databaseManager;

    public static final String UNIQUE_BINDING_NAME = "StudentService";

    public Main() throws SQLException, NotBoundException, RemoteException {
        final Registry registry = LocateRegistry.getRegistry(7788);
        databaseManager = (DatabaseManagerInterface) registry.lookup(UNIQUE_BINDING_NAME);
    }

    public static void main(String[] args) throws SQLException {
        launch(args);
    }

    public void start(Stage primaryStage) throws SQLException, RemoteException, NotBoundException {
        this.stage = primaryStage;
        this.stage.setTitle("StudentsDatabase");

        this.setHomeScene();

        this.stage.setScene(this.homeScene);
        this.stage.show();
    }

    private void setHomeScene() {
        Button studentsButton = new Button("Students");
        studentsButton.setOnAction((e) -> {
            try {
                this.setStudentsScene();
            } catch (SQLException | RemoteException var3) {
                var3.printStackTrace();
            }

            this.stage.setScene(this.studentsScene);
        });
        Button subjectsButton = new Button("Subjects");
        subjectsButton.setOnAction((e) -> {
            try {
                this.setSubjectsScene();
            } catch (SQLException | RemoteException var3) {
                var3.printStackTrace();
            }

            this.stage.setScene(this.subjectsScene);
        });
        Button notesButton = new Button("Notes");
        notesButton.setOnAction((e) -> {
            try {
                this.setNotesScene();
            } catch (SQLException | RemoteException var3) {
                var3.printStackTrace();
            }

            this.stage.setScene(this.notesScene);
        });
        GridPane homeGrid = new GridPane();
        homeGrid.setPadding(new Insets(100.0D, 10.0D, 10.0D, 100.0D));
        homeGrid.setVgap(1.0D);
        homeGrid.setHgap(3.0D);
        homeGrid.add(studentsButton, 0, 0);
        homeGrid.add(subjectsButton, 0, 1);
        homeGrid.add(notesButton, 0, 2);
        GridPane.setValignment(studentsButton, VPos.CENTER);
        GridPane.setValignment(subjectsButton, VPos.CENTER);
        GridPane.setValignment(notesButton, VPos.CENTER);
        GridPane.setHalignment(studentsButton, HPos.CENTER);
        GridPane.setHalignment(subjectsButton, HPos.CENTER);
        GridPane.setHalignment(notesButton, HPos.CENTER);
        this.homeScene = new Scene(homeGrid, 270.0D, 300.0D);
    }

    private void setStudentsScene() throws SQLException, RemoteException {
        StudentsTable studentsTable = new StudentsTable();
        List<Student> students = this.databaseManager.getAllStudents();
        studentsTable.setStudents(students);
        GridPane studentsGrid = new GridPane();
        studentsGrid.setPadding(new Insets(10.0D, 10.0D, 10.0D, 10.0D));
        studentsGrid.setVgap(5.0D);
        studentsGrid.setHgap(6.0D);
        Button backButton1 = new Button("Back");
        backButton1.setOnAction((e) -> {
            this.stage.setScene(this.homeScene);
        });
        Label nameLabel = new Label("Enter student name:");
        TextField studentNameTF = new TextField();
        Label groupNumberLabel = new Label("Enter group number:");
        TextField groupNumberTF = new TextField();
        Button addStudentButton = new Button("Add student");
        addStudentButton.setOnAction((event) -> {
            String name = studentNameTF.getText();
            String groupNumber = groupNumberTF.getText();
            if (!name.isEmpty() && !groupNumber.isEmpty()) {
                try {
                    this.databaseManager.addStudent(new Student(name, Integer.parseInt(groupNumber)));
                    studentsTable.setStudents(this.databaseManager.getAllStudents());
                    studentNameTF.setText("");
                    groupNumberTF.setText("");
                } catch (SQLException | RemoteException var8) {
                    var8.printStackTrace();
                }

            }
        });
        Button deleteStudent = new Button("Delete student");
        deleteStudent.setOnAction((event) -> {
            String name = studentNameTF.getText();
            if (!name.isEmpty()) {
                try {
                    this.databaseManager.deleteStudentByName(name);
                    studentsTable.setStudents(this.databaseManager.getAllStudents());
                    studentNameTF.setText("");
                    groupNumberTF.setText("");
                } catch (SQLException | RemoteException var7) {
                    var7.printStackTrace();
                }

            }
        });
        studentsGrid.add(backButton1, 0, 0);
        studentsGrid.add(studentsTable.getTable(), 0, 1);
        studentsGrid.add(nameLabel, 0, 2);
        studentsGrid.add(studentNameTF, 1, 2);
        studentsGrid.add(groupNumberLabel, 0, 3);
        studentsGrid.add(groupNumberTF, 1, 3);
        studentsGrid.add(addStudentButton, 0, 4);
        studentsGrid.add(deleteStudent, 0, 5);
        this.studentsScene = new Scene(studentsGrid, 400.0D, 400.0D);
    }

    private void setSubjectsScene() throws SQLException, RemoteException {
        SubjectsTable subjectsTable = new SubjectsTable();
        List<Subject> subjects = this.databaseManager.getAllSubjects();
        subjectsTable.setStudents(subjects);
        GridPane subjectsGrid = new GridPane();
        subjectsGrid.setPadding(new Insets(10.0D, 10.0D, 10.0D, 10.0D));
        subjectsGrid.setVgap(5.0D);
        subjectsGrid.setHgap(5.0D);
        Button backButton = new Button("Back");
        backButton.setOnAction((e) -> {
            this.stage.setScene(this.homeScene);
        });
        Label subjectLabel = new Label("Enter subject name:");
        TextField subjectTF = new TextField();
        Button addSubjectButton = new Button("Add subject");
        addSubjectButton.setOnAction((event) -> {
            String name = subjectTF.getText();
            if (!name.isEmpty()) {
                try {
                    this.databaseManager.addSubject(new Subject(name));
                    subjectsTable.setStudents(this.databaseManager.getAllSubjects());
                    subjectTF.setText("");
                } catch (SQLException | RemoteException var6) {
                    var6.printStackTrace();
                }

            }
        });
        Button deleteSubjectButton = new Button("Delete subject");
        deleteSubjectButton.setOnAction((event) -> {
            String name = subjectTF.getText();
            if (!name.isEmpty()) {
                try {
                    this.databaseManager.deleteSubjectByName(name);
                    subjectsTable.setStudents(this.databaseManager.getAllSubjects());
                    subjectTF.setText("");
                } catch (SQLException | RemoteException var6) {
                    var6.printStackTrace();
                }

            }
        });
        subjectsGrid.add(backButton, 0, 0);
        subjectsGrid.add(subjectsTable.getTable(), 0, 1);
        subjectsGrid.add(subjectLabel, 0, 2);
        subjectsGrid.add(subjectTF, 1, 2);
        subjectsGrid.add(addSubjectButton, 0, 4);
        subjectsGrid.add(deleteSubjectButton, 0, 5);
        this.subjectsScene = new Scene(subjectsGrid, 400.0D, 400.0D);
    }

    private void setNotesScene() throws SQLException, RemoteException {
        GridPane notesGrid = new GridPane();
        notesGrid.setPadding(new Insets(10.0D, 10.0D, 10.0D, 10.0D));
        notesGrid.setVgap(4.0D);
        notesGrid.setHgap(4.0D);

        Button backButton = new Button("Back");
        backButton.setOnAction((e) -> {
            this.stage.setScene(this.homeScene);
        });

        NotesTable notesTable = new NotesTable();
        List<SubjectScore> scores = this.databaseManager.getAllScores();
        notesTable.setStudents(scores);

        ChoiceBox<String> scoresFilter = new ChoiceBox<>();
        scoresFilter.getItems().addAll("all", "debtors");
        scoresFilter.setValue("all");
        scoresFilter.setOnAction((event) -> {
            String selectedOption = (String) scoresFilter.getValue();
            byte var6 = -1;
            switch (selectedOption.hashCode()) {
                case 96673:
                    if (selectedOption.equals("all")) {
                        var6 = 0;
                    }
                    break;
                case 1541670269:
                    if (selectedOption.equals("debtors")) {
                        var6 = 1;
                    }
            }

            switch (var6) {
                case 0:
                    try {
                        notesTable.setStudents(this.databaseManager.getAllScores());
                    } catch (SQLException | RemoteException var9) {
                        var9.printStackTrace();
                    }
                    break;
                case 1:
                    try {
                        notesTable.setStudents(this.databaseManager.getDebtorsScores());
                    } catch (SQLException | RemoteException var8) {
                        var8.printStackTrace();
                    }
            }

        });

        Label chooseStudentLabel = new Label("Choose student");
        ChoiceBox<String> studentsChoiceBox = this.createStudentsChoiceBox();
        GridPane.setValignment(studentsChoiceBox, VPos.TOP);

        Label chooseSubjectLabel = new Label("Choose subject");
        ChoiceBox<String> subjectChoiceBox = this.createSubjectChoiceBox();
        GridPane.setValignment(subjectChoiceBox, VPos.TOP);

        Label noteLabel = new Label("Note");
        TextField noteTF = new TextField();
        noteTF.setPrefWidth(30.0D);
        GridPane.setValignment(noteTF, VPos.TOP);

        Button addScoreButton = new Button("Add Note");
        addScoreButton.setOnAction((event) -> {
            String student = (String) studentsChoiceBox.getValue();
            String subject = (String) subjectChoiceBox.getValue();
            String note = noteTF.getText();
            if (!student.isEmpty() && !subject.isEmpty() && !note.isEmpty()) {
                int studentId = -1;
                int subjectId = -1;

                try {
                    studentId = this.databaseManager.getStudentByName(student).getId();
                    subjectId = this.databaseManager.getSubjectByName(subject).getId();
                } catch (SQLException | RemoteException var14) {
                    var14.printStackTrace();
                }

                if (studentId != -1 && subjectId != -1) {
                    SubjectScore score = new SubjectScore(studentId, subjectId, Integer.parseInt(note));

                    try {
                        this.databaseManager.addSubjectScore(score);
                        notesTable.setStudents(this.databaseManager.getAllScores());
                    } catch (SQLException | RemoteException var13) {
                        var13.printStackTrace();
                    }

                }
            }
        });

        Button deleteScoreButton = new Button("Delete note");
        deleteScoreButton.setOnAction((event) -> {
            String student = (String) studentsChoiceBox.getValue();
            String subject = (String) subjectChoiceBox.getValue();
            if (!student.isEmpty() && !subject.isEmpty()) {
                try {
                    this.databaseManager.deleteScore(student, subject);
                    notesTable.setStudents(this.databaseManager.getAllScores());
                } catch (SQLException | RemoteException var8) {
                    var8.printStackTrace();
                }

            }
        });

        Button debtorsExcludeButton = new Button("Exclude all debtors");
        debtorsExcludeButton.setOnAction((event) -> {
            try {
                this.databaseManager.deleteDebtors();
                notesTable.setStudents(this.databaseManager.getAllScores());
            } catch (SQLException | RemoteException var4) {
                var4.printStackTrace();
            }

        });

        notesGrid.add(backButton, 0, 0);
        notesGrid.add(notesTable.getTable(), 0, 1);
        notesGrid.add(scoresFilter, 0, 2);
        notesGrid.add(chooseStudentLabel, 1, 0);
        notesGrid.add(studentsChoiceBox, 1, 1);
        notesGrid.add(chooseSubjectLabel, 2, 0);
        notesGrid.add(subjectChoiceBox, 2, 1);
        notesGrid.add(noteLabel, 3, 0);
        notesGrid.add(noteTF, 3, 1);
        notesGrid.add(addScoreButton, 1, 2);
        notesGrid.add(deleteScoreButton, 2, 2);
        notesGrid.add(debtorsExcludeButton, 0, 3);

        this.notesScene = new Scene(notesGrid, 540.0D, 300.0D);
    }

    public ChoiceBox<String> createStudentsChoiceBox() throws SQLException, RemoteException {
        List<Student> students = this.databaseManager.getAllStudents();
        List<String> studentNames = new ArrayList<>();

        for (Student student : students) {
            studentNames.add(student.getLastName());
        }

        ChoiceBox<String> studentsChoiceBox = new ChoiceBox(FXCollections.observableList(studentNames));
        return studentsChoiceBox;
    }

    public ChoiceBox<String> createSubjectChoiceBox() throws SQLException, RemoteException {
        List<Subject> subjects = this.databaseManager.getAllSubjects();
        List<String> studentNames = new ArrayList<>();

        for (Subject subject : subjects) {
            studentNames.add(subject.getName());
        }

        ChoiceBox<String> studentsChoiceBox = new ChoiceBox(FXCollections.observableList(studentNames));
        return studentsChoiceBox;
    }
}