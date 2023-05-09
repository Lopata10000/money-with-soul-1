module com.fanta.moneywithsoul1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires com.zaxxer.hikari;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires org.flywaydb.core;
    requires java.persistence;

    opens com.fanta.entity to org.hibernate.orm.core;
    opens com.fanta to javafx.fxml;
    exports com.fanta;
}