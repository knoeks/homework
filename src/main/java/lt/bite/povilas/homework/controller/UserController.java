package lt.bite.povilas.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lt.bite.povilas.homework.dto.user.LoginRequest;
import lt.bite.povilas.homework.dto.user.LoginResponse;
import lt.bite.povilas.homework.dto.user.RegistrationRequest;
import lt.bite.povilas.homework.dto.user.RegistrationResponse;
import lt.bite.povilas.homework.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
  private final UserService userService;

  @PostMapping("/register")
  @Operation(
          summary = "Register a new user",
          description = "Creates a new user account. No authentication required.")
  @ApiResponses(value = {
          @ApiResponse(
                  responseCode = "201",
                  description = "User registered successfully",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = RegistrationResponse.class))),
          @ApiResponse(
                  responseCode = "400",
                  description = "Invalid user data",
                  content = @Content),
          @ApiResponse(
                  responseCode = "409",
                  description = "Email already exists",
                  content = @Content)
  })
  @ResponseStatus(HttpStatus.CREATED)
  public RegistrationResponse addUser(
          @Valid @RequestBody RegistrationRequest user,
          HttpServletResponse response) {

    RegistrationResponse savedUser = userService.saveUser(user);

    response.setHeader(HttpHeaders.LOCATION,
            ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedUser.id())
                    .toUri()
                    .toString());

    return savedUser;
  }

  @ResponseStatus(HttpStatus.OK)
  @PostMapping("/login")
  public LoginResponse loginUser(
          @Valid @RequestBody LoginRequest user,
          HttpServletResponse response) {

    String token = userService.loginUser(user);

    response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);

    return new LoginResponse(true);
  }
}
