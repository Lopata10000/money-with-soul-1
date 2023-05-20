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
    opens com.fanta.moneywithsoul.controller.tablecontroller to javafx.fxml;
    exports com.fanta.moneywithsoul.controller.tablecontroller;
    exports com.fanta.moneywithsoul.validator;
    exports com.fanta.moneywithsoul;

    opens com.fanta.moneywithsoul;

    exports com.fanta.moneywithsoul.database;

    opens com.fanta.moneywithsoul.database;

    exports com.fanta.moneywithsoul.controller;

    opens com.fanta.moneywithsoul.controller;
}
