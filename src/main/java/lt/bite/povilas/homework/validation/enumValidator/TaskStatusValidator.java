package lt.bite.povilas.homework.validation.EnumValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = TaskStatusValidation.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface TaskStatusValidator {
  String message() default "Invalid TaskStatus value";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}