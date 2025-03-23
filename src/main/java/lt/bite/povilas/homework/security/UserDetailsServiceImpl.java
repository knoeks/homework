package lt.bite.povilas.homework.security;

import lombok.AllArgsConstructor;
import lt.bite.povilas.homework.dto.UserDTO.UserMapper;
import lt.bite.povilas.homework.dto.UserDTO.UserResponse;
import lt.bite.povilas.homework.model.User;
import lt.bite.povilas.homework.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserService userService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userService.findUserByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
  }
}

