package lt.bite.povilas.homework.exception;

public class TaskNotFoundException extends NotFoundException {
  public TaskNotFoundException(String message) {
    super("Task was not found: " + message + " (id)");
  }
}
