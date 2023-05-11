package com.fanta.service;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public interface ServiceInterface<T> {
    T getById(Long id);
    List<T> getAll();
    void save(T entity);
    void update(T entity);
    void delete(T entity);

    default void validateAndSave(T object) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            // Обробка помилок валідації, наприклад, виведення повідомлень про помилки
            for (ConstraintViolation<T> violation : violations) {
                System.out.println(violation.getPropertyPath() + ": " + violation.getMessage());
            }
            // Повернення null або виключення, в залежності від вашої логіки обробки помилок
        }
    }
    default void validateAndUpdate(T object) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            // Обробка помилок валідації, наприклад, виведення повідомлень про помилки
            for (ConstraintViolation<T> violation : violations) {
                System.out.println(violation.getPropertyPath() + ": " + violation.getMessage());
            }
            // Повернення null або виключення, в залежності від вашої логіки обробки помилок
        } else {
            // Логіка для оновлення об'єкта, наприклад, виклик методу update()
            update(object);
        }
    }
}

