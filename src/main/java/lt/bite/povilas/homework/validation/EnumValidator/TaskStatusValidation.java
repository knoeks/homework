package lt.bite.povilas.homework.validation.EnumValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lt.bite.povilas.homework.enums.TaskStatus;

import java.util.Set;
import java.util.stream.Collectors;

public class TaskStatusValidation implements ConstraintValidator<TaskStatusValidator, TaskStatus> {
  private Set<String> validValues;

  @Override
  public void initialize(TaskStatusValidator constraintAnnotation) {
    validValues = Set.of(TaskStatus.class.getEnumConstants())
            .stream()
            .map(Enum::name)
            .collect(Collectors.toSet());
  }

  @Override
  public boolean isValid(TaskStatus taskStatus, ConstraintValidatorContext context) {
    if (taskStatus == null) {
      return false;
    }
    return validValues.contains(taskStatus.name());
  }
}
