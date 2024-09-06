package project.banking_app.mapper;

import project.banking_app.dto.UserDTO;
import project.banking_app.entity.User;

public class UserMapper {
    public static UserDTO mapToUserDto(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getEmail() , user.getPassword());
    }

    public static User mapToUser(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        return user;
    }
}
