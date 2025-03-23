package lt.bite.povilas.homework.exception;

public class UnauthorizedEventAccessException extends RuntimeException {
  public UnauthorizedEventAccessException(String message) {
    super("unauthorized access: " + message);
  }
}
