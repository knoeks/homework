package lt.bite.povilas.homework.controller;

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
  public ResponseEntity<UserResponse> addUser(@RequestBody UserRequest user) {

    System.out.println("testEmail:" + user.email());
    UserResponse savedUser = userService.saveUser(user);

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(savedUser.id())
                            .toUri()
            )
            .body(savedUser);
  }

  // TODO: request UserRequest
  @PostMapping("/login")
  public ResponseEntity<?> loginUser(@RequestBody UserRequest user) {
    String token = userService.loginUser(user);
    return ResponseEntity.ok()
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            .body(Map.of("success", true));
  }
}
