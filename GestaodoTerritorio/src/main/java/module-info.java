module grupo.n.gestaodoterritorio {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires jts.core;
    requires com.fasterxml.jackson.databind;
    requires org.apache.commons.csv;
    requires org.jgrapht.core;

    opens grupo.n.gestaodoterritorio to javafx.fxml;
    exports grupo.n.gestaodoterritorio;
}