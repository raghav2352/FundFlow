package project.banking_app.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import project.banking_app.dto.UserDTO;
import project.banking_app.entity.User;

import java.util.stream.Collectors;


public class UserMapper {

    private static RoleMapper roleMapper;

    public UserMapper(RoleMapper roleMapper){
        this.roleMapper = roleMapper;
    }

    public static UserDTO mapToUserDto(User user) {
        UserDTO dto  = new UserDTO();
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        dto.setPassword(user.getPassword());
        dto.setId(user.getId());
        dto.setRoles(user.getRoles().stream()
                        .map(role -> roleMapper.mapToRoleDTO(role))
                        .collect(Collectors.toSet())
                    );
        return dto;
    }

    public static User mapToUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setId(userDTO.getId());
        user.setRoles(userDTO.getRoles().stream()
                .map(role -> roleMapper.mapToRole(role))
                .collect(Collectors.toSet()));
        return user;
    }
}
