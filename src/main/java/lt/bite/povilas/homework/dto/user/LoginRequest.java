package lt.bite.povilas.homework.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request body for user login")
public record LoginRequest(
        String email,
        String password
) {
}
