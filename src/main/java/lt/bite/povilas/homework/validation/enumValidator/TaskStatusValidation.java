package lt.bite.povilas.homework.validation.enumValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lt.bite.povilas.homework.enums.TaskStatus;

import java.util.Set;
import java.util.stream.Collectors;

public class TaskStatusValidation implements ConstraintValidator<TaskStatusValidator, String> {
  private Set<String> validValues;

  @Override
  public void initialize(TaskStatusValidator constraintAnnotation) {
    validValues = Set.of(TaskStatus.class.getEnumConstants())
            .stream()
            .map(Enum::name)
            .collect(Collectors.toSet());
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null || value.trim().isEmpty()) {
      return false;
    }
    return validValues.contains(value.toUpperCase());
  }
}
