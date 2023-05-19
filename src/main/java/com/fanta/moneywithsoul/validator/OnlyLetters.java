package com.fanta.moneywithsoul.validator;

import java.lang.annotation.*;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = OnlyLettersValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface OnlyLetters {

    String message() default
            "Уведено не правильне значення. Використовуйте тільки букви будь ласка.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
