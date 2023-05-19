package com.fanta.moneywithsoul.validator;

import java.lang.annotation.*;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.PastOrPresent;

@Documented
@Constraint(validatedBy = {PastOrPresentValidator.class})
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@PastOrPresent(message = "Дата не може бути в майбутньому")
@ReportAsSingleViolation
public @interface PastOrPresentDate {
    String message() default "Невалідна дата1";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
