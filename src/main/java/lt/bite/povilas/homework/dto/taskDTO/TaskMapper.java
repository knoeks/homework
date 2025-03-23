package lt.bite.povilas.homework.mapper;

import lt.bite.povilas.homework.dto.taskDTO.TaskPostRequest;
import lt.bite.povilas.homework.dto.taskDTO.TaskPutRequest;
import lt.bite.povilas.homework.dto.taskDTO.TaskResponse;
import lt.bite.povilas.homework.model.Task;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskMapper {

  private final ModelMapper modelMapper;

  @Autowired
  public TaskMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  public Task toEntity(TaskPostRequest dto) {
    return modelMapper.map(dto, Task.class);
  }

  public void updateEntity(TaskPutRequest dto, Task task) {
    modelMapper.map(dto, task);
  }

  public TaskResponse toResponse(Task task) {
    return modelMapper.map(task, TaskResponse.class);
  }

  public List<TaskResponse> toResponseList(List<Task> tasks) {
    return tasks.stream()
            .map(this::toResponse)
            .toList();
  }
}