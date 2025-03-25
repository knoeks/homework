package lt.bite.povilas.homework.exception.core;

public abstract class NotFoundException extends RuntimeException {
  public NotFoundException(String messagePrefix) {
    super(messagePrefix);
  }
}
