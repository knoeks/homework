package lt.bite.povilas.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Task Management", description = "Endpoints for managing tasks")
@SecurityRequirement(name = "bearerAuth")
public class TaskController {
  private final TaskService taskService;

  @PostMapping
  @Operation(summary = "Create a new task", description = "Creates a new task for the authenticated user.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "Task created successfully",
                  content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskResponse.class))),
          @ApiResponse(responseCode = "400", description = "Invalid task data provided", content = @Content),
          @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing token", content = @Content)
  })
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
  @Operation(summary = "Update an existing task", description = "Updates the task with the specified ID if the authenticated user has access.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Task updated successfully",
                  content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskResponse.class))),
          @ApiResponse(responseCode = "400", description = "Invalid task data provided", content = @Content),
          @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing token", content = @Content),
          @ApiResponse(responseCode = "403", description = "Forbidden - User lacks permission to update this task", content = @Content),
          @ApiResponse(responseCode = "404", description = "Task not found", content = @Content)
  })
  public ResponseEntity<TaskResponse> updateTask(
          @Parameter(description = "ID of the task to update", required = true) @PathVariable long id,
          @Valid @RequestBody TaskPutRequest taskRequest,
          Authentication authentication) {
    TaskResponse updatedTask = taskService.updateTask(id, taskRequest, authentication);
    return ResponseEntity.ok(updatedTask);
  }

  @PutMapping("/{id}/cycle-status")
  @Operation(summary = "Cycle task status", description = "Cycles the task status (NOT_STARTED -> STARTED -> COMPLETED -> NOT_STARTED) for the specified task.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Task status cycled successfully",
                  content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskResponse.class))),
          @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing token", content = @Content),
          @ApiResponse(responseCode = "403", description = "Forbidden - User lacks permission to modify this task", content = @Content),
          @ApiResponse(responseCode = "404", description = "Task not found", content = @Content)
  })
  public ResponseEntity<TaskResponse> updateTask(
          @Parameter(description = "ID of the task to cycle status for", required = true)
          @PathVariable long id, Authentication authentication) {
    TaskResponse updatedTask = taskService.cycleStatus(id, authentication);

    return ResponseEntity.ok(updatedTask);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get a task by ID", description = "Retrieves the task with the specified ID if the authenticated user has access.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Task retrieved successfully",
                  content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskResponse.class))),
          @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing token", content = @Content),
          @ApiResponse(responseCode = "403", description = "Forbidden - User lacks permission to view this task", content = @Content),
          @ApiResponse(responseCode = "404", description = "Task not found", content = @Content)
  })
  public ResponseEntity<TaskResponse> getTask(
          @Parameter(description = "ID of the task to retrieve", required = true) @PathVariable long id,
          Authentication authentication) {
    TaskResponse taskFromDb = taskService.findTaskById(id, authentication);

    return ResponseEntity.ok(taskFromDb);
  }

  @GetMapping
  @Operation(summary = "Get tasks by status", description = "Retrieves a list of tasks filtered by status for the authenticated user.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully",
                  content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskResponse.class))),
          @ApiResponse(responseCode = "400", description = "Invalid status value provided", content = @Content),
          @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing token", content = @Content)
  })
  public ResponseEntity<List<TaskResponse>> getTaskByStatus(
          @RequestParam
          TaskStatus status,
          Authentication authentication) {
    List<TaskResponse> tasks = taskService.findTasksByStatus(status, authentication);

    return ResponseEntity.ok(tasks);
  }
}
