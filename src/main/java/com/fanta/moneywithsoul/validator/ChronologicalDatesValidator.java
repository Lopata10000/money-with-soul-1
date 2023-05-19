package com.fanta.moneywithsoul.validator;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.LocalDate;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ChronologicalDatesValidator
        implements ConstraintValidator<ChronologicalDates, Object> {
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

            Timestamp startDate = (Timestamp) startDateField.get(value);
            Timestamp endDate = (Timestamp) endDateField.get(value);

            return endDate == null || startDate == null || endDate.after(startDate);
        } catch (Exception e) {
            return false;
        }
    }
}
