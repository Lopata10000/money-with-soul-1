package com.fanta.moneywithsoul.validator;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.sql.Timestamp;

/**
 * The type Date consistency constraint validator.
 */
public class DateConsistencyConstraintValidator implements ConstraintValidator<DateConsistency, Object> {

    private String startDateFieldName;
    private String endDateFieldName;

    @Override
    public void initialize(DateConsistency constraintAnnotation) {
        this.startDateFieldName = constraintAnnotation.startDate();
        this.endDateFieldName = constraintAnnotation.endDate();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        try {
            Class<?> clazz = object.getClass();
            Field startDateField = clazz.getDeclaredField(startDateFieldName);
            startDateField.setAccessible(true);
            Timestamp startDate = (Timestamp) startDateField.get(object);

            Field endDateField = clazz.getDeclaredField(endDateFieldName);
            endDateField.setAccessible(true);
            Timestamp endDate = (Timestamp) endDateField.get(object);

            if (endDate != null && startDate != null) {
                return !endDate.before(startDate);
            } else {
                return true;
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}



