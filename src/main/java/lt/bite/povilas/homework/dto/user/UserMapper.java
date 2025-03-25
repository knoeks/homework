package lt.bite.povilas.homework.dto.user;

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

    this.modelMapper.createTypeMap(UserRequest.class, User.class)
            .setProvider(request -> {
              UserRequest req = (UserRequest) request.getSource();
              return new User(req.email(), req.password());
            });


    // jau geriau buciau mappines rankiniu budu negu su sita nesamone uzsiemes...
    // vis tiek tiek pat rasymo...
    this.modelMapper.createTypeMap(User.class, UserResponse.class)
            .setProvider(request -> {
              User src = (User) request.getSource();
              return new UserResponse(src.getId(), src.getEmail(), src.getRegisteredAt(), src.getRoles());
            });
  }

  public User toEntity(UserRequest dto) {
    return modelMapper.map(dto, User.class);
  }

  public UserResponse toResponse(User user) {
    return modelMapper.map(user, UserResponse.class);
  }
}
