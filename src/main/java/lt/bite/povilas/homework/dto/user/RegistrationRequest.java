package lt.bite.povilas.homework.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Request body for user registration")
public record RegistrationRequest(
        @NotBlank(message = "Email must not be blank")
        @Email(message = "Email must be a valid email address")
        @Size(max = 100, message = "Email must not exceed 100 characters")
        String email,

        @Size(min = 8, max = 128, message = "Password must be between 8 and 128 characters")
        @Pattern(regexp = "^((?=.*[\\d])(?=.*[A-Z])(?=.*[^\\w\\d\\s])|(?=.*[\\d])(?=.*[a-z])(?=.*[^\\w\\d\\s])).+$",
                message = "Must contain at least one uppercase letter, one lowercase letter, one number and one special character")
        String password
) {
}
