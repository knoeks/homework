package lt.bite.povilas.homework.exception.user;

import lt.bite.povilas.homework.exception.core.NotFoundException;

public class UserNotFoundException extends NotFoundException {
  public UserNotFoundException(String message) {
    super("User was not found: " + message + " (id)");
  }
}
