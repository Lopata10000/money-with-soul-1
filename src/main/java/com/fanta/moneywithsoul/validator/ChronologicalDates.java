package com.fanta.moneywithsoul.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

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
