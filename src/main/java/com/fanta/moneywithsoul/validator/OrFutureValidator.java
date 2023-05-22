package com.fanta.moneywithsoul.validator;

import java.sql.Timestamp;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * The type Or future validator.
 */
public class OrFutureValidator implements ConstraintValidator<OrFutureDate, Timestamp> {

    @Override
    public void initialize(OrFutureDate constraintAnnotation) {
        // Ініціалізація, якщо потрібно
    }

    @Override
    public boolean isValid(Timestamp value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Дозволяємо значенням null
        }
        Timestamp now = new Timestamp(System.currentTimeMillis());
        return value.after(now) || value.equals(now);
    }
}
