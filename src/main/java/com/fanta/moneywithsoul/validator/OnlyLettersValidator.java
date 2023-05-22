package com.fanta.moneywithsoul.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * The type Only letters validator.
 */
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

        return value.matches("^[a-zA-Zа-щА-Щью-яЮ-ЯіІїЇєЄґҐ\\s]+$");
    }
}
