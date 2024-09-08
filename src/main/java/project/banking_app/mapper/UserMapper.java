package project.banking_app.mapper;

import project.banking_app.dto.UserDTO;
import project.banking_app.entity.User;

public class UserMapper {
    public static UserDTO mapToUserDto(User user) {
        UserDTO dto  = new UserDTO();
        dto.setRole(user.getRole());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        dto.setPassword(user.getPassword());
        dto.setId(user.getId());
        return dto;
    }

    public static User mapToUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        user.setId(userDTO.getId());
        return user;
    }
}
