package lt.bite.povilas.homework.service;

import lombok.AllArgsConstructor;
import lt.bite.povilas.homework.enums.TaskStatus;
import lt.bite.povilas.homework.exception.EventNotFoundException;
import lt.bite.povilas.homework.exception.TaskNotFoundException;
import lt.bite.povilas.homework.model.Task;
import lt.bite.povilas.homework.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TaskService {
  private final TaskRepository taskRepository;

  // TODO: TaskPostRequest TaskResponse
  public Task savetask(Task task) {
    return taskRepository.save(task);
  }

  // TODO: TaskResponse
  public Optional<Task> findTaskById(long id) {

    return taskRepository.findById(id);
  }

  // TODO: TaskResponse
  public List<Task> findTasksByStatus(TaskStatus status) {
    return taskRepository.findByStatus(status);
  }

  // TODO: TaskUpdateRequest TaskResponse
  public Task updateTask(long taskId, Task task) {
    Task taskFromDb = taskRepository.findById(taskId).orElseThrow(() -> new EventNotFoundException(String.valueOf(taskId)));

    taskFromDb.setName(task.getName());
    taskFromDb.setDescription(task.getDescription());
    taskFromDb.setStatus(task.getStatus());

    return taskRepository.save(taskFromDb);
  }

  public Task cycleStatus(Long id) {
    Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(String.valueOf(id)));

    task.setStatus(task.getStatus().next());
    return taskRepository.save(task);
  }

}
