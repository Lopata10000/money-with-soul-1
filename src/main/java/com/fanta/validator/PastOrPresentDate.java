package com.fanta.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.PastOrPresent;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {PastOrPresentValidator.class})
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@PastOrPresent(message = "Дата не може бути в майбутньому")
@ReportAsSingleViolation
public @interface PastOrPresentDate {
    String message() default "Invalid date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}