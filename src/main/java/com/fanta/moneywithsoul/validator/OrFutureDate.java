package com.fanta.moneywithsoul.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;


@Documented
@Constraint(validatedBy = OrFutureValidator.class)
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface OrFutureDate {
    
    String message() default "Невалідна дата";

    
    Class<?>[] groups() default {};

    
    Class<? extends Payload>[] payload() default {};
}
