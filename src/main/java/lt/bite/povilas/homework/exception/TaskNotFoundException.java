package lt.bite.povilas.homework.exception;

public class TaskNotFoundException extends RuntimeException {
  public TaskNotFoundException(String message) {
    super("Task was not found: " + message);
  }
}
