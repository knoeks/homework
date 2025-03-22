package lt.bite.povilas.homework.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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

  @Setter(NONE)
  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Setter(NONE)
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @PrePersist
  public void prePersist() {
    this.createdAt = LocalDateTime.now();
  }

  public Task(String name, String description, User user) {
    this.name = name;
    this.description = description;
    this.user = user;
  }

  public void setStatus(TaskStatus status) {
    if (status == null) {
      throw new IllegalArgumentException("Status cannot be null");
    }
    this.status = status;
  }
}
