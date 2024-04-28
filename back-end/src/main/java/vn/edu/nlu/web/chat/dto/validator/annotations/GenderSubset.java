package vn.edu.nlu.web.chat.dto.validator.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import vn.edu.nlu.web.chat.dto.validator.GenderSubSetValidator;
import vn.edu.nlu.web.chat.enums.Gender;

import java.lang.annotation.*;


@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GenderSubSetValidator.class)
public @interface GenderSubset {
    Gender[] anyOf();
    String message() default "must be any of {anyOf}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}