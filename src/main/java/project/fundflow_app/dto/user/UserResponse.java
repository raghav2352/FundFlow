package project.fundflow_app.dto.user;

import java.util.Set;

import lombok.Data;
import project.fundflow_app.dto.role.RoleDTO;

@Data
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private Set<RoleDTO> roles;
}
