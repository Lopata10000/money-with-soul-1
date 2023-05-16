module com {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires com.zaxxer.hikari;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires org.flywaydb.core;
    requires java.persistence;
    requires java.validation;
    requires com.jfoenix;
    opens db.migration;
    opens com.fanta.moneywithsoul.entity;
    exports com.fanta.moneywithsoul.validator;
    exports com.fanta.moneywithsoul;
    opens com.fanta.moneywithsoul;
}
