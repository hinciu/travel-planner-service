package com.test.travelplanner.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;

@Target({PARAMETER, FIELD, TYPE_USE})
@Constraint(validatedBy = {})
@Retention(RUNTIME)
@Pattern(regexp = "^(?<!\\d)(\\d{2}|\\d{4})(?!\\d)-(0[1-9]|1[012])-(0[1-9]|[12]\\d|3[01])$")
@ReportAsSingleViolation
public @interface ValidDateFormat {
  String message() default "{javax.validation.constraints.Pattern.message}";

  String regexp() default "^(?<!\\d)(\\d{2}|\\d{4})(?!\\d)-(0[1-9]|1[012])-(0[1-9]|[12]\\d|3[01])$";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
