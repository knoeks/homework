package lt.bite.povilas.homework.dto.taskDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lt.bite.povilas.homework.enums.TaskStatus;
import lt.bite.povilas.homework.validation.EnumValidator.TaskStatusValidator;

public record TaskPutRequest(
        @NotBlank(message = "Name must not be blank")
        @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
        String name,

        @Size(max = 500, message = "Description must not exceed 500 characters")
        String description,

        @TaskStatusValidator
        String status
) {

  // ahhhhh...
  public TaskStatus getStatusEnum() {
    return TaskStatus.valueOf(status);
  }
}
