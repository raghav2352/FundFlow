package project.fundflow_app.mapper;

import org.springframework.stereotype.Component;

import project.fundflow_app.dto.role.RoleDTO;
import project.fundflow_app.entity.Role;

@Component
public class RoleMapper {

    public  RoleDTO mapToRoleDTO(Role role){
        if (role == null) {
            throw new IllegalArgumentException("Role is null");
        }
        RoleDTO dto = new RoleDTO();
        dto.setId(role.getId());
        dto.setName(role.getName());
        return dto;
    }

    public  Role mapToRole(RoleDTO roleDTO){
        return new Role(roleDTO.getId() , roleDTO.getName());
    }
}
