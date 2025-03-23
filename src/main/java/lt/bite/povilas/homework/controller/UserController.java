package lt.bite.povilas.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lt.bite.povilas.homework.dto.UserDTO.UserRequest;
import lt.bite.povilas.homework.dto.UserDTO.UserResponse;
import lt.bite.povilas.homework.model.User;
import lt.bite.povilas.homework.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
  private final UserService userService;

  @PostMapping("/register")
  @Operation(summary = "Register a new user", description = "Creates a new user account. No authentication required.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "User registered successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
          @ApiResponse(responseCode = "400", description = "Invalid user data", content = @Content),
          @ApiResponse(responseCode = "409", description = "Email already exists", content = @Content)
  })
  public ResponseEntity<UserResponse> addUser(@Valid @RequestBody UserRequest user) {

    UserResponse savedUser = userService.saveUser(user);

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(savedUser.id())
                            .toUri()
            )
            .body(savedUser);
  }

  @PostMapping("/login")
  public ResponseEntity<?> loginUser(@Valid @RequestBody UserRequest user) {
    String token = userService.loginUser(user);
    return ResponseEntity.ok()
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            .body(Map.of("success", true));
  }
}
