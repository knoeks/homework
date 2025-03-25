package lt.bite.povilas.homework.dto.task;

import lt.bite.povilas.homework.dto.user.UserResponse;
import lt.bite.povilas.homework.enums.TaskStatus;

import java.time.LocalDateTime;

public record TaskResponse(
        long id,
        String name,
        String description,
        TaskStatus status,
        LocalDateTime createdAt
) {
}
