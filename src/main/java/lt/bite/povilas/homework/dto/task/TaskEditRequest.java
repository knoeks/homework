package lt.bite.povilas.homework.dto.task;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lt.bite.povilas.homework.enums.TaskStatus;
import lt.bite.povilas.homework.validation.EnumValidator.TaskStatusValidator;

public record TaskEditRequest(
        @NotBlank(message = "Name must not be blank")
        @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
        String name,

        @Size(max = 500, message = "Description must not exceed 500 characters")
        String description,

        @TaskStatusValidator
        @Schema(example = "NOT_STARTED", allowableValues = {"NOT_STARTED", "IN_PROGRESS", "COMPLETED"})
        String status
) {

  // ahhhhh...
  @JsonIgnore
  public TaskStatus getStatusEnum() {
    return TaskStatus.valueOf(status.toUpperCase());
  }
}
