package lt.bite.povilas.homework.controller;

import lt.bite.povilas.homework.config.SecurityConfig;
import lt.bite.povilas.homework.dto.user.LoginRequest;
import lt.bite.povilas.homework.dto.user.RegistrationRequest;
import lt.bite.povilas.homework.dto.user.RegistrationResponse;
import lt.bite.povilas.homework.exception.auth.EmailAlreadyExistsException;
import lt.bite.povilas.homework.exception.auth.InvalidCredentialsException;
import lt.bite.povilas.homework.model.Role;
import lt.bite.povilas.homework.service.UserService;
import org.hamcrest.text.MatchesPattern;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)

@Import(SecurityConfig.class)
public class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private UserService userService;

  // happy path
  @Test
  @WithMockUser
  void Given_ValidCredentials_When_LoggingIn_Then_ReturnUserWithTokenAnd200() throws Exception {
    LoginRequest loginRequest = new LoginRequest("admin@example.com", "Admin1234!");
    String token = "mock-jwt-token";

    when(userService.loginUser(loginRequest)).thenReturn(token);

    mockMvc.perform(post("/api/users/login").contentType(MediaType.APPLICATION_JSON).content("{\"email\":\"admin@example.com\",\"password\":\"Admin1234!\"}")).andExpect(status().isOk()).andExpect(header().string(HttpHeaders.AUTHORIZATION, "Bearer " + token)).andExpect(jsonPath("$.success").value(true));
  }

  // unhappy path
  @Test
  @WithMockUser
  void Given_InvalidCredentials_When_LoggingIn_Then_ReturnErrorAnd401() throws Exception {
    LoginRequest loginRequest = new LoginRequest("badlogin@example.com", "badPassword");

    when(userService.loginUser(loginRequest)).thenThrow(new InvalidCredentialsException("Invalid email or password"));


    //invalid credentials probably shouldn't return validation information (either know password or don't)
    mockMvc.perform(post("/api/users/login").contentType(MediaType.APPLICATION_JSON).content("{\"email\":\"badlogin@example.com\",\"password\":\"badPassword\"}")).andExpect(status().isUnauthorized()).andExpect(header().doesNotExist(HttpHeaders.AUTHORIZATION)).andExpect(jsonPath("$.error").value("Invalid email or password"));
  }

  // happy path
  @Test
  @WithMockUser
  void Given_ValidUserData_When_Registering_Then_ReturnUserWithLocationAnd201() throws Exception {
    RegistrationRequest registrationRequest = new RegistrationRequest("newuser@example.com", "Pass1234!");
    // fikstuotas laikas testavimui
    LocalDateTime createdAt = LocalDateTime.of(2025, 3, 26, 10, 0, 0);
    RegistrationResponse registrationResponse = new RegistrationResponse(1L, "newuser@example.com", createdAt, Set.of(new Role(1L, "USER")));

    when(userService.saveUser(registrationRequest)).thenReturn(registrationResponse);

    mockMvc.perform(post("/api/users/register").contentType(MediaType.APPLICATION_JSON).content("{\"email\":\"newuser@example.com\",\"password\":\"Pass1234!\"}")).andExpect(status().isCreated()).andExpect(header().string(HttpHeaders.LOCATION, "http://localhost/api/users/register/1")).andExpect(jsonPath("$.id").value(1L)).andExpect(jsonPath("$.email").value("newuser@example.com")).andExpect(jsonPath("$.registeredAt").exists()).andExpect(jsonPath("$.registeredAt").isString()).andExpect(jsonPath("$.registeredAt").value(MatchesPattern.matchesPattern("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}"))).andExpect(jsonPath("$.roles[0].id").value(1L)).andExpect(jsonPath("$.roles[0].name").value("USER"));
  }

  // unhappy path
  @Test
  @WithMockUser
  void Given_DuplicateUserEmail_When_Registering_Then_ReturnUserWithLocationAnd409() throws Exception {
    RegistrationRequest registrationRequest = new RegistrationRequest("admin@example.com", "SomePass1234!");

    when(userService.saveUser(registrationRequest)).thenThrow(new EmailAlreadyExistsException("admin@example.com"));

    mockMvc.perform(post("/api/users/register").contentType(MediaType.APPLICATION_JSON).content("{\"email\":\"admin@example.com\",\"password\":\"SomePass1234!\"}")).andExpect(status().isConflict()).andExpect(jsonPath("$.error").value("this email already exists: admin@example.com"));
  }

}
