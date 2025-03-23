package lt.bite.povilas.homework.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lt.bite.povilas.homework.dto.taskDTO.TaskPostRequest;
import lt.bite.povilas.homework.dto.taskDTO.TaskPutRequest;
import lt.bite.povilas.homework.dto.taskDTO.TaskResponse;
import lt.bite.povilas.homework.enums.TaskStatus;
import lt.bite.povilas.homework.exception.TaskNotFoundException;
import lt.bite.povilas.homework.exception.UnauthorizedEventAccessException;
import lt.bite.povilas.homework.exception.UserNotFoundException;
import lt.bite.povilas.homework.model.Task;
import lt.bite.povilas.homework.model.User;
import lt.bite.povilas.homework.repository.TaskRepository;
import lt.bite.povilas.homework.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import lt.bite.povilas.homework.mapper.TaskMapper;

import java.util.List;

@AllArgsConstructor
@Service
public class TaskService {
  private final TaskRepository taskRepository;
  private final UserRepository userRepository;
  private final TaskMapper taskMapper;
  private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

  private void verifyTaskOwnership(Task task, String currentEmail, long taskId) {
    if (!task.getUser().getEmail().equals(currentEmail)) {
      throw new UnauthorizedEventAccessException(String.valueOf(taskId));
    }
  }

  @Transactional
  public TaskResponse saveTask(TaskPostRequest taskRequest, Authentication authentication) {
    logger.info("Saving task for user: {}", authentication.getName());
    User user = userRepository.findByEmail(authentication.getName())
            .orElseThrow(() -> new UserNotFoundException(authentication.getName()));

    Task task = taskMapper.toEntity(taskRequest); // Associate the user with the task
    task.setUser(user);
    TaskResponse response = taskMapper.toResponse(taskRepository.save(task));
    logger.debug("Task saved with ID: {}", response.id());
    return response;
  }

  public TaskResponse findTaskById(long id, Authentication authentication) {
    Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(String.valueOf(id)));

    verifyTaskOwnership(task, authentication.getName(), id);

    return taskMapper.toResponse(task);
  }

  public List<TaskResponse> findTasksByStatus(TaskStatus status, Authentication authentication) {
    String currentEmail = authentication.getName();
    List<Task> tasks = taskRepository.findByStatusAndUserEmail(status, currentEmail);

    return taskMapper.toResponseList(tasks);
  }

  @Transactional
  public TaskResponse updateTask(long taskId, TaskPutRequest taskRequest, Authentication authentication) {
    Task taskFromDb = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(String.valueOf(taskId)));

    verifyTaskOwnership(taskFromDb, authentication.getName(), taskId);

    taskMapper.updateEntity(taskRequest, taskFromDb);

    return taskMapper.toResponse(taskRepository.save(taskFromDb));
  }

  public TaskResponse cycleStatus(long id, Authentication authentication) {
    Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(String.valueOf(id)));

    verifyTaskOwnership(task, authentication.getName(), id);

    task.setStatus(task.getStatus().next());
    return taskMapper.toResponse(taskRepository.save(task));
  }

  public List<TaskResponse> findAllTasks() {
    return taskMapper.toResponseList(taskRepository.findAll());
  }
}
