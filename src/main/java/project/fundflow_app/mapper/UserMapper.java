package project.fundflow_app.mapper;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import project.fundflow_app.dto.user.UserRequest;
import project.fundflow_app.dto.user.UserResponse;
import project.fundflow_app.entity.User;

import java.util.HashSet;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper{

    private final RoleMapper roleMapper;

    public User toEntity(UserRequest dto){
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return user;
    }

    public UserResponse toDto(User user) {
        System.out.println("User: " + user.getEmail());
        System.out.println("Roles: " + user.getRoles());


        UserResponse dto = new UserResponse();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        if (user.getRoles() == null) {
            System.out.println("user roles is null");
            dto.setRoles(new HashSet<>());
        } else {
            dto.setRoles(
                    user.getRoles() == null ?
                            new HashSet<>() :
                            user.getRoles().stream()
                                    .map(roleMapper::mapToRoleDTO)
                                    .collect(Collectors.toSet())
            );
        }
        return dto;
    }
}
