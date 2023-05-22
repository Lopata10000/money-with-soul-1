package com.fanta.moneywithsoul.validator;

import java.lang.annotation.*;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.PastOrPresent;

/**
 * The interface Past or present date.
 */
@Documented
@Constraint(validatedBy = {PastOrPresentValidator.class})
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@PastOrPresent(message = "Дата не може бути в майбутньому")
@ReportAsSingleViolation
public @interface PastOrPresentDate {
    /**
     * Message string.
     *
     * @return the string
     */
    String message() default "Невалідна дата1";

    /**
     * Groups class [ ].
     *
     * @return the class [ ]
     */
    Class<?>[] groups() default {};

    /**
     * Payload class [ ].
     *
     * @return the class [ ]
     */
    Class<? extends Payload>[] payload() default {};
}
