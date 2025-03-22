package lt.bite.povilas.homework.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 100)
  private String email;

  @Column(nullable = false, unique = true, length = 128)
  private String password;

  @Column(nullable = false, name = "registered_at")
  private LocalDateTime registeredAt;

  @PrePersist
  public void prePersist() {
    if (this.registeredAt == null) {
      this.registeredAt = LocalDateTime.now();
    }
  }

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
          name = "users_roles",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  private Set<Role> roles;

  // TODO: make connection to tasks

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles.stream().map(item -> new SimpleGrantedAuthority("ROLE_" + item.getName())).toList();
  }

  @Override
  public String getUsername() {
    return email;
  }
}
