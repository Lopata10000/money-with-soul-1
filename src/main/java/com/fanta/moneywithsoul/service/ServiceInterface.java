package com.fanta.moneywithsoul.service;

import java.util.List;
import java.util.Set;
import javafx.scene.control.Alert;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * The interface Service interface.
 *
 * @param <T> the type parameter
 */
public interface ServiceInterface<T> {
    /**
     * Gets by id.
     *
     * @param id the id
     * @return the by id
     */
    T getById(Long id);

    /**
     * Gets all.
     *
     * @return the all
     */
    List<T> getAll();

    /**
     * Save.
     *
     * @param entity the entity
     */
    void save(T entity);

    /**
     * Update.
     *
     * @param id     the id
     * @param entity the entity
     */
    void update(Long id, T entity);

    /**
     * Delete.
     *
     * @param id the id
     */
    void delete(Long id);

    /**
     * Validate and save.
     *
     * @param object the object
     */
    default void validateAndSave(T object) {
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

    /**
     * Validate and update.
     *
     * @param id     the id
     * @param object the object
     */
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

    /**
     * Show error message.
     *
     * @param message the message
     */
    default void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Помилка валідації");
        alert.setHeaderText("Будь ласка, виправте наступні помилки:");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
