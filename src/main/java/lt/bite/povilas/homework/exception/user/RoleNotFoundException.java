package lt.bite.povilas.homework.exception.user;

import lt.bite.povilas.homework.exception.core.NotFoundException;

public class RoleNotFoundException extends NotFoundException {
  public RoleNotFoundException(String message) {
    super("Role was found: " + message + " (id)");
  }
}
