package lt.bite.povilas.homework.dto.UserDTO;

import lt.bite.povilas.homework.enums.TaskStatus;
import lt.bite.povilas.homework.model.Role;
import lt.bite.povilas.homework.model.User;

import java.time.LocalDateTime;
import java.util.Set;

public record UserResponse(
        long id,
        String email,
        LocalDateTime registeredAt,
        Set<Role> roles
) {
}
