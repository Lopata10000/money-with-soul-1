package com.fanta.moneywithsoul.service;

import java.util.List;
import java.util.Set;
import javafx.scene.control.Alert;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public interface ServiceInterface<T> {
    T getById(Long id);

    List<T> getAll();

    void save(T entity);

    void update(Long id, T entity);

    void delete(Long id);

    default void validateAndSave(T object) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder();
            for (ConstraintViolation<T> violation : violations) {
                errorMessage.append(violation.getPropertyPath()).append(": ").append(violation.getMessage()).append("\n");
            }
            showErrorMessage(errorMessage.toString());
            throw new RuntimeException();
        }
    }

    default void validateAndUpdate(Long id, T object) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder();
            for (ConstraintViolation<T> violation : violations) {
                errorMessage
                        .append(violation.getPropertyPath())
                        .append(": ")
                        .append(violation.getMessage())
                        .append("\n");
            }
            showErrorMessage(errorMessage.toString());
            throw new RuntimeException();
        }
    }

    default void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Помилка валідації");
        alert.setHeaderText("Будь ласка, виправте наступні помилки:");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
