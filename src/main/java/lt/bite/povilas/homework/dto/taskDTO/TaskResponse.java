package lt.bite.povilas.homework.dto.taskDTO;

import lt.bite.povilas.homework.dto.UserDTO.UserResponse;
import lt.bite.povilas.homework.enums.TaskStatus;
import lt.bite.povilas.homework.model.User;

import java.time.LocalDateTime;

public record TaskResponse(
        long id,
        String name,
        String description,
        UserResponse user,
        TaskStatus status,
        LocalDateTime createdAt
) {
}
