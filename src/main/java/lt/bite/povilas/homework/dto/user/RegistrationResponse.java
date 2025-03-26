package lt.bite.povilas.homework.dto.user;

import lt.bite.povilas.homework.model.Role;

import java.time.LocalDateTime;
import java.util.Set;

public record RegistrationResponse(
        long id,
        String email,
        LocalDateTime registeredAt,
        Set<Role> roles
) {
}
