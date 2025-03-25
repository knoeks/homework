package lt.bite.povilas.homework.exception.task;

import lt.bite.povilas.homework.exception.core.NotFoundException;

public class TaskNotFoundException extends NotFoundException {
  public TaskNotFoundException(String message) {
    super("Task was not found: " + message + " (id)");
  }
}
