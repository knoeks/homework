package lt.bite.povilas.homework.exception.auth;

public class EmailAlreadyExistsException extends RuntimeException {
  public EmailAlreadyExistsException(String message) {
    super("this email already exists: " + message);
  }
}
