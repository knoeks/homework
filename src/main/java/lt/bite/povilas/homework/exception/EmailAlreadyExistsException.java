package lt.bite.povilas.homework.exception;

public class EmailAlreadyExistsException extends RuntimeException {
  public EmailAlreadyExistsException(String message) {
    super("this email already exists: " + message);
  }
}
