module com.tr.ereceipt.ui.ereceipt {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;
    requires javafx.swing;
    requires java.sql.rowset;
    requires com.oracle.database.jdbc;

    opens com.tr.ereceipt.ui.ereceipt to javafx.fxml;
    exports com.tr.ereceipt.ui.ereceipt;
}