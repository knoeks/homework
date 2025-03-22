package lt.bite.povilas.homework.controller;

import lombok.AllArgsConstructor;
import lt.bite.povilas.homework.model.User;
import lt.bite.povilas.homework.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
  private final UserService userService;

  // TODO: request UserRequest
  @PostMapping
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

}
