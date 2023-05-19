package com.fanta.moneywithsoul.validator;

import java.lang.annotation.*;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = ChronologicalDatesValidator.class)
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ChronologicalDates {
    String message() default "End date cannot be earlier than start date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String startDate();

    String endDate();

    @Target({})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        ChronologicalDates[] value();
    }
}
