package com.fanta.moneywithsoul.validator;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * The interface Date consistency.
 */
@Documented
@Constraint(validatedBy = DateConsistencyConstraintValidator.class)  // змінено тут
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface DateConsistency {
    /**
     * Message string.
     *
     * @return the string
     */
    String message() default "Початкова дата не може бути пізніше кінцевої ";

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

    /**
     * Start date string.
     *
     * @return the string
     */
    String startDate();

    /**
     * End date string.
     *
     * @return the string
     */
    String endDate();
}

