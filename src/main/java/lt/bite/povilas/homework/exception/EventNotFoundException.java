package lt.bite.povilas.homework.exception;

public class EventNotFoundException extends RuntimeException {
  public EventNotFoundException(String message) {
    super("Event not found " + message);
  }
}
