package am.solido.restexample.mapper;

import am.solido.restexample.dto.CreateUserDto;
import am.solido.restexample.dto.UserDto;
import am.solido.restexample.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper{

    User map(CreateUserDto createUserDto);

    UserDto map(User user);
}
