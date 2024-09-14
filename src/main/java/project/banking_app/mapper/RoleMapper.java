package project.banking_app.mapper;

import org.springframework.stereotype.Component;
import project.banking_app.dto.RoleDTO;
import project.banking_app.entity.Role;

@Component
public class RoleMapper {

    public  RoleDTO mapToRoleDTO(Role role){
        return  new RoleDTO(role.getId() , role.getName());
    }

    public  Role mapToRole(RoleDTO roleDTO){
        return new Role(roleDTO.getId() , roleDTO.getName());
    }
}
