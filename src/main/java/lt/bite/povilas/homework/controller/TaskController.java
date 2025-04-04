package lt.bite.povilas.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lt.bite.povilas.homework.dto.task.TaskCreateRequest;
import lt.bite.povilas.homework.dto.task.TaskEditRequest;
import lt.bite.povilas.homework.dto.task.TaskResponse;
import lt.bite.povilas.homework.enums.TaskStatus;
import lt.bite.povilas.homework.service.TaskService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@AllArgsConstructor
@Tag(
        name = "Task Management",
        description = "Endpoints for managing tasks")
@SecurityRequirement(name = "bearerAuth")
public class TaskController {
  private final TaskService taskService;

  @PostMapping
  @Operation(summary = "Create a new task",
          description = "Creates a new task for the authenticated user."
  )
  @ApiResponses(value = {
          @ApiResponse(
                  responseCode = "201",
                  description = "Task created successfully",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = TaskResponse.class))),
          @ApiResponse(
                  responseCode = "400",
                  description = "Invalid task data provided",
                  content = @Content),
          @ApiResponse(
                  responseCode = "401",
                  description = "Unauthorized - Invalid or missing token",
                  content = @Content)
  })
  @ResponseStatus(HttpStatus.CREATED)
  public TaskResponse addTask(
          @Valid @RequestBody TaskCreateRequest taskRequest,
          Authentication authentication,
          HttpServletResponse response) {

    TaskResponse savedTask = taskService.saveTask(taskRequest, authentication.getName());

    response.setHeader(HttpHeaders.LOCATION,
            ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedTask.id())
                    .toUri().toString());

    return savedTask;
  }

  @PutMapping("/{id}")
  @Operation(
          summary = "Update an existing task",
          description = "Updates the task with the specified ID if the authenticated user has access.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200",
                  description = "Task updated successfully",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = TaskResponse.class))),
          @ApiResponse(
                  responseCode = "400",
                  description = "Invalid task data provided",
                  content = @Content),
          @ApiResponse(
                  responseCode = "401",
                  description = "Unauthorized - Invalid or missing token",
                  content = @Content),
          @ApiResponse(
                  responseCode = "403",
                  description = "Forbidden - User lacks permission to update this task",
                  content = @Content),
          @ApiResponse(
                  responseCode = "404",
                  description = "Task not found",
                  content = @Content)
  })
  @ResponseStatus(HttpStatus.OK)
  public TaskResponse updateTask(
          @Parameter(description = "ID of the task to update", required = true)
          @PathVariable long id,
          @Valid @RequestBody TaskEditRequest taskRequest,
          Authentication authentication) {

    return taskService.updateTask(id, taskRequest, authentication.getName());
  }

  @PutMapping("/{id}/cycle-status")
  @Operation(
          summary = "Cycle task status",
          description = "Cycles the task status (NOT_STARTED -> STARTED -> COMPLETED -> NOT_STARTED) for the specified task.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200",
                  description = "Task status cycled successfully",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = TaskResponse.class))),
          @ApiResponse(
                  responseCode = "401",
                  description = "Unauthorized - Invalid or missing token",
                  content = @Content),
          @ApiResponse(
                  responseCode = "403",
                  description = "Forbidden - User lacks permission to modify this task",
                  content = @Content),
          @ApiResponse(
                  responseCode = "404",
                  description = "Task not found",
                  content = @Content)
  })
  @ResponseStatus(HttpStatus.OK)
  public TaskResponse updateTask(
          @Parameter(description = "ID of the task to cycle status for", required = true)
          @PathVariable long id,
          Authentication authentication) {

    return taskService.cycleStatus(id, authentication.getName());
  }

  @GetMapping("/{id}")
  @Operation(
          summary = "Get a task by ID",
          description = "Retrieves the task with the specified ID if the authenticated user has access.")
  @ApiResponses(value = {
          @ApiResponse(
                  responseCode = "200",
                  description = "Task retrieved successfully",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = TaskResponse.class))),

          @ApiResponse(
                  responseCode = "401",
                  description = "Unauthorized - Invalid or missing token",
                  content = @Content),
          @ApiResponse(
                  responseCode = "403",
                  description = "Forbidden - User lacks permission to view this task",
                  content = @Content),
          @ApiResponse(
                  responseCode = "404",
                  description = "Task not found",
                  content = @Content)
  })
  @ResponseStatus(HttpStatus.OK)
  public TaskResponse getTask(
          @Parameter(description = "ID of the task to retrieve", required = true)
          @PathVariable long id,
          Authentication authentication) {

    return taskService.findTaskById(id, authentication.getName());
  }

  @GetMapping
  @Operation(
          summary = "Get tasks by status",
          description = "Retrieves a list of tasks filtered by status for the authenticated user.")
  @ApiResponses(
          value = {
                  @ApiResponse(responseCode = "200",
                          description = "Tasks retrieved successfully",
                          content = @Content(mediaType = "application/json",
                                  schema = @Schema(implementation = TaskResponse.class))),
                  @ApiResponse(
                          responseCode = "400",
                          description = "Invalid status value provided",
                          content = @Content),
                  @ApiResponse(
                          responseCode = "401",
                          description = "Unauthorized - Invalid or missing token",
                          content = @Content)
          })
  @ResponseStatus(HttpStatus.OK)
  public List<TaskResponse> getTaskByStatus(
          @RequestParam
          TaskStatus status,
          Authentication authentication) {

    return taskService.findTasksByStatus(status, authentication.getName());
  }
}
