package com.fanta.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OnlyLettersValidator implements ConstraintValidator<OnlyLetters, String> {

    @Override
    public void initialize(OnlyLetters constraintAnnotation) {
        // Метод для ініціалізації валідатора (необов'язковий)
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Допускаємо null значення, можна налаштувати по-іншому за потребою
        }

        return value.matches("^[a-zA-Z]+$"); // Перевірка на наявність тільки букв
    }
}
