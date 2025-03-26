package lt.bite.povilas.homework.service;

import lombok.AllArgsConstructor;
import lt.bite.povilas.homework.dto.user.LoginRequest;
import lt.bite.povilas.homework.dto.user.RegistrationRequest;
import lt.bite.povilas.homework.dto.user.RegistrationResponse;
import lt.bite.povilas.homework.dto.user.UserMapper;
import lt.bite.povilas.homework.exception.auth.EmailAlreadyExistsException;
import lt.bite.povilas.homework.exception.auth.InvalidCredentialsException;
import lt.bite.povilas.homework.exception.user.RoleNotFoundException;
import lt.bite.povilas.homework.model.Role;
import lt.bite.povilas.homework.model.User;
import lt.bite.povilas.homework.repository.RoleRepository;
import lt.bite.povilas.homework.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;
  private final TokenService tokenService;
  private final UserMapper userMapper;

  public Optional<User> findUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  public RegistrationResponse saveUser(RegistrationRequest registrationRequest) {
    if (userRepository.existsByEmail(registrationRequest.email())) {
      throw new EmailAlreadyExistsException(registrationRequest.email());
    }

    User user = userMapper.toEntity(registrationRequest);

    user.setPassword(passwordEncoder.encode(registrationRequest.password()));
    Role role = roleRepository.findByName("USER")
            .orElseThrow(() -> new RoleNotFoundException("USER"));

    user.setRoles(Set.of(role));
    return userMapper.toResponse(userRepository.save(user));
  }

  public String loginUser(LoginRequest loginRequest) {
    User userFromDb = userRepository.findByEmail(loginRequest.email())
            .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password."));

    if (!passwordEncoder.matches(loginRequest.password(), userFromDb.getPassword())) {
      throw new InvalidCredentialsException("Invalid email or password");
    }

    return tokenService.generateToken(userFromDb);
  }
}
