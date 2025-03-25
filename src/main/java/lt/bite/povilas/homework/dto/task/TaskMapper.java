package lt.bite.povilas.homework.mapper;

import lombok.AllArgsConstructor;
import lt.bite.povilas.homework.dto.task.TaskCreateRequest;
import lt.bite.povilas.homework.dto.task.TaskEditRequest;
import lt.bite.povilas.homework.dto.task.TaskResponse;
import lt.bite.povilas.homework.dto.user.UserMapper;
import lt.bite.povilas.homework.dto.user.UserResponse;
import lt.bite.povilas.homework.model.Task;
import org.springframework.stereotype.Component;

import java.util.List;


// ditching modelMapper for manual mapping since after researching it turns out
// it cant map records and that just defeats the whole purpose of using them
@Component
@AllArgsConstructor
public class TaskMapper {
  private final UserMapper userMapper;

  public Task toEntity(TaskCreateRequest dto) {
    if (dto == null) {
      throw new IllegalArgumentException("TaskPostRequest cannot be null");
    }
    Task task = new Task();
    task.setName(dto.name());
    task.setDescription(dto.description());
    return task;
  }

  public void updateEntity(TaskEditRequest dto, Task task) {
    if (dto == null || task == null) {
      throw new IllegalArgumentException("TaskPutRequest or Task cannot be null");
    }
    task.setName(dto.name());
    task.setDescription(dto.description());
    task.setStatus(dto.getStatusEnum());
  }

  public TaskResponse toResponse(Task task) {
    if (task == null) {
      throw new IllegalArgumentException("Task cannot be null");
    }
    UserResponse userResponse = userMapper.toResponse(task.getUser());
    return new TaskResponse(
            task.getId(),
            task.getName(),
            task.getDescription(),
            task.getStatus(),
            task.getCreatedAt()
    );
  }

  public List<TaskResponse> toResponseList(List<Task> tasks) {
    if (tasks == null) {
      throw new IllegalArgumentException("Task list cannot be null");
    }
    return tasks.stream()
            .map(this::toResponse)
            .toList();
  }
}