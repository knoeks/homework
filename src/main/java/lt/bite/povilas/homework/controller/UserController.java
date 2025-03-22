package lt.bite.povilas.homework.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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

  // TODO: request UserRequest
  @PostMapping("/register")
  public ResponseEntity<User> addUser(@RequestBody User user) {

    // TODO: make it return UserResponse
    User savedUser = userService.saveUser(user);

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(savedUser.getId())
                            .toUri()
            )
            .body(savedUser);
  }

  // TODO: request UserRequest
  @PostMapping("/login")
  public ResponseEntity<?> loginUser(@RequestBody User user) {
    String token = userService.loginUser(user);
    return ResponseEntity.ok()
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            .body(Map.of("success", true));
  }

  @GetMapping
  public ResponseEntity<List<User>> getUsers() {
    return ResponseEntity.ok(userService.findAllUsers());
  }

}
