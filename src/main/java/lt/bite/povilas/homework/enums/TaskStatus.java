package lt.bite.povilas.homework.enums;

public enum TaskStatus {
  NOT_STARTED,
  STARTED,
  COMPLETED;

  public TaskStatus next() {
    return switch (this) {
      case NOT_STARTED -> STARTED;
      case STARTED -> COMPLETED;
      case COMPLETED -> COMPLETED;
    };
  }
}
