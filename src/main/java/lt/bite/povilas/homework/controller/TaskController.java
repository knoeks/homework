package lt.bite.povilas.homework.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lt.bite.povilas.homework.dto.taskDTO.TaskPostRequest;
import lt.bite.povilas.homework.dto.taskDTO.TaskPutRequest;
import lt.bite.povilas.homework.dto.taskDTO.TaskResponse;
import lt.bite.povilas.homework.enums.TaskStatus;
import lt.bite.povilas.homework.model.Task;
import lt.bite.povilas.homework.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@AllArgsConstructor
public class TaskController {
  private final TaskService taskService;

  @PostMapping
  public ResponseEntity<TaskResponse> addTask(@Valid @RequestBody TaskPostRequest taskRequest, Authentication authentication) {
    TaskResponse savedTask = taskService.saveTask(taskRequest, authentication);

    return ResponseEntity.created(
            ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedTask.id())
                    .toUri()
    ).body(savedTask);
  }

  @PutMapping("/{id}")
  public ResponseEntity<TaskResponse> updateTask(@PathVariable long id, @RequestBody TaskPutRequest taskRequest, Authentication authentication) {
    TaskResponse updatedTask = taskService.updateTask(id, taskRequest, authentication);
    return ResponseEntity.ok(updatedTask);
  }

  @PutMapping("/{id}/cycle-status")
  public ResponseEntity<TaskResponse> updateTask(@PathVariable long id, Authentication authentication) {
    TaskResponse updatedTask = taskService.cycleStatus(id, authentication);

    return ResponseEntity.ok(updatedTask);
  }

  @GetMapping("/{id}")
  public ResponseEntity<TaskResponse> getTask(@PathVariable long id, Authentication authentication) {
    TaskResponse taskFromDb = taskService.findTaskById(id, authentication);

    return ResponseEntity.ok(taskFromDb);
  }

  @GetMapping
  public ResponseEntity<List<TaskResponse>> getTaskByStatus(
          @RequestParam
          @Parameter(description = "Task status filter", required = true)
          TaskStatus status,
          Authentication authentication) {
    List<TaskResponse> tasks = taskService.findTasksByStatus(status, authentication);

    return ResponseEntity.ok(tasks);
  }
}
