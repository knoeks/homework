package lt.bite.povilas.homework.exception;

public class RoleNotFoundException extends NotFoundException {
  public RoleNotFoundException(String message) {
    super("Role was found: " + message);
  }
}
