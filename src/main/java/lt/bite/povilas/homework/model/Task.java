package lt.bite.povilas.homework.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lt.bite.povilas.homework.enums.TaskStatus;

import java.time.LocalDateTime;

import static lombok.AccessLevel.NONE;

@Entity
@Table(name = "tasks")
@Setter
@Getter
@NoArgsConstructor
public class Task {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Setter(NONE)
  private long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  @Setter(NONE)
  private TaskStatus status = TaskStatus.NOT_STARTED;

  @Column(nullable = false)
  private String name;

  private String description;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  private User user;

  @Setter(NONE)
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  public Task(String name, String description, User user) {
    this.name = name;
    this.description = description;
    this.user = user;
  }

  @PrePersist
  public void prePersist() {
    this.createdAt = LocalDateTime.now();
  }

  public void setStatus(TaskStatus status) {
    if (status == null) {
      throw new IllegalArgumentException("Status cannot be null");
    }
    this.status = status;
  }
}
