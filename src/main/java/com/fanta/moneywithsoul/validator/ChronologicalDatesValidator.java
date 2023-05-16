package com.fanta.moneywithsoul.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.time.LocalDate;

public class ChronologicalDatesValidator implements ConstraintValidator<ChronologicalDates, Object> {
    private String startDateFieldName;
    private String endDateFieldName;

    @Override
    public void initialize(ChronologicalDates constraintAnnotation) {
        startDateFieldName = constraintAnnotation.startDate();
        endDateFieldName = constraintAnnotation.endDate();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            Class<?> clazz = value.getClass();
            Field startDateField = clazz.getDeclaredField(startDateFieldName);
            Field endDateField = clazz.getDeclaredField(endDateFieldName);
            startDateField.setAccessible(true);
            endDateField.setAccessible(true);

            LocalDate startDate = (LocalDate) startDateField.get(value);
            LocalDate endDate = (LocalDate) endDateField.get(value);

            return endDate == null || startDate == null || !endDate.isBefore(startDate);
        } catch (Exception e) {
            return false;
        }
    }
}
