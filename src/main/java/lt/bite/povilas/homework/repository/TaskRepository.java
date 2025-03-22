package lt.bite.povilas.homework.repository;

import lt.bite.povilas.homework.enums.TaskStatus;
import lt.bite.povilas.homework.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
  List<Task> findByStatus(TaskStatus status);
}
