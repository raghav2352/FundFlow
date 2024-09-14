package project.banking_app.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.banking_app.dto.RoleDTO;
import project.banking_app.dto.UserDTO;
import project.banking_app.entity.Role;
import project.banking_app.entity.User;
import project.banking_app.repository.RoleRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Component
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
        if(user.getRoles() != null) {
            Set<RoleDTO> roleDTOS = user.getRoles().stream()
                    .map(roleMapper::mapToRoleDTO)
                    .collect(Collectors.toSet());
            dto.setRoles(roleDTOS);
        }
        return dto;
    }

    public static User mapToUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setId(userDTO.getId());
        if(userDTO.getRoles() != null){
            Set<Role> roles = userDTO.getRoles().stream()
                    .map(roleMapper::mapToRole)
                    .collect(Collectors.toSet());
            user.setRoles(roles);

        }
        return user;
    }
}
