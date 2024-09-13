package project.banking_app.dto;

import lombok.*;
import project.banking_app.entity.Role;

import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Data

public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private String password;
    private Set<RoleDTO> roles;
}