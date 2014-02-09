package com.domain.constraints;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.domain.constraints.impl.ReturnAfterOriginValidator;

@Target({java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.ANNOTATION_TYPE})
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ReturnAfterOriginValidator.class)
public @interface ReturnAfterOrigin
{
    String message() default "{com.domain.constraints.returnafterorigin}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
