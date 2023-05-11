module com.fanta.moneywithsoul1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires com.zaxxer.hikari;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires org.flywaydb.core;
    requires java.persistence;
    requires java.validation;
    opens db.migration;
    opens com.fanta.entity;
    opens com.fanta;

    exports com.fanta.moneywithsoul;
    exports com.fanta.validator;
    opens com.fanta.moneywithsoul to javafx.fxml;
}
