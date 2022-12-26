package com.eazybytes.eazyschool.annotation;


import com.eazybytes.eazyschool.validations.FieldsValueMatchValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = FieldsValueMatchValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldsValueMatch {

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String message() default "Fields values dont match";

    // buradan aşağıdaki kodu iki field'ı karşılaştırdığmıız için yazdık
    String field();
    String fieldMatch();

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface List{
        FieldsValueMatch[] value();
    }
}
