package com.fanta.moneywithsoul.validator;

import java.sql.Timestamp;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


/**
 * The type Past or present validator.
 */
public class PastOrPresentValidator implements ConstraintValidator<PastOrPresentDate, Timestamp> {

    @Override
    public void initialize(PastOrPresentDate constraintAnnotation) {
        // Ініціалізація, якщо потрібно
    }

    @Override
    public boolean isValid(Timestamp value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Дозволяємо значенням null
        }
        Timestamp now = new Timestamp(System.currentTimeMillis());
        return value.before(now) || value.equals(now);
    }
}
