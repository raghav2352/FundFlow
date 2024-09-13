package project.banking_app.mapper;

import project.banking_app.dto.RoleDTO;
import project.banking_app.entity.Role;

public class RoleMapper {

    public  RoleDTO mapToRoleDTO(Role role){
        return  new RoleDTO(role.getId() , role.getName());
    }

    public  Role mapToRole(RoleDTO roleDTO){
        return new Role(roleDTO.getId() , roleDTO.getName());
    }
}
