package lt.bite.povilas.homework.service;

import lombok.AllArgsConstructor;
import lt.bite.povilas.homework.exception.EmailAlreadyExistsException;
import lt.bite.povilas.homework.exception.InvalidCredentialsException;
import lt.bite.povilas.homework.model.Role;
import lt.bite.povilas.homework.model.User;
import lt.bite.povilas.homework.repository.RoleRepository;
import lt.bite.povilas.homework.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;
  private final TokenService tokenService;


  // TODO: return UserResponse
  public Optional<User> findUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  // TODO: request UserRequest, return UserResponse
  public User saveUser(User user) {
    if (userRepository.existsByEmail(user.getEmail())) {
      throw new EmailAlreadyExistsException(user.getEmail());
    }

    user.setPassword(passwordEncoder.encode(user.getPassword()));
    Role role = roleRepository.findByName("USER").orElseThrow();
    user.setRoles(Set.of(role));
    return userRepository.save(user);
  }

  public String loginUser(User user) {
    Optional<User> userFromDb = userRepository.findByEmail(user.getEmail());

    if (userFromDb.isEmpty() || !passwordEncoder.matches(user.getPassword(), userFromDb.get().getPassword())) {
      throw new InvalidCredentialsException("Invalid email or password.");
    }

    return tokenService.generateToken(userFromDb.get());
  }

  public List<User> findAllUsers() {
    return userRepository.findAll();
  }
}
