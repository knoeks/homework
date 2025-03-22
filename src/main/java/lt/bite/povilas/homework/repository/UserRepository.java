package lt.bite.povilas.homework.repository;

import lt.bite.povilas.homework.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {


}
