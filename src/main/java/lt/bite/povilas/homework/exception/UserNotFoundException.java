package lt.bite.povilas.homework.exception;

public class UserNotFoundException extends NotFoundException {
  public UserNotFoundException(String message) {
    super("User was not found: " + message);
  }
}
