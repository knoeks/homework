package lt.bite.povilas.homework.exception;

public abstract class NotFoundException extends RuntimeException {
  public NotFoundException(String messagePrefix) {
    super(messagePrefix);
  }
}
