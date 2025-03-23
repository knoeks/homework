package lt.bite.povilas.homework.service;

import lombok.AllArgsConstructor;
import lt.bite.povilas.homework.dto.UserDTO.UserMapper;
import lt.bite.povilas.homework.dto.UserDTO.UserRequest;
import lt.bite.povilas.homework.dto.UserDTO.UserResponse;
import lt.bite.povilas.homework.exception.EmailAlreadyExistsException;
import lt.bite.povilas.homework.exception.InvalidCredentialsException;
import lt.bite.povilas.homework.exception.RoleNotFoundException;
import lt.bite.povilas.homework.model.Role;
import lt.bite.povilas.homework.model.User;
import lt.bite.povilas.homework.repository.RoleRepository;
import lt.bite.povilas.homework.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
  private final UserMapper userMapper;

  public Optional<User> findUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  public UserResponse saveUser(UserRequest userRequest) {
    if (userRepository.existsByEmail(userRequest.email())) {
      throw new EmailAlreadyExistsException(userRequest.email());
    }

    User user = userMapper.toEntity(userRequest);

    user.setPassword(passwordEncoder.encode(userRequest.password()));
    Role role = roleRepository.findByName("USER")
            .orElseThrow(() -> new RoleNotFoundException("USER"));

    user.setRoles(Set.of(role));
    return userMapper.toResponse(userRepository.save(user));
  }

  public String loginUser(UserRequest userRequest) {
    User userFromDb = userRepository.findByEmail(userRequest.email())
            .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password."));

    if (!passwordEncoder.matches(userRequest.password(), userFromDb.getPassword())) {
      throw new InvalidCredentialsException("Invalid email or password");
    }

    return tokenService.generateToken(userFromDb);
  }
}
