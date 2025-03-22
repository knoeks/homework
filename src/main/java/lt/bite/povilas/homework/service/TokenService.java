package lt.bite.povilas.homework.service;

import lombok.AllArgsConstructor;
import lt.bite.povilas.homework.model.Role;
import lt.bite.povilas.homework.model.User;
import lt.bite.povilas.homework.repository.UserRepository;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TokenService {
  private final JwtEncoder jwtEncoder;
  private final UserRepository userRepository;

  public String generateToken(User user) {
    Instant now = Instant.now();

    long expiry = 360000L;

    String scope = user.getRoles().stream().map(Role::getName).collect(Collectors.joining(" "));

    JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer("self")
            .issuedAt(now)
            .expiresAt(now.plusSeconds(expiry))
            .subject(user.getUsername())
            .claim("scope", scope)
            .claim("userId", user.getId())
            .build();

    return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
  }
}
