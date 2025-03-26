package lt.bite.povilas.homework.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lt.bite.povilas.homework.dto.task.TaskCreateRequest;
import lt.bite.povilas.homework.dto.task.TaskEditRequest;
import lt.bite.povilas.homework.dto.task.TaskResponse;
import lt.bite.povilas.homework.enums.TaskStatus;
import lt.bite.povilas.homework.exception.task.TaskNotFoundException;
import lt.bite.povilas.homework.exception.auth.UnauthorizedEventAccessException;
import lt.bite.povilas.homework.exception.user.UserNotFoundException;
import lt.bite.povilas.homework.mapper.TaskMapper;
import lt.bite.povilas.homework.model.Task;
import lt.bite.povilas.homework.model.User;
import lt.bite.povilas.homework.repository.TaskRepository;
import lt.bite.povilas.homework.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class TaskService {
  private final TaskRepository taskRepository;
  private final UserRepository userRepository;
  private final TaskMapper taskMapper;
  private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

  @Transactional
  public TaskResponse saveTask(TaskCreateRequest taskRequest, String email) {
    logger.info("Saving task for user: {}", email);
    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UserNotFoundException(email));

    Task task = taskMapper.toEntity(taskRequest); // Associate the user with the task
    task.setUser(user);
    TaskResponse response = taskMapper.toResponse(taskRepository.save(task));
    logger.debug("Task saved with ID: {}", response.id());
    return response;
  }


  // TODO: email
  public TaskResponse findTaskById(long id, String email) {
    Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(String.valueOf(id)));

    // TODO: pakeist kad email paduot
    verifyTaskOwnership(task, email, id);

    return taskMapper.toResponse(task);
  }

  public List<TaskResponse> findTasksByStatus(TaskStatus status, Authentication authentication) {
    String currentEmail = authentication.getName();
    List<Task> tasks = taskRepository.findByStatusAndUserEmail(status, currentEmail);

    return taskMapper.toResponseList(tasks);
  }

  @Transactional
  public TaskResponse updateTask(long taskId, TaskEditRequest taskRequest, String email) {
    Task taskFromDb = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(String.valueOf(taskId)));

    verifyTaskOwnership(taskFromDb, email, taskId);

    taskMapper.updateEntity(taskRequest, taskFromDb);

    return taskMapper.toResponse(taskRepository.save(taskFromDb));
  }

  public TaskResponse cycleStatus(long id, String email) {
    Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(String.valueOf(id)));

    verifyTaskOwnership(task, email, id);

    task.setStatus(task.getStatus().next());
    return taskMapper.toResponse(taskRepository.save(task));
  }

  // other methods always go to the end
  private void verifyTaskOwnership(Task task, String currentEmail, long taskId) {
    if (!task.getUser().getEmail().equals(currentEmail)) {
      throw new UnauthorizedEventAccessException(String.valueOf(taskId));
    }
  }
}
