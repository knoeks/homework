package lt.bite.povilas.homework.dto.UserDTO;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lt.bite.povilas.homework.dto.UserDTO.UserRequest;
import lt.bite.povilas.homework.dto.UserDTO.UserResponse;
import lt.bite.povilas.homework.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
  private final ModelMapper modelMapper;

  @Autowired
  public UserMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

//  @PostConstruct
//  public void init() {
//    this.modelMapper.createTypeMap(UserRequest.class, User.class)
//            .setProvider(request -> {
//              UserRequest req = (UserRequest) request.getSource();
//              return new User(req.email(), req.password());
//            });
//  }

  public User toEntity(UserRequest dto) {
    return modelMapper.map(dto, User.class);
  }

  public UserResponse toResponse(User user) {
    return modelMapper.map(user, UserResponse.class);
  }
}
