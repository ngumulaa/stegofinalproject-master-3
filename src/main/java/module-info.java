module edu.guilford {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.commons.io;
    requires java.desktop;
    requires java.base;
    requires java.logging;
    requires java.sql;
    requires java.xml;
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.swing;

    opens edu.guilford to javafx.fxml;
    exports edu.guilford;
}
