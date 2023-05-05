module com.client.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.rmi;
    exports com.lab3.DBModels;

    opens com.lab3 to javafx.fxml;
    opens com.lab3.DBModels to javafx.base;
    exports com.lab3;
}