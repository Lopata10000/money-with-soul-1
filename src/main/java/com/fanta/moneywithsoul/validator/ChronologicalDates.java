package com.fanta.moneywithsoul.validator;

import java.lang.annotation.*;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * The interface Chronological dates.
 */
@Documented
@Constraint(validatedBy = ChronologicalDatesValidator.class)
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ChronologicalDates {
    /**
     * Message string.
     *
     * @return the string
     */
    String message() default "End date cannot be earlier than start date";

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

    /**
     * The interface List.
     */
    @Target({})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        /**
         * Value chronological dates [ ].
         *
         * @return the chronological dates [ ]
         */
        ChronologicalDates[] value();
    }
}
